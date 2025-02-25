package com.ioteam.order_management_platform.order.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ioteam.order_management_platform.global.dto.CommonPageResponse;
import com.ioteam.order_management_platform.global.exception.CustomApiException;
import com.ioteam.order_management_platform.global.exception.type.BaseException;
import com.ioteam.order_management_platform.menu.entity.Menu;
import com.ioteam.order_management_platform.menu.repository.MenuRepository;
import com.ioteam.order_management_platform.order.dto.req.AdminOrderSearchCondition;
import com.ioteam.order_management_platform.order.dto.req.CancelOrderRequestDto;
import com.ioteam.order_management_platform.order.dto.req.CreateOrderRequestDto;
import com.ioteam.order_management_platform.order.dto.req.OrderByRestaurantSearchCondition;
import com.ioteam.order_management_platform.order.dto.req.OrderByUserSearchCondition;
import com.ioteam.order_management_platform.order.dto.req.OrderMenuRequestDto;
import com.ioteam.order_management_platform.order.dto.res.OrderResponseDto;
import com.ioteam.order_management_platform.order.entity.Order;
import com.ioteam.order_management_platform.order.entity.OrderMenu;
import com.ioteam.order_management_platform.order.enums.OrderStatus;
import com.ioteam.order_management_platform.order.exception.OrderException;
import com.ioteam.order_management_platform.order.repository.OrderRepository;
import com.ioteam.order_management_platform.restaurant.entity.Restaurant;
import com.ioteam.order_management_platform.restaurant.repository.RestaurantRepository;
import com.ioteam.order_management_platform.user.entity.User;
import com.ioteam.order_management_platform.user.entity.UserRoleEnum;
import com.ioteam.order_management_platform.user.repository.UserRepository;
import com.ioteam.order_management_platform.user.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

	private final OrderRepository orderRepository;

	private final UserRepository userRepository;

	private final RestaurantRepository restaurantRepository;

	private final MenuRepository menuRepository;

	//주문 생성하기
	@Transactional
	public OrderResponseDto createOrder(UUID userId, UserRoleEnum role, CreateOrderRequestDto requestDto) {

		if (requestDto.getOrderMenuList().isEmpty())
			throw new CustomApiException(OrderException.EMPTY_ORDER_MENU);

		User user = userRepository.findByUserIdAndDeletedAtIsNull(userId)
			.orElseThrow(() -> {
				throw new CustomApiException(OrderException.INVALID_USER_ID);
			});
		Restaurant restaurant = restaurantRepository.findByResIdAndDeletedAtIsNull(requestDto.getOrderResId())
			.orElseThrow(() -> {
				throw new CustomApiException(OrderException.INVALID_RES_ID);
			});

		Order order = requestDto.toEntity(role, user, restaurant);
		for (OrderMenuRequestDto orderMenuRequest : requestDto.getOrderMenuList()) {
			Menu menu = menuRepository.getReferenceById(orderMenuRequest.getOrderMenuId());
			OrderMenu orderMenu = orderMenuRequest.toEntity(order, menu);
			order.getOrderMenus().add(orderMenu);
		}

		Order savedOrder = orderRepository.save(order);
		return OrderResponseDto.fromEntity(savedOrder);
	}

	//주문 상세 조회하기
	public OrderResponseDto getOrderDetail(UUID orderId, UserDetailsImpl userDetails) {
		Order order = orderRepository.findByOrderIdAndDeletedAtIsNullFetchJoinUser(orderId)
			.orElseThrow(() -> new CustomApiException(OrderException.INVALID_ORDER_ID));

		// CUSTOMER 이고 본인의 주문이 아닌 경우 예외
		if (UserRoleEnum.CUSTOMER.equals(userDetails.getRole())
			&& !order.getUser().getUserId().equals(userDetails.getUserId())) {
			throw new CustomApiException(BaseException.UNAUTHORIZED_REQ);
		}
		return OrderResponseDto.fromEntity(order);
	}

	//주문 취소하기
	@Transactional
	public OrderResponseDto cancelOrder(UUID orderId, CancelOrderRequestDto requestDto, UserDetailsImpl userDetails) {
		Order order = orderRepository.findByOrderIdAndDeletedAtIsNull(orderId)
			.orElseThrow(() -> new CustomApiException(OrderException.INVALID_ORDER_ID));

		String role = userDetails.getRole().name();
		UUID userId = userDetails.getUserId();
		LocalDateTime minTime = LocalDateTime.now().minusMinutes(5);
		OrderStatus cancelStatus = OrderStatus.CANCELED;

		if (orderRepository.cancelOrder(orderId, role, userId, minTime, cancelStatus) == 0) {
			throw new CustomApiException(OrderException.INVALID_ORDER_CANCEL);
		}

		return OrderResponseDto.fromEntity(order);

	}

	//주문 삭제하기
	@Transactional
	public void softDeleteOrder(UUID orderId, UserDetailsImpl userDetails) {

		Order order = orderRepository.findByOrderIdAndDeletedAtIsNullFetchJoinUser(orderId)
			.orElseThrow(() -> new CustomApiException(OrderException.INVALID_ORDER));

		// 고객이면, 주문자인지 검증
		if (UserRoleEnum.CUSTOMER.equals(userDetails.getRole())
			&& !order.getUser().getUserId().equals(userDetails.getUserId())) {
			throw new CustomApiException(BaseException.UNAUTHORIZED_REQ);
		}
		order.softDelete(userDetails.getUserId());
		for (OrderMenu orderMenu : order.getOrderMenus()) {
			orderMenu.softDelete(userDetails.getUserId());
		}
	}

	//어드민(매니저, 마스터) 주문 전체 조회
	public CommonPageResponse<OrderResponseDto> searchOrderByAdmin(AdminOrderSearchCondition condition,
		Pageable pageable) {
		// MANAGER, MASTER 조회가능
		Page<OrderResponseDto> orderDtoPage = orderRepository.searchOrderAdminByCondition(condition,
				pageable)
			.map(OrderResponseDto::fromEntity);
		return new CommonPageResponse<>(orderDtoPage);
	}

	//가게별 조회
	public CommonPageResponse<OrderResponseDto> searchOrderByRestaurant(UserDetailsImpl userDetails, UUID resId,
		OrderByRestaurantSearchCondition condition, Pageable pageable) {
		// MANAGER, MASTER 조회가능
		// 가게 주인 조회 가능

		// OWNER 인 경우, 해당 가게의 주인이 맞는지 확인
		if (UserRoleEnum.OWNER.equals(userDetails.getRole())
			&& !restaurantRepository.existsByResIdAndOwner_userIdAndDeletedAtIsNull(resId, userDetails.getUserId())) {
			throw new CustomApiException(OrderException.UNAUTH_OWNER);
		}
		Page<OrderResponseDto> orderDtoPage = orderRepository.searchOrderByRestaurantAndCondition(resId, condition,
				pageable)
			.map(OrderResponseDto::fromEntity);
		return new CommonPageResponse<>(orderDtoPage);
	}

	//사용자별 조회
	public CommonPageResponse<OrderResponseDto> searchOrderByUser(UserDetailsImpl userDetails, UUID requiredUserId,
		OrderByUserSearchCondition condition, Pageable pageable) {
		//CUSTOMER, MANAGER, MASTER 조회가능
		//주문 고객만 조회 가능
		if (!userDetails.getUserId().equals(requiredUserId)) {
			throw new CustomApiException(OrderException.UNAUTH_CUSTOMER);
		}

		Page<OrderResponseDto> orderDtoPage = orderRepository.searchOrderByUserAndCondition(requiredUserId, condition,
				pageable)
			.map(OrderResponseDto::fromEntity);
		return new CommonPageResponse<>(orderDtoPage);
	}
}
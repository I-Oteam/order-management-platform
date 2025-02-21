package com.ioteam.order_management_platform.order.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.ioteam.order_management_platform.global.dto.CommonPageResponse;
import com.ioteam.order_management_platform.global.exception.CustomApiException;
import com.ioteam.order_management_platform.global.exception.type.BaseException;
import com.ioteam.order_management_platform.menu.entity.Menu;
import com.ioteam.order_management_platform.menu.repository.MenuRepository;
import com.ioteam.order_management_platform.order.dto.req.CancelOrderRequestDto;
import com.ioteam.order_management_platform.order.dto.req.CreateOrderRequestDto;
import com.ioteam.order_management_platform.order.dto.req.OrderByRestaurantSearchCondition;
import com.ioteam.order_management_platform.order.dto.req.OrderMenuRequestDto;
import com.ioteam.order_management_platform.order.dto.res.OrderListResponseDto;
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

import jakarta.persistence.EntityNotFoundException;
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

		User referenceUser = userRepository.getReferenceById(userId);
		Restaurant referenceRestaurant = restaurantRepository.getReferenceById(requestDto.getOrderResId());

		Order order = Order.builder()
			.user(referenceUser)
			.restaurant(referenceRestaurant)
			.orderType(requestDto.getOrderType())
			.orderLocation(requestDto.getOrderLocation())
			.orderRequest(requestDto.getOrderRequest())
			.orderResTotal(requestDto.getOrderResTotal())
			.orderStatus(OrderStatus.WAITING)
			.build();

		for (OrderMenuRequestDto orderMenuRequest : requestDto.getOrderMenuList()) {
			Menu referenceMenu = menuRepository.getReferenceById(orderMenuRequest.getOrderMenuId());

			OrderMenu orderMenu = OrderMenu.builder()
				.order(order)
				.menu(referenceMenu)
				.orderPrice(orderMenuRequest.getOrderPrice())
				.orderCount(orderMenuRequest.getOrderCount())
				.build();

			order.getOrderMenus().add(orderMenu);
		}

		Order savedOrder = orderRepository.save(order);
		return OrderResponseDto.fromEntity(savedOrder);
	}

	//주문 성공 상태
	public void OrderStatusProcess(UUID orderId) {
		Order order = orderRepository.findById(orderId)
			.orElseThrow(() -> new EntityNotFoundException("주문을 찾을 수 없습니다."));

		order.orderConfirm();
		orderRepository.save(order);
	}

	//주문 전체 조회하기
	@Transactional
	public OrderListResponseDto getAllOrders() {
		List<Order> orderList = orderRepository.findAll();
		List<OrderResponseDto> responseDtos = orderList.stream()
			.map(OrderResponseDto::fromEntity)
			.collect(Collectors.toList());

		return new OrderListResponseDto(responseDtos);
	}

	//주문 상세 조회하기
	@Transactional
	public OrderResponseDto getOrderDetail(UUID orderId) {
		Order order = orderRepository.findById(orderId)
			.orElseThrow(() -> new CustomApiException(OrderException.INVALID_ORDER_ID));

		return OrderResponseDto.fromEntity(order);
	}

	//주문 취소하기
	@Transactional
	public OrderResponseDto cancelOrder(UUID orderId, CancelOrderRequestDto requestDto) {
		Order order = orderRepository.findById(orderId)
			.orElseThrow(() -> new CustomApiException(OrderException.INVALID_ORDER_ID));

		//5분이 지나면 취소 불가능
		if (order.getCreatedAt().isBefore(LocalDateTime.now().minusMinutes(5))) {
			throw new CustomApiException("취소 가능 시간이 지났습니다.");
		}

		order.orderCancel(requestDto);
		orderRepository.save(order);
		return OrderResponseDto.fromEntity(order);
	}

	//주문 삭제하기
	@Transactional
	public void softDeleteOrder(UUID orderId, UserDetailsImpl userDetails) {

		Order order = orderRepository.findById(orderId)
			.orElseThrow(() -> new CustomApiException(OrderException.INVALID_ORDER));

		//주문 가게 주인이 아니라면
		if (!order.getRestaurant().getOwner().getUserId().equals(userDetails.getUserId())) {
			throw new CustomApiException(BaseException.UNAUTHORIZED_REQ);
		}

		Order targetOrder = orderRepository.findByOrderIdAndDeletedAtIsNull(orderId)
			.orElseThrow(() -> new CustomApiException(OrderException.INVALID_ORDER));

		targetOrder.softDelete(userDetails.getUserId());
	}

	public CommonPageResponse<OrderResponseDto> searchOrderByRestaurant(UserDetailsImpl userDetails, UUID resId,
		OrderByRestaurantSearchCondition condition, Pageable pageable) {
		// MANAGER, MASTER 조회가능
		// 가게 주인 조회 가능
		if (userDetails.getRole().equals(UserRoleEnum.OWNER)
			&& !restaurantRepository.existsByResIdAndOwner_userIdAndDeletedAtIsNull(resId, userDetails.getUserId())) {
			throw new CustomApiException(OrderException.UNAUTH_OWNER);
		}
		Page<OrderResponseDto> orderDtoPage = orderRepository.searchOrderByRestaurantAndCondition(resId, condition,
				pageable)
			.map(OrderResponseDto::fromEntity);
		return new CommonPageResponse<>(orderDtoPage);
	}
}



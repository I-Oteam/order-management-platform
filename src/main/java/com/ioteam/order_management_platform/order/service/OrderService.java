package com.ioteam.order_management_platform.order.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ioteam.order_management_platform.global.exception.CustomApiException;
import com.ioteam.order_management_platform.global.exception.type.BaseException;
import com.ioteam.order_management_platform.menu.entity.Menu;
import com.ioteam.order_management_platform.menu.repository.MenuRepository;
import com.ioteam.order_management_platform.order.dto.req.CancelOrderRequestDto;
import com.ioteam.order_management_platform.order.dto.req.CreateOrderRequestDto;
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

	//주문 전체 조회하기
	public OrderListResponseDto getAllOrders() {
		List<Order> orderList = orderRepository.findAllByDeletedAtIsNull();
		List<OrderResponseDto> responseDtos = orderList.stream()
			.map(OrderResponseDto::fromEntity)
			.collect(Collectors.toList());

		return new OrderListResponseDto(responseDtos);
	}

	//주문 상세 조회하기
	public OrderResponseDto getOrderDetail(UUID orderId) {
		Order order = orderRepository.findByOrderIdAndDeletedAtIsNull(orderId)
			.orElseThrow(() -> new CustomApiException(OrderException.INVALID_ORDER_ID));

		return OrderResponseDto.fromEntity(order);
	}

	//주문 취소하기
	@Transactional
	public OrderResponseDto cancelOrder(UUID orderId, CancelOrderRequestDto requestDto, UserDetailsImpl userDetails) {
		Order order = orderRepository.findById(orderId)
			.orElseThrow(() -> new CustomApiException(OrderException.INVALID_ORDER_ID));

		//제 머릿속에서 나오는대로 썼더니 지저분하네요.. 이거 맞나요?
		boolean isOwner = order.getRestaurant().getOwner().getUserId().equals(userDetails.getUserId());
		boolean isCustomer = order.getUser().getUserId().equals(userDetails.getUserId());

		if (isOwner) {
			//가게 주인인 경우 취소 가능
			order.orderCancel(requestDto);
		} else if (isCustomer) {
			//주문 고객인 경우 5분 이내에만 취소 가능
			if (order.getCreatedAt().isBefore(LocalDateTime.now().minusMinutes(5))) {
				throw new CustomApiException(OrderException.INVALID_ORDER_CANCEL);
			} else {
				order.orderCancel(requestDto);
			}
		}

		orderRepository.save(order);
		return OrderResponseDto.fromEntity(order);
	}

	//주문 삭제하기
	@Transactional
	public void softDeleteOrder(UUID orderId, UserDetailsImpl userDetails) {

		Order order = orderRepository.findById(orderId)
			.orElseThrow(() -> new CustomApiException(OrderException.INVALID_ORDER));

		//OWNER나 MANAGER가 아니라면 예외 발생
		if (userDetails.getAuthorities().stream().noneMatch(
			authority -> authority.getAuthority().equals("ROLE_MANAGER") ||
				authority.getAuthority().equals("ROLE_OWNER")
		)) {
			throw new CustomApiException(BaseException.UNAUTHORIZED_REQ);
		}

		// 주문 가게의 주인과 사용자 ID가 일치하지 않으면 예외 발생
		if (!order.getRestaurant().getOwner().getUserId().equals(userDetails.getUserId())) {
			throw new CustomApiException(BaseException.UNAUTHORIZED_REQ);
		}

		order.softDelete(userDetails.getUserId());
	}
}



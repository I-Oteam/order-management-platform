package com.ioteam.order_management_platform.order.dto.req;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.ioteam.order_management_platform.order.entity.Order;
import com.ioteam.order_management_platform.order.enums.OrderStatus;
import com.ioteam.order_management_platform.order.enums.OrderType;
import com.ioteam.order_management_platform.restaurant.entity.Restaurant;
import com.ioteam.order_management_platform.user.entity.User;
import com.ioteam.order_management_platform.user.entity.UserRoleEnum;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CreateOrderRequestDto {

	@NotNull
	private UUID orderResId;

	@NotNull
	private List<OrderMenuRequestDto> orderMenuList;

	@NotNull
	@Min(value = 0, message = "총 주문 금액은 0원 이상이어야 합니다.")
	private BigDecimal orderResTotal;

	private OrderType orderType;

	private String orderLocation;

	private String orderRequest;

	public Order toEntity(UserRoleEnum role, User user, Restaurant restaurant) {

		return Order.builder()
			.user(user)
			.restaurant(restaurant)
			.orderType(this.getOrderType())
			.orderLocation(this.getOrderLocation())
			.orderRequest(this.getOrderRequest())
			.orderResTotal(this.getOrderResTotal())
			.orderType(role.equals(UserRoleEnum.OWNER) ? OrderType.STORE_ORDER : OrderType.DELIVERY)
			.orderStatus(OrderStatus.WAITING)
			.build();
	}
}
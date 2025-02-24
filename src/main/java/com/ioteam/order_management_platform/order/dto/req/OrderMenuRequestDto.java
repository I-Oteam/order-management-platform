package com.ioteam.order_management_platform.order.dto.req;

import java.math.BigDecimal;
import java.util.UUID;

import com.ioteam.order_management_platform.menu.entity.Menu;
import com.ioteam.order_management_platform.order.entity.Order;
import com.ioteam.order_management_platform.order.entity.OrderMenu;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderMenuRequestDto {

	@NotNull
	private UUID orderMenuId;

	@NotNull
	@Min(value = 0)
	private BigDecimal orderPrice;

	@NotNull
	@Min(value = 0)
	private Integer orderCount;

	public OrderMenu toEntity(Order order, Menu menu) {
		return OrderMenu.builder()
			.order(order)
			.menu(menu)
			.orderPrice(this.getOrderPrice())
			.orderCount(this.getOrderCount())
			.build();
	}
}
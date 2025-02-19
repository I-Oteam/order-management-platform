package com.ioteam.order_management_platform.order.dto.res;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderListResponseDto {
	private List<OrderResponseDto> orders;
}

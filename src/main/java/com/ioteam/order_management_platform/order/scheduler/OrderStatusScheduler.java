package com.ioteam.order_management_platform.order.scheduler;


import com.ioteam.order_management_platform.order.entity.Order;
import com.ioteam.order_management_platform.order.enums.OrderStatus;
import com.ioteam.order_management_platform.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class OrderStatusScheduler {

	private final OrderRepository orderRepository;

	@Scheduled(fixedRate = 300000)
	public void checkOrderStatus() {
		List<Order> waitingOrders = orderRepository.findByOrderStatus(OrderStatus.WAITING);

		for (Order order : waitingOrders) {
			order.orderCancel();
		}

		orderRepository.saveAll(waitingOrders);
	}
}

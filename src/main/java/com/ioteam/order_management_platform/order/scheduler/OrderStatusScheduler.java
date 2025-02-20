package com.ioteam.order_management_platform.order.scheduler;


import com.ioteam.order_management_platform.order.entity.Order;
import com.ioteam.order_management_platform.order.enums.OrderStatus;
import com.ioteam.order_management_platform.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Component
@RequiredArgsConstructor
public class OrderStatusScheduler {

	private final OrderRepository orderRepository;

	@Scheduled(fixedRate = 60000)
	@Transactional
	public void checkOrderStatus() {

		//5분 전
		LocalDateTime fiveMinutesAgo = LocalDateTime.now().minusMinutes(5);

		List<Order> expiredOrders = orderRepository.findByOrderStatusAndCreatedAtBefore(OrderStatus.WAITING, fiveMinutesAgo);

		for (Order order : expiredOrders) {
			order.orderConfirm();
		}

		orderRepository.saveAll(expiredOrders);
	}

}

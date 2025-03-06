package com.ioteam.order_management_platform.payment.service.strategy;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentRetrieveStrategies {
	private final List<PaymentRetrieveStrategy> strategies;

	public Stream<PaymentRetrieveStrategy> stream() {
		return strategies.stream();
	}
}

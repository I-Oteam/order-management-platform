package com.ioteam.order_management_platform.payment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ioteam.order_management_platform.global.exception.CustomApiException;
import com.ioteam.order_management_platform.order.entity.Order;
import com.ioteam.order_management_platform.order.repository.OrderRepository;
import com.ioteam.order_management_platform.payment.dto.req.CreatePaymentRequestDto;
import com.ioteam.order_management_platform.payment.dto.res.PaymentResponseDto;
import com.ioteam.order_management_platform.payment.entity.Payment;
import com.ioteam.order_management_platform.payment.exception.PaymentException;
import com.ioteam.order_management_platform.payment.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {
	private final PaymentRepository paymentRepository;
	private final OrderRepository orderRepository;

	@Transactional
	public PaymentResponseDto createPayment(CreatePaymentRequestDto requestDto) {
		Order order = orderRepository.findById(requestDto.getOrderId())
			.orElseThrow(() -> new CustomApiException(PaymentException.INVALID_USERNAME));

		if (paymentRepository.existsByOrderOrderId(requestDto.getOrderId())) {
			throw new CustomApiException(PaymentException.PAYMENT_ALREADY_COMPLETED);
		}

		Payment savedPayment = paymentRepository.save(requestDto.toEntity(order));

		return PaymentResponseDto.fromEntity(savedPayment);
	}
}

package com.ioteam.order_management_platform.payment.service;

import java.util.UUID;

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
import com.ioteam.order_management_platform.user.entity.UserRoleEnum;
import com.ioteam.order_management_platform.user.security.UserDetailsImpl;

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

	public PaymentResponseDto getPayment(UUID paymentId, UserDetailsImpl userDetails) {

		// MASTER, MANAGER: 모든 결제 내역 조회 가능
		if (userDetails.getRole() == UserRoleEnum.MASTER || userDetails.getRole() == UserRoleEnum.MANAGER) {
			Payment payment = paymentRepository.findByPaymentIdAndDeletedAtIsNull(paymentId)
				.orElseThrow(() -> new CustomApiException(PaymentException.INVALID_PAYMENT_ID));
			return PaymentResponseDto.fromEntity(payment);
		}

		// CUSTOMER: 본인이 주문한 결제 내역만 조회 가능
		if (userDetails.getRole() == UserRoleEnum.CUSTOMER) {
			Payment payment = paymentRepository.findPaymentForCustomer(paymentId, userDetails.getUserId())
				.orElseThrow(() -> new CustomApiException(PaymentException.UNAUTHORIZED_PAYMENT_ACCESS));
			return PaymentResponseDto.fromEntity(payment);
		}

		// OWNER: 본인 가게의 주문 결제 내역만 조회 가능
		if (userDetails.getRole() == UserRoleEnum.OWNER) {
			Payment payment = paymentRepository.findPaymentWithOrderAndRestaurant(paymentId)
				.orElseThrow(() -> new CustomApiException(PaymentException.INVALID_PAYMENT_ID));

			// 본인이 해당 가게의 주인인지 확인
			if (!payment.getOrder().getRestaurant().getOwner().getUserId().equals(userDetails.getUserId())) {
				throw new CustomApiException(PaymentException.UNAUTHORIZED_PAYMENT_ACCESS);
			}
			return PaymentResponseDto.fromEntity(payment);
		}

		throw new CustomApiException(PaymentException.UNAUTHORIZED_PAYMENT_ACCESS);
	}
}

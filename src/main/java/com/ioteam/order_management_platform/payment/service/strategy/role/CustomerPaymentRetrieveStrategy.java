package com.ioteam.order_management_platform.payment.service.strategy.role;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.ioteam.order_management_platform.global.exception.CustomApiException;
import com.ioteam.order_management_platform.payment.dto.res.PaymentResponseDto;
import com.ioteam.order_management_platform.payment.entity.Payment;
import com.ioteam.order_management_platform.payment.exception.PaymentException;
import com.ioteam.order_management_platform.payment.repository.PaymentRepository;
import com.ioteam.order_management_platform.payment.service.strategy.PaymentRetrieveStrategy;
import com.ioteam.order_management_platform.user.entity.UserRoleEnum;
import com.ioteam.order_management_platform.user.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerPaymentRetrieveStrategy implements PaymentRetrieveStrategy {

	private final PaymentRepository paymentRepository;

	@Override
	public boolean isSupport(UserRoleEnum role) {
		return role == UserRoleEnum.CUSTOMER;
	}

	@Override
	public PaymentResponseDto getPayment(UUID paymentId, UserDetailsImpl userDetails) {
		Payment payment = paymentRepository.findPaymentForCustomer(paymentId, userDetails.getUserId())
			.orElseThrow(() -> new CustomApiException(PaymentException.UNAUTHORIZED_PAYMENT_ACCESS));
		return PaymentResponseDto.from(payment);
	}

}

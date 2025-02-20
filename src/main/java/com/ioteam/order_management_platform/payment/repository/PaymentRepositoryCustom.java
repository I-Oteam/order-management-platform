package com.ioteam.order_management_platform.payment.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ioteam.order_management_platform.payment.dto.req.AdminPaymentSearchCondition;
import com.ioteam.order_management_platform.payment.dto.res.AdminPaymentResponseDto;
import com.ioteam.order_management_platform.payment.dto.res.PaymentResponseDto;

public interface PaymentRepositoryCustom {
	Page<AdminPaymentResponseDto> searchPaymentAdminByCondition(AdminPaymentSearchCondition condition,
		Pageable pageable);

	Page<PaymentResponseDto> searchPaymentByUser(UUID userId, Pageable pageable);

	Page<PaymentResponseDto> searchPaymentByRestaurant(UUID userId, UUID restaurantId, Pageable pageable);
}

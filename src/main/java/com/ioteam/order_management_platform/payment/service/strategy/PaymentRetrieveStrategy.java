package com.ioteam.order_management_platform.payment.service.strategy;

import java.util.UUID;

import com.ioteam.order_management_platform.payment.dto.res.PaymentResponseDto;
import com.ioteam.order_management_platform.user.entity.UserRoleEnum;
import com.ioteam.order_management_platform.user.security.UserDetailsImpl;

public interface PaymentRetrieveStrategy {
	boolean isSupport(UserRoleEnum userRole);

	PaymentResponseDto getPayment(UUID paymentId, UserDetailsImpl userDetails);

}

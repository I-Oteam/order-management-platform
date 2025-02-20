package com.ioteam.order_management_platform.payment.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ioteam.order_management_platform.global.dto.CommonPageResponse;
import com.ioteam.order_management_platform.global.exception.CustomApiException;
import com.ioteam.order_management_platform.order.entity.Order;
import com.ioteam.order_management_platform.order.repository.OrderRepository;
import com.ioteam.order_management_platform.payment.dto.req.AdminPaymentSearchCondition;
import com.ioteam.order_management_platform.payment.dto.req.CreatePaymentRequestDto;
import com.ioteam.order_management_platform.payment.dto.req.CustomerPaymentSearchCondition;
import com.ioteam.order_management_platform.payment.dto.req.OwnerPaymentSearchCondition;
import com.ioteam.order_management_platform.payment.dto.res.AdminPaymentResponseDto;
import com.ioteam.order_management_platform.payment.dto.res.PaymentResponseDto;
import com.ioteam.order_management_platform.payment.entity.Payment;
import com.ioteam.order_management_platform.payment.exception.PaymentException;
import com.ioteam.order_management_platform.payment.repository.PaymentRepository;
import com.ioteam.order_management_platform.restaurant.repository.RestaurantRepository;
import com.ioteam.order_management_platform.user.entity.UserRoleEnum;
import com.ioteam.order_management_platform.user.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {
	private final PaymentRepository paymentRepository;
	private final OrderRepository orderRepository;
	private final RestaurantRepository restaurantRepository;

	@Transactional
	public PaymentResponseDto createPayment(CreatePaymentRequestDto requestDto) {
		Order order = orderRepository.findById(requestDto.getOrderId())
			.orElseThrow(() -> new CustomApiException(PaymentException.INVALID_USERNAME));

		if (paymentRepository.existsByOrderOrderId(requestDto.getOrderId())) {
			throw new CustomApiException(PaymentException.PAYMENT_ALREADY_COMPLETED);
		}

		Payment savedPayment = paymentRepository.save(requestDto.toEntity(order));

		return PaymentResponseDto.from(savedPayment);
	}

	public PaymentResponseDto getPayment(UUID paymentId, UserDetailsImpl userDetails) {
		// MASTER, MANAGER: 모든 결제 내역 조회 가능
		if (userDetails.getRole() == UserRoleEnum.MASTER || userDetails.getRole() == UserRoleEnum.MANAGER) {
			Payment payment = paymentRepository.findByPaymentIdAndDeletedAtIsNull(paymentId)
				.orElseThrow(() -> new CustomApiException(PaymentException.INVALID_PAYMENT_ID));
			return PaymentResponseDto.from(payment);
		}

		// CUSTOMER: 본인이 주문한 결제 내역만 조회 가능
		if (userDetails.getRole() == UserRoleEnum.CUSTOMER) {
			Payment payment = paymentRepository.findPaymentForCustomer(paymentId, userDetails.getUserId())
				.orElseThrow(() -> new CustomApiException(PaymentException.UNAUTHORIZED_PAYMENT_ACCESS));
			return PaymentResponseDto.from(payment);
		}

		// OWNER: 본인 가게의 주문 결제 내역만 조회 가능
		if (userDetails.getRole() == UserRoleEnum.OWNER) {
			Payment payment = paymentRepository.findPaymentWithOrderAndRestaurant(paymentId)
				.orElseThrow(() -> new CustomApiException(PaymentException.INVALID_PAYMENT_ID));

			// 본인이 해당 가게의 주인인지 확인
			if (!payment.getOrder().getRestaurant().getOwner().getUserId().equals(userDetails.getUserId())) {
				throw new CustomApiException(PaymentException.UNAUTHORIZED_PAYMENT_ACCESS);
			}
			return PaymentResponseDto.from(payment);
		}

		throw new CustomApiException(PaymentException.UNAUTHORIZED_PAYMENT_ACCESS);
	}

	public CommonPageResponse<AdminPaymentResponseDto> searchPaymentAdminByCondition(
		AdminPaymentSearchCondition condition, Pageable pageable) {
		Page<AdminPaymentResponseDto> paymentDtoPage = paymentRepository.searchPaymentAdminByCondition(condition,
			pageable);
		return new CommonPageResponse<>(paymentDtoPage);
	}

	public CommonPageResponse<PaymentResponseDto> searchPaymentByUser(CustomerPaymentSearchCondition condition,
		UUID userId, UUID requiredUserId,
		Pageable pageable) {
		if (!userId.equals(requiredUserId))
			throw new CustomApiException(PaymentException.UNAUTHORIZED_REQ);
		Page<PaymentResponseDto> paymentDtoPage = paymentRepository.searchPaymentByUser(condition, userId, pageable);
		return new CommonPageResponse<>(paymentDtoPage);
	}

	public CommonPageResponse<PaymentResponseDto> searchPaymentByRestaurant(OwnerPaymentSearchCondition condition,
		UUID userId, UUID resId,
		Pageable pageable) {
		UUID resUserId = restaurantRepository.getReferenceById(resId).getOwner().getUserId();
		if (!userId.equals(resUserId))
			throw new CustomApiException(PaymentException.UNAUTHORIZED_REQ);
		Page<PaymentResponseDto> paymentDtoPage = paymentRepository.searchPaymentByRestaurant(condition, userId, resId,
			pageable);
		return new CommonPageResponse<>(paymentDtoPage);
	}

	@Transactional
	public void softDeletePayment(UUID paymentId, UserDetailsImpl userDetails) {
		Payment payment = paymentRepository.findByPaymentIdAndDeletedAtIsNull(paymentId)
			.orElseThrow(() -> new CustomApiException(PaymentException.INVALID_PAYMENT_ID));

		payment.softDelete(userDetails.getUserId());
	}
}

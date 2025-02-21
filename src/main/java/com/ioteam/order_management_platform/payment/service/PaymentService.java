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
import com.ioteam.order_management_platform.payment.entity.PaymentStatusEnum;
import com.ioteam.order_management_platform.payment.exception.PaymentException;
import com.ioteam.order_management_platform.payment.repository.PaymentRepository;
import com.ioteam.order_management_platform.restaurant.entity.Restaurant;
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
	public PaymentResponseDto createPayment(UserDetailsImpl userDetails, CreatePaymentRequestDto requestDto) {
		// 주문이 유효한지, 주문자가 본인인지, 주문이 삭제되지 않았는지 확인
		Order order = orderRepository.findValidOrderForPayment(requestDto.getOrderId(),
				userDetails.getUser().getUserId())
			.orElseThrow(() -> new CustomApiException(PaymentException.INVALID_ORDER_OR_USER));
		// 이미 결제된 내역이 있는지 확인
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
		UserDetailsImpl userDetails, UUID resId,
		Pageable pageable) {
		Restaurant restaurant = restaurantRepository.findById(resId)
			.orElseThrow(() -> new CustomApiException(PaymentException.RESTAURANT_NOT_FOUND));

		UUID resOwnerId = restaurant.getOwner().getUserId();
		UserRoleEnum userRole = userDetails.getRole();

		// 접근 권한 확인 (OWNER 이면서 해당 가게의 소유자이거나, MASTER 또는 MANAGER 만 허용)
		boolean isOwnerOfThisRestaurant = userRole == UserRoleEnum.OWNER && userDetails.getUserId().equals(resOwnerId);
		boolean hasHigherPrivileges = userRole == UserRoleEnum.MANAGER || userRole == UserRoleEnum.MASTER;

		if (!isOwnerOfThisRestaurant && !hasHigherPrivileges) {
			throw new CustomApiException(PaymentException.UNAUTHORIZED_REQ);
		}
		Page<PaymentResponseDto> paymentDtoPage = paymentRepository.searchPaymentByRestaurant(condition, resId,
			pageable);
		return new CommonPageResponse<>(paymentDtoPage);
	}

	@Transactional
	public void softDeletePayment(UUID paymentId, UserDetailsImpl userDetails) {
		Payment payment = paymentRepository.findByPaymentIdAndDeletedAtIsNull(paymentId)
			.orElseThrow(() -> new CustomApiException(PaymentException.INVALID_PAYMENT_ID));

		payment.softDelete(userDetails.getUserId());
	}

	@Transactional
	public PaymentResponseDto changePaymentStatus(UUID paymentId, PaymentStatusEnum newStatus) {
		Payment payment = paymentRepository.findByPaymentIdAndDeletedAtIsNull(paymentId)
			.orElseThrow(() -> new CustomApiException(PaymentException.PAYMENT_NOT_FOUND));
		if (payment.getPaymentStatus() == PaymentStatusEnum.PENDING && payment.getPaymentStatus() != newStatus) {
			payment.setPaymentStatus(newStatus);
			Payment updatedPayment = paymentRepository.save(payment);
			return PaymentResponseDto.from(updatedPayment);
		} else {
			throw new CustomApiException(PaymentException.PAYMENT_ALREADY_COMPLETED);
		}
	}
}

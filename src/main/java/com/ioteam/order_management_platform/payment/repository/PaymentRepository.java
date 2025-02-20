package com.ioteam.order_management_platform.payment.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ioteam.order_management_platform.payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, UUID>, PaymentRepositoryCustom {
	boolean existsByOrderOrderId(UUID orderId);

	Optional<Payment> findByPaymentIdAndDeletedAtIsNull(UUID paymentId);

	// 1. OWNER가 본인의 가게에 속한 결제 내역을 조회할 수 있도록 최적화
	@Query("SELECT p FROM Payment p " +
		"JOIN FETCH p.order o " +
		"JOIN FETCH o.restaurant r " +  // 주문과 연결된 레스토랑 조회
		"JOIN FETCH r.owner ro " +      // 레스토랑의 주인 정보까지 페치 조인
		"WHERE p.paymentId = :paymentId " +
		"AND p.deletedAt IS NULL")
	Optional<Payment> findPaymentWithOrderAndRestaurant(@Param("paymentId") UUID paymentId);

	// 2. CUSTOMER가 본인의 주문 내역만 조회할 수 있도록 최적화
	@Query("SELECT p FROM Payment p " +
		"JOIN FETCH p.order o " +
		"WHERE p.paymentId = :paymentId " +
		"AND o.user.userId = :userId " +
		"AND p.deletedAt IS NULL")
	Optional<Payment> findPaymentForCustomer(@Param("paymentId") UUID paymentId, @Param("userId") UUID userId);

}

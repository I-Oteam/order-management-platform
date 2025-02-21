package com.ioteam.order_management_platform.payment.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.ioteam.order_management_platform.global.entity.BaseEntity;
import com.ioteam.order_management_platform.order.entity.Order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "p_payment")
public class Payment extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID paymentId;

	@OneToOne
	@JoinColumn(name = "payment_order_id", nullable = false, unique = true)
	private Order order;

	@Column(nullable = false)
	private BigDecimal paymentTotal;

	@Column(length = 100)
	private String paymentMethod;

	@Column(length = 100)
	private String paymentNumber;

	@Enumerated(EnumType.STRING)
	@Column(length = 10, nullable = false)
	private PaymentStatusEnum paymentStatus;

	@Column
	private LocalDateTime paymentCompletedAt;

	@Column
	private LocalDateTime paymentFailedAt;

	public void setPaymentStatus(PaymentStatusEnum newStatus) {
		// 결제 상태가 'PENDING'에서 'COMPLETED'로 변경될 때 결제 완료 시간을 설정
		if (newStatus == PaymentStatusEnum.COMPLETED && this.paymentStatus != PaymentStatusEnum.COMPLETED) {
			this.paymentCompletedAt = LocalDateTime.now();
		}
		// 결제 상태가 'PENDING'에서 'FAILED'로 변경될 때 결제 실패 시간을 설정
		if (newStatus == PaymentStatusEnum.FAILED && this.paymentStatus != PaymentStatusEnum.FAILED) {
			this.paymentFailedAt = LocalDateTime.now();
		}
		// 상태 변경
		this.paymentStatus = newStatus;
	}
}

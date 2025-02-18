package com.ioteam.order_management_platform.payment.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.ioteam.order_management_platform.global.entity.BaseEntity;
import com.ioteam.order_management_platform.order.entity.Order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

	@Column(length = 100)
	private String paymentStatus;

	private LocalDateTime deletedAt;

	private UUID deletedBy;

	public void softDelete() {
		this.deletedAt = LocalDateTime.now();
		this.deletedBy = UUID.randomUUID();
	}

}

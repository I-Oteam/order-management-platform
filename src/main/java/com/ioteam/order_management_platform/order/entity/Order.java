package com.ioteam.order_management_platform.order.entity;

import com.ioteam.order_management_platform.global.entity.BaseEntity;
import com.ioteam.order_management_platform.order.enums.OrderStatus;
import com.ioteam.order_management_platform.order.enums.OrderType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_order")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID orderId;

	@Column(nullable = false, length = 100)
	private String orderUserId;

	@Column(nullable = false, length = 100)
	private String orderResId;

	@Column(nullable = false)
	private BigDecimal orderResTotal;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrderType orderType;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrderStatus orderStatus;

	@Column(length = 100)
	private String orderLocation;

	@Column(columnDefinition = "TEXT")
	private String orderRequest;

	@Column
	private LocalDateTime deletedAt;

	@Column
	private UUID deletedBy;

	//주문 메뉴 추가 필요

}

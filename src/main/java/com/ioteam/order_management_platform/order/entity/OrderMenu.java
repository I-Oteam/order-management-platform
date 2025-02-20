//package com.ioteam.order_management_platform.order.entity;
//
//
//import jakarta.persistence.*;
//import lombok.*;
//import org.springframework.data.annotation.Id;
//
//import java.math.BigDecimal;
//import java.util.UUID;
//
//@Entity
//@Table(name = "p_order_menu")
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor(access = AccessLevel.PROTECTED)
//@Builder
//public class OrderMenu extends Order {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.UUID)
//	private UUID orderMenuId;
//
//	@Column(nullable = false, length = 100)
//	private String orderRmId;
//
//	@Column(nullable = false, length = 100)
//	private String orderId;
//
//	@Column(nullable = false)
//	private BigDecimal orderPrice;
//
/// /	@Column
/// /	private LocalDateTime deletedAt;
/// /
/// /	@Column
/// /	private UUID deletedBy;
//}

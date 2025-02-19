package com.ioteam.order_management_platform.order.entity;

import com.ioteam.order_management_platform.global.entity.BaseEntity;
import com.ioteam.order_management_platform.order.dto.req.ModifyOrderRequestDto;
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

//	@Column
//	private LocalDateTime deletedAt;
//
//	@Column
//	private UUID deletedBy;

	//주문 상태
	//5분 초과 시 취소
	public void orderCancel() {
		if (this.orderStatus == OrderStatus.WAITING && this.getCreatedAt().isBefore(LocalDateTime.now().minusMinutes(5))) {
			this.orderStatus = OrderStatus.CANCELED;
		}
	}

	//주문 성공
	public void orderConfirm() {
		if (this.orderStatus == OrderStatus.WAITING) {
			this.orderStatus = OrderStatus.CONFIRMED;
		}
	}

	//취소
	public void modify(ModifyOrderRequestDto requestDto) {
		this.orderId = requestDto.getOrderId();
		this.orderUserId = requestDto.getUserId();
		this.orderResId = requestDto.getResId();
		this.orderResTotal = requestDto.getResTotal();
		this.orderStatus = requestDto.getStatus();
		this.orderType = requestDto.getType();
		this.orderLocation = requestDto.getOrderLocation();
		this.orderRequest = requestDto.getOrderRequest();
	}


}

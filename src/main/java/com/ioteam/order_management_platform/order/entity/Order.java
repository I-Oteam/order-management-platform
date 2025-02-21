package com.ioteam.order_management_platform.order.entity;

import com.ioteam.order_management_platform.global.entity.BaseEntity;
import com.ioteam.order_management_platform.order.dto.req.CancelOrderRequestDto;
import com.ioteam.order_management_platform.order.enums.OrderStatus;
import com.ioteam.order_management_platform.order.enums.OrderType;
import com.ioteam.order_management_platform.restaurant.entity.Restaurant;
import com.ioteam.order_management_platform.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_res_id")
	private Restaurant restaurant;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	@Builder.Default
	private List<OrderMenu> orderMenus = new ArrayList<>();

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


	//주문 상태
	//주문 취소(5분 안에)
	public void orderCancel(CancelOrderRequestDto requestDto) {
		this.orderStatus = OrderStatus.CANCELED;
	}

	//주문 성공
	public void orderConfirm() {
		if (this.orderStatus == OrderStatus.WAITING) {
			this.orderStatus = OrderStatus.COMPLETED;
		}
	}


}

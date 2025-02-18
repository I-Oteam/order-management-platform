package com.ioteam.order_management_platform.order.repository;

import com.ioteam.order_management_platform.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

}

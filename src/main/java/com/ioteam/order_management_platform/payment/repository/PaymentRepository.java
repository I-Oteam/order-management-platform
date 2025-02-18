package com.ioteam.order_management_platform.payment.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ioteam.order_management_platform.payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
}

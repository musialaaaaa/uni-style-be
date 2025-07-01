package org.example.uni_style_be.repositories;

import org.example.uni_style_be.entities.Order;
import org.example.uni_style_be.entities.Payment;
import org.example.uni_style_be.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findFirstByOrderAndPaymentLinkId(Order order, String paymentLinkId);

    List<Payment> findByOrderAndStatus(Order order, PaymentStatus status);


}
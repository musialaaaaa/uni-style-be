package org.example.uni_style_be.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.uni_style_be.enums.PaymentMethod;
import org.example.uni_style_be.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "payments")
public class Payment extends BaseEntity {

    @Column(name = "order_id", nullable = false)
    Long orderId;

    @Column(name = "payment_method", nullable = false)
    @Enumerated(EnumType.STRING)
    PaymentMethod paymentMethod;

    @Column(name = "payment_amount", precision = 10, scale = 2)
    BigDecimal paymentAmount;

    @Column(name = "payment_time")
    LocalDateTime paymentTime;

    @Column(name = "transaction_code", length = 100)
    String transactionCode;

    @Column(name = "note", columnDefinition = "TEXT")
    String note;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    PaymentStatus status;
}

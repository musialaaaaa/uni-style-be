package org.example.uni_style_be.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.uni_style_be.enums.DiscountType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "coupon")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Coupon {
    @Id
    @Column(name = "voucher_id")
    private String voucherId;

    private String code;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type")
    private DiscountType discountType;

    private BigDecimal value;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "usage_limit")
    private Integer usageLimit;

    private Integer used;

    private Boolean isDeleted;
}
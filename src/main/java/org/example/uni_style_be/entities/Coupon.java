package org.example.uni_style_be.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.uni_style_be.enums.DiscountType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Coupon extends BaseEntity {

    @Column(nullable = false, unique = true)
    String code;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type")
    DiscountType discountType;

    BigDecimal value;

    @Column(name = "expiration_date")
    LocalDate expirationDate;

    @Column(name = "usage_limit")
    Integer usageLimit;

    Integer used;

    @PrePersist
    public void prePersist() {
        this.used = 0;
    }
}
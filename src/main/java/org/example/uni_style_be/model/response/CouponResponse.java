package org.example.uni_style_be.model.response;

import lombok.*;
import org.example.uni_style_be.enums.DiscountType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CouponResponse {
    private String voucherId;
    private DiscountType discountType;
    private BigDecimal value;
    private LocalDateTime expirationDate;
    private Integer usageLimit;
}


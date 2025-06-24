package org.example.uni_style_be.model.response;

import lombok.Data;
import org.example.uni_style_be.enums.DiscountType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CouponResponse {
    private String voucherId;
    private String code;
    private DiscountType discountType;
    private BigDecimal value;
    private LocalDate expirationDate;
    private Integer usageLimit;
    private Integer used;
}

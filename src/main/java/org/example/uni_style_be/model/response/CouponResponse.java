package org.example.uni_style_be.model.response;

import lombok.Getter;
import lombok.Setter;
import org.example.uni_style_be.enums.DiscountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class CouponResponse extends BaseResponse {
    private DiscountType discountType;
    private BigDecimal value;
    private LocalDateTime expirationDate;
    private Integer usageLimit;
}


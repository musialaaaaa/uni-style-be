package org.example.uni_style_be.model.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.uni_style_be.enums.DiscountType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CouponResponse extends BaseResponse {
    String code;
    DiscountType discountType;
    BigDecimal value;
    LocalDate expirationDate;
    Integer usageLimit;
    Integer used;
}


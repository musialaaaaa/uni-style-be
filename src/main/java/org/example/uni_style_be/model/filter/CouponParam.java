package org.example.uni_style_be.model.filter;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.uni_style_be.enums.DiscountType;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CouponParam {
    String code;
    DiscountType discountType;
}

package org.example.uni_style_be.model.filter;

import lombok.Data;
import org.example.uni_style_be.enums.DiscountType;

@Data
public class CouponParam {
    private String code;
    private DiscountType discountType;
    private Boolean isDeleted = false;
}

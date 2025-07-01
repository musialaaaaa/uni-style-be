package org.example.uni_style_be.mapper;

import org.example.uni_style_be.entities.Coupon;
import org.example.uni_style_be.model.request.CouponRequest;
import org.example.uni_style_be.model.response.CouponResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface CouponMapper {

    CouponResponse toCouponResponse(Coupon coupon);

    @AfterMapping
    default void afterMappingToCouponResponse(Coupon coupon, @MappingTarget CouponResponse couponResponse) {
        couponResponse.setExpirationDate(coupon.getExpirationDate().atStartOfDay());
    }

    Coupon toCoupon(CouponRequest couponRequest);
}

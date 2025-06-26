package org.example.uni_style_be.service;

import org.example.uni_style_be.entities.Coupon;
import org.example.uni_style_be.model.filter.CouponParam;
import org.example.uni_style_be.model.request.CouponRequest;
import org.example.uni_style_be.model.response.CouponResponse;
import org.springframework.data.domain.*;

import java.math.BigDecimal;

public interface CouponService {
    CouponResponse create(CouponRequest request);
    CouponResponse update(String id, CouponRequest request);
    void delete(String id);
    Page<CouponResponse> filter(CouponParam param, Pageable pageable);
    Coupon findById(String id);
    BigDecimal applyDiscount(String code, BigDecimal total);
}

package org.example.uni_style_be.service;

import org.example.uni_style_be.model.filter.CouponParam;
import org.example.uni_style_be.model.request.CouponRequest;
import org.example.uni_style_be.model.response.CouponResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface CouponService {
    CouponResponse create(CouponRequest request);

    CouponResponse update(Long id, CouponRequest request);

    void delete(Long id);
    Page<CouponResponse> filter(CouponParam param, Pageable pageable);

    CouponResponse detail(Long id);

    BigDecimal applyDiscount(String code, BigDecimal total);
}

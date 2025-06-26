package org.example.uni_style_be.controller;

import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.entities.Coupon;
import org.example.uni_style_be.model.filter.CouponParam;
import org.example.uni_style_be.model.request.CouponRequest;
import org.example.uni_style_be.model.response.CouponResponse;
import org.example.uni_style_be.service.CouponService;
import org.example.uni_style_be.utils.PageUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping("/add")
    public ResponseEntity<CouponResponse> create(@RequestBody CouponRequest request) {
        return ResponseEntity.ok(couponService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CouponResponse> update(@PathVariable String id, @RequestBody CouponRequest request) {
        return ResponseEntity.ok(couponService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        couponService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PageUtils<CouponResponse>> filter(
            @ModelAttribute CouponParam param,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(new PageUtils<>(couponService.filter(param, pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CouponResponse> getById(@PathVariable String id) {
        Coupon coupon = couponService.findById(id);
        return ResponseEntity.ok(new CouponResponse(
                coupon.getVoucherId(),
                coupon.getDiscountType(),
                coupon.getValue(),
                coupon.getExpirationDate().atStartOfDay(),
                coupon.getUsageLimit()
        ));
    }

    @GetMapping("/apply-discount")
    public ResponseEntity<BigDecimal> applyDiscount(
            @RequestParam String code,
            @RequestParam BigDecimal total
    ) {
        return ResponseEntity.ok(couponService.applyDiscount(code, total));
    }
}


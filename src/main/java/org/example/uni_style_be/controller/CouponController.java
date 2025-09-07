package org.example.uni_style_be.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.model.filter.CouponParam;
import org.example.uni_style_be.model.request.CouponRequest;
import org.example.uni_style_be.model.response.CouponResponse;
import org.example.uni_style_be.model.response.ServiceResponse;
import org.example.uni_style_be.service.CouponService;
import org.example.uni_style_be.utils.PageUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
@Tag(name = "Api mã khuyến mại")
public class CouponController {

    private final CouponService couponService;

    @PostMapping
    public ResponseEntity<CouponResponse> create(@RequestBody CouponRequest request) {
        return ResponseEntity.ok(couponService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CouponResponse> update(@PathVariable Long id, @RequestBody CouponRequest request) {
        return ResponseEntity.ok(couponService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
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
    public ServiceResponse<CouponResponse> getById(@PathVariable Long id) {
        return ServiceResponse.ok(couponService.detail(id));
    }

    @GetMapping("/apply-discount")
    public ResponseEntity<BigDecimal> applyDiscount(
            @RequestParam String code,
            @RequestParam BigDecimal total
    ) {
        return ResponseEntity.ok(couponService.applyDiscount(code, total));
    }
}


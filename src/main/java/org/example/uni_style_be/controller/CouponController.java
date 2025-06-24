package org.example.uni_style_be.controller;

import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.model.filter.CouponParam;
import org.example.uni_style_be.model.request.CouponRequest;
import org.example.uni_style_be.model.response.CouponResponse;
import org.example.uni_style_be.service.CouponService;
import org.example.uni_style_be.utils.PageUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping(value = "/add")
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
}

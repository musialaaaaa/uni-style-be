package org.example.uni_style_be.service.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.entities.Coupon;
import org.example.uni_style_be.enums.CommonError;
import org.example.uni_style_be.enums.DiscountType;
import org.example.uni_style_be.enums.NotFoundError;
import org.example.uni_style_be.exception.ResponseException;
import org.example.uni_style_be.mapper.CouponMapper;
import org.example.uni_style_be.model.filter.CouponParam;
import org.example.uni_style_be.model.request.CouponRequest;
import org.example.uni_style_be.model.response.CouponResponse;
import org.example.uni_style_be.repositories.CouponRepository;
import org.example.uni_style_be.repositories.specification.CouponSpecification;
import org.example.uni_style_be.service.CouponService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepo;
    private final ObjectMapper objectMapper;
    private final CouponMapper couponMapper;

    @Override
    @Transactional
    public CouponResponse create(CouponRequest request) {
        Coupon coupon = couponMapper.toCoupon(request);
        Coupon result = couponRepo.save(coupon);
        return couponMapper.toCouponResponse(result);
    }

    @Override
    public CouponResponse update(Long id, CouponRequest request) {
        Coupon coupon = findById(id);
        try {
            objectMapper.updateValue(coupon, request);
        } catch (JsonMappingException e) {
            throw new ResponseException(CommonError.INVALID_DATA);

        }
        return objectMapper.convertValue(couponRepo.save(coupon), CouponResponse.class);
    }

    @Override
    public void delete(Long id) {
        findById(id);
        couponRepo.deleteById(id);
    }

    @Override
    public Page<CouponResponse> filter(CouponParam param, Pageable pageable) {
        var spec = CouponSpecification.filterSpec(param);
        var page = couponRepo.findAll(spec, pageable);
        return page.map(c -> objectMapper.convertValue(c, CouponResponse.class));
    }

    @Override
    public CouponResponse detail(Long id) {
        Coupon coupon = findById(id);
        return couponMapper.toCouponResponse(coupon);
    }

    @Override
    public BigDecimal applyDiscount(String code, BigDecimal total) {
        Coupon coupon = couponRepo.findByCode(code)
                .orElseThrow(() -> new ResponseException(NotFoundError.DATA_NOT_FOUND));

        if (coupon.getExpirationDate().isBefore(LocalDate.now()))
            throw new ResponseException(CommonError.COUPON_EXPIRED);

        if (coupon.getUsageLimit() != null && coupon.getUsed() >= coupon.getUsageLimit())
            throw new ResponseException(CommonError.COUPON_LIMIT_REACHED);


        BigDecimal discount = coupon.getDiscountType() == DiscountType.PERCENT
                ? total.multiply(coupon.getValue()).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
                : coupon.getValue();

        return total.subtract(discount.min(total));
    }

    private Coupon findById(Long id) {
        return couponRepo.findById(id)
                .orElseThrow(() -> new ResponseException(NotFoundError.DATA_NOT_FOUND));
    }
}

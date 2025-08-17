package org.example.uni_style_be.repositories.specification;

import jakarta.persistence.criteria.Predicate;
import org.example.uni_style_be.entities.Coupon;
import org.example.uni_style_be.model.filter.CouponParam;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CouponSpecification {
    public static Specification<Coupon> filterSpec(CouponParam param) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (param.getCode() != null) {
                predicates.add(cb.like(root.get("code"), "%" + param.getCode() + "%"));
            }
            if (param.getDiscountType() != null) {
                predicates.add(cb.equal(root.get("discountType"), param.getDiscountType()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

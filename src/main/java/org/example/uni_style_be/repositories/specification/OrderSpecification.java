package org.example.uni_style_be.repositories.specification;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.uni_style_be.entities.Order;
import org.example.uni_style_be.model.filter.OrderParam;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

public class OrderSpecification {
    public static Specification<Order> filterSpec(OrderParam param) {
        return (Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();

            if (Objects.nonNull(param.getCode())) {
                predicate = cb.and(predicate, cb.equal(root.get("code"), param.getCode()));
            }

            if (StringUtils.isNotEmpty(param.getPhoneNumber())) {
                predicate = cb.and(predicate, cb.equal(root.get("phoneNumber"), param.getPhoneNumber()));
            }

            if (Objects.nonNull(param.getStatus())) {
                predicate = cb.and(predicate, cb.equal(root.get("status"), param.getStatus()));
            }

            return predicate;
        };
    }
}

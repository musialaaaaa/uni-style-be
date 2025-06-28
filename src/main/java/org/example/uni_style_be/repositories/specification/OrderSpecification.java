package org.example.uni_style_be.repositories.specification;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.uni_style_be.entities.Order;
import org.example.uni_style_be.model.filter.OrderParam;
import org.springframework.data.jpa.domain.Specification;

public class OrderSpecification {
    public static Specification<Order> filterSpec(OrderParam param) {
        return (Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();

            // Lọc theo orderDate (nếu có)
            if (param.getOrderDate() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("orderDate"), param.getOrderDate()));
            }

            // Lọc theo totalAmount (nếu có)
            if (param.getTotalAmount() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("totalAmount"), param.getTotalAmount()));
            }

            // Lọc theo status (nếu có, không phân biệt hoa thường)
            if (StringUtils.isNotBlank(param.getStatus())) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("status")), "%" + param.getStatus().toLowerCase() + "%"));
            }

            // Lọc theo shippingAddress (nếu có, không phân biệt hoa thường)
            if (StringUtils.isNotBlank(param.getShippingAddress())) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("shippingAddress")), "%" + param.getShippingAddress().toLowerCase() + "%"));
            }

            return predicate;
        };
    }
}

package org.example.uni_style_be.repositories.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.example.uni_style_be.entities.Customer;
import org.example.uni_style_be.model.filter.CustomerParam;
import org.springframework.data.jpa.domain.Specification;

public class CustomerSpecification {

    public static Specification<Customer> filterSpec(CustomerParam param) {
        return (Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();

            if (StringUtils.isNotBlank(param.getCode())) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("code")), "%" + param.getCode().toLowerCase() + "%"));
            }

            if (StringUtils.isNotBlank(param.getFullName())) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("fullName")), "%" + param.getFullName().toLowerCase() + "%"));
            }

            if (StringUtils.isNotBlank(param.getEmail())) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("email")), "%" + param.getEmail().toLowerCase() + "%"));
            }

            if (StringUtils.isNotBlank(param.getPhoneNumber())) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("phoneNumber")), "%" + param.getPhoneNumber().toLowerCase() + "%"));
            }

            return predicate;
        };
    }
}

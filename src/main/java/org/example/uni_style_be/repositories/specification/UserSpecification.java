package org.example.uni_style_be.repositories.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.example.uni_style_be.entities.User;
import org.example.uni_style_be.model.filter.UserParam;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<User> filterSpec(UserParam param) {
        return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();


            if (StringUtils.isNotBlank(param.getName())) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("fullName")), "%" + param.getName().toLowerCase() + "%"));
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

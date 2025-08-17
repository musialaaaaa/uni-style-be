package org.example.uni_style_be.repositories.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.example.uni_style_be.entities.Brand;
import org.example.uni_style_be.model.filter.BrandParam;
import org.springframework.data.jpa.domain.Specification;

public class BrandSpecification {
    public static Specification<Brand> filterSpec(BrandParam param) {
        return (Root<Brand> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();

            if (StringUtils.isNotBlank(param.getName())) {
                predicate =
                        cb.and(
                                predicate,
                                cb.like(cb.lower(root.get("name")), "%" + param.getName().toLowerCase() + "%"));
            }

            if (StringUtils.isNotBlank(param.getCode())) {
                predicate =
                        cb.and(
                                predicate,
                                cb.like(cb.lower(root.get("code")), "%" + param.getCode().toLowerCase() + "%"));
            }

            return predicate;
        };
    }
}

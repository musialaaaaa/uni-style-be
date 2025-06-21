package org.example.uni_style_be.repositories.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.example.uni_style_be.entities.Category;
import org.example.uni_style_be.model.filter.CategoryParam;
import org.springframework.data.jpa.domain.Specification;

public class CategorySpecification {
    public static Specification<Category> filterSpec(CategoryParam param) {
        return (Root<Category> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();

            if (StringUtils.isNotBlank(param.getName())) {
                predicate =
                        cb.and(
                                predicate,
                                cb.like(cb.lower(root.get("name")), "%" + param.getName().toLowerCase() + "%"));
            }
            return predicate;
        };
    }
}

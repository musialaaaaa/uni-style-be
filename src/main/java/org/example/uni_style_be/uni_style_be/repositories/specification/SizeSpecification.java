package org.example.uni_style_be.uni_style_be.repositories.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.example.uni_style_be.uni_style_be.entities.Size;
import org.example.uni_style_be.uni_style_be.model.filter.SizeParam;
import org.springframework.data.jpa.domain.Specification;

public class SizeSpecification {
  public static Specification<Size> filterSpec(SizeParam param) {
    return (Root<Size> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
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

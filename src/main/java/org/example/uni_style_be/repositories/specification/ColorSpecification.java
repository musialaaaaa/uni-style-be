package org.example.uni_style_be.repositories.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.example.uni_style_be.entities.Color;
import org.example.uni_style_be.model.filter.ColorParam;
import org.springframework.data.jpa.domain.Specification;

public class ColorSpecification {
  public static Specification<Color> filterSpec(ColorParam param) {
    return (Root<Color> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
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

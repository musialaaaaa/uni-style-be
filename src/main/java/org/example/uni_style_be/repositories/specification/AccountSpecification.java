package org.example.uni_style_be.repositories.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.uni_style_be.entities.Account;
import org.example.uni_style_be.model.filter.AccountParam;
import org.springframework.data.jpa.domain.Specification;

public class AccountSpecification {
    public static Specification<Account> filterSpec(AccountParam param) {
        return (Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();

            if (param.getKeyword() != null) {
                Predicate usernamePredicate = cb.like(cb.upper(root.get("username")), "%" + param.getKeyword() + "%");
                Predicate fullNamePredicate = cb.like(cb.upper(root.get("fullName")), "%" + param.getKeyword() + "%");
                Predicate emailPredicate = cb.like(cb.upper(root.get("email")), "%" + param.getKeyword() + "%");
                Predicate phonePredicate = cb.like(cb.upper(root.get("phone")), "%" + param.getKeyword() + "%");

                Predicate keywordPredicate = cb.or(usernamePredicate, fullNamePredicate, emailPredicate, phonePredicate);

                predicate = cb.and(predicate, keywordPredicate);
            }

            if (param.getType() != null) {
                Predicate typePredicate = cb.equal(root.get("type"), param.getType());
                predicate = cb.and(predicate, typePredicate);
            }

            return predicate;
        };
    }
}

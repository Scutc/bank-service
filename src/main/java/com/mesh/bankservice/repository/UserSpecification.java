package com.mesh.bankservice.repository;

import com.mesh.bankservice.model.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserSpecification {

    public static Specification<User> build(String phone, String email, String name, LocalDate dateOfBirth) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (phone != null) {
                predicates.add(criteriaBuilder.equal(root.join("phones", JoinType.LEFT).get("phone"), phone));
            }

            if (email != null) {
                predicates.add(criteriaBuilder.equal(root.join("emails", JoinType.LEFT).get("email"), email));
            }

            if (name != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }

            if (dateOfBirth != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dateOfBirth"), dateOfBirth));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

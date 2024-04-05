package com.mesh.bankservice.repository;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.mesh.bankservice.model.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {
    public static Specification<User> findByParams(String phoneNumber, String email, String name, LocalDate dateOfBirth) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (phoneNumber != null) {
                predicates.add(cb.equal(root.get("phone"), phoneNumber));
            }
            if (email != null) {
                predicates.add(cb.equal(root.get("email"), email));
            }
            if (name != null) {
                predicates.add(cb.like(root.get("name"), name + "%"));
            }
            if (dateOfBirth != null) {
                predicates.add(cb.greaterThan(root.get("dateOfBirth"), dateOfBirth));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

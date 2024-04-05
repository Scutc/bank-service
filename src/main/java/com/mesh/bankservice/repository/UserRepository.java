package com.mesh.bankservice.repository;

import java.util.Optional;

import com.mesh.bankservice.model.User;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Nullable
    User findByEmail(String email);
}

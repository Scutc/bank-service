package com.mesh.bankservice.repository;

import java.util.Optional;

import com.mesh.bankservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT e.user FROM EmailData e WHERE e.email = :email")
    Optional<User> findByEmail(@Param("email") String email);
}

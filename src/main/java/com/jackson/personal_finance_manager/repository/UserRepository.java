package com.jackson.personal_finance_manager.repository;

import com.jackson.personal_finance_manager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<Object> findByUsername(String username);

    Optional<Object> findByEmail(String email);
}

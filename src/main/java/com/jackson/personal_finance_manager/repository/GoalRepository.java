package com.jackson.personal_finance_manager.repository;

import com.jackson.personal_finance_manager.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {
}

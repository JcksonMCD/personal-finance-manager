package com.jackson.personal_finance_manager.repository;

import com.jackson.personal_finance_manager.model.Goal;
import com.jackson.personal_finance_manager.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class GoalRepositoryTest {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setName("Test User");
        userRepository.save(user);
    }

    // Helper method for goal initialisation
    private Goal createGoal(String name, BigDecimal targetAmount, BigDecimal savedAmount, int deadlineDays) {
        Goal goal = new Goal();
        goal.setUser(user);
        goal.setName(name);
        goal.setTargetAmount(targetAmount);
        goal.setSavedAmount(savedAmount);
        goal.setDeadline(LocalDateTime.now().plusDays(deadlineDays));
        return goal;
    }

}
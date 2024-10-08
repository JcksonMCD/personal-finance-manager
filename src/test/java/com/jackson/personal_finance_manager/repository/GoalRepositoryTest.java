package com.jackson.personal_finance_manager.repository;

import com.jackson.personal_finance_manager.model.Goal;
import com.jackson.personal_finance_manager.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
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
        user.setEmail("test@example.com");
        user.setUsername("testuser");
        user.setPasswordHash("encryptedPassword");
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

    @Test
    void testSaveGoal() {
        // Arrange
        Goal goal = createGoal("Test Goal", BigDecimal.valueOf(1000.00), BigDecimal.valueOf(500.00), 10);

        // Act
        Goal savedGoal = goalRepository.save(goal);

        // Assert
        assertNotNull(savedGoal.getGoalID());
        assertEquals("Test Goal", savedGoal.getName());
    }

    @Test
    void testFindById() {
        // Arrange
        Goal goal = createGoal("Find By ID Goal", BigDecimal.valueOf(2000.00), BigDecimal.valueOf(1500.00), 15);
        Goal savedGoal = goalRepository.save(goal);

        // Act
        Optional<Goal> foundGoal = goalRepository.findById(savedGoal.getGoalID());

        // Assert
        assertTrue(foundGoal.isPresent());
        assertEquals("Find By ID Goal", foundGoal.get().getName());
    }

    @Test
    void testFindAllGoals() {
        // Arrange
        goalRepository.save(createGoal("Goal 1", BigDecimal.valueOf(1000.00), BigDecimal.valueOf(500.00), 10));
        goalRepository.save(createGoal("Goal 2", BigDecimal.valueOf(2000.00), BigDecimal.valueOf(1500.00), 15));

        // Act
        List<Goal> goals = goalRepository.findAll();

        // Assert
        assertEquals(2, goals.size());
    }

    @Test
    void testDeleteGoal() {
        // Arrange
        Goal goal = createGoal("Goal to Delete", BigDecimal.valueOf(500.00), BigDecimal.valueOf(100.00), 5);
        Goal savedGoal = goalRepository.save(goal);
        Long goalId = savedGoal.getGoalID();

        // Act
        goalRepository.deleteById(goalId);
        Optional<Goal> deletedGoal = goalRepository.findById(goalId);

        // Assert
        assertFalse(deletedGoal.isPresent());
    }

    @Test
    void testUpdateGoal() {
        // Arrange
        Goal goal = createGoal("Goal to Update", BigDecimal.valueOf(3000.00), BigDecimal.valueOf(1000.00), 20);
        Goal savedGoal = goalRepository.save(goal);

        // Act
        savedGoal.setName("Updated Goal");
        savedGoal.setSavedAmount(BigDecimal.valueOf(2000.00));
        goalRepository.save(savedGoal);

        Optional<Goal> updatedGoal = goalRepository.findById(savedGoal.getGoalID());

        // Assert
        assertTrue(updatedGoal.isPresent());
        assertEquals("Updated Goal", updatedGoal.get().getName());
        assertEquals(BigDecimal.valueOf(2000.00), updatedGoal.get().getSavedAmount());
    }
}
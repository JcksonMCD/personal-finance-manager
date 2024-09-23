package com.jackson.personal_finance_manager.service;

import com.jackson.personal_finance_manager.model.Goal;
import com.jackson.personal_finance_manager.repository.GoalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GoalServiceImplTest {

    @Mock
    private GoalRepository goalRepository;

    @InjectMocks
    private GoalServiceImpl goalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveGoal() {
        Goal goal = new Goal();
        goal.setName("Test Goal");
        goal.setTargetAmount(BigDecimal.valueOf(1000.00));
        goal.setSavedAmount(BigDecimal.valueOf(500.00));
        goal.setDeadline(LocalDateTime.now().plusDays(10));

        when(goalRepository.save(goal)).thenReturn(goal);

        Goal savedGoal = goalService.saveGoal(goal);
        assertNotNull(savedGoal);
        assertEquals(goal.getName(), savedGoal.getName());
    }

    @Test
    void testGetGoalById() {
        Goal goal = new Goal();
        goal.setName("Test Goal");
        goal.setTargetAmount(BigDecimal.valueOf(1000.00));
        goal.setSavedAmount(BigDecimal.valueOf(500.00));
        goal.setDeadline(LocalDateTime.now().plusDays(10));

        when(goalRepository.findById(1L)).thenReturn(Optional.of(goal));

        Optional<Goal> foundGoal = goalService.getGoalById(1L);
        assertNotNull(foundGoal);
        assertEquals(goal.getName(), foundGoal.orElse(null).getName());
    }

    @Test
    void testGetAllGoals() {
        // Arrange: Mock data for goals
        Goal goal1 = new Goal();
        goal1.setGoalID(1L);
        goal1.setName("Goal 1");
        goal1.setTargetAmount(BigDecimal.valueOf(1000.00));
        goal1.setSavedAmount(BigDecimal.valueOf(200.00));
        goal1.setDeadline(LocalDate.of(2024, 12, 31).atStartOfDay());
        goal1.setCreatedAt(LocalDateTime.now());

        Goal goal2 = new Goal();
        goal2.setGoalID(2L);
        goal2.setName("Goal 2");
        goal2.setTargetAmount(BigDecimal.valueOf(500.00));
        goal2.setSavedAmount(BigDecimal.valueOf(100.00));
        goal2.setDeadline(LocalDate.of(2024, 6, 30).atStartOfDay());
        goal2.setCreatedAt(LocalDateTime.now());

        List<Goal> mockGoals = Arrays.asList(goal1, goal2);

        // Mock the repository to return the mocked list of goals
        when(goalRepository.findAll()).thenReturn(mockGoals);

        // Act:
        List<Goal> result = goalService.getAllGoals();

        // Assert:
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Goal 1", result.get(0).getName());
        assertEquals("Goal 2", result.get(1).getName());

        verify(goalRepository, times(1)).findAll();
    }

}
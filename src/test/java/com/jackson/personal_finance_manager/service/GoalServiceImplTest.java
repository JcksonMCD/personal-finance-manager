package com.jackson.personal_finance_manager.service;

import com.jackson.personal_finance_manager.model.Goal;
import com.jackson.personal_finance_manager.repository.GoalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
}
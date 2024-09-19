package com.jackson.personal_finance_manager.controller;

import com.jackson.personal_finance_manager.model.Goal;
import com.jackson.personal_finance_manager.service.GoalService;
import com.jackson.personal_finance_manager.service.GoalServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GoalController.class)
class GoalControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private GoalService goalService;

    @InjectMocks
    private GoalController goalController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(goalController).build();
    }

    @Test
    void testCreateGoal() throws Exception {
        Goal goal = new Goal();
        goal.setName("Test Goal");
        goal.setTargetAmount(BigDecimal.valueOf(1000.00));
        goal.setSavedAmount(BigDecimal.valueOf(500.00));
        goal.setDeadline(LocalDateTime.now().plusDays(10));

        when(goalService.saveGoal(any(Goal.class))).thenReturn(goal);

        mockMvc.perform(post("/api/goals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Goal\",\"targetAmount\":1000.00,\"savedAmount\":500.00,\"deadline\":\"2024-10-01T00:00:00\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Goal"));
    }

    @Test
    void testGetGoalById() throws Exception {

    }
}
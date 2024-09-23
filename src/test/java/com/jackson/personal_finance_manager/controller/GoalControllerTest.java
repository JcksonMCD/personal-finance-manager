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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
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
        Goal goal = new Goal();
        goal.setName("Test Goal");
        goal.setTargetAmount(BigDecimal.valueOf(1000.00));
        goal.setSavedAmount(BigDecimal.valueOf(500.00));
        goal.setDeadline(LocalDateTime.now().plusDays(10));

        when(goalService.getGoalById(1L)).thenReturn(Optional.of(goal));

        mockMvc.perform(get("/api/goals/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Goal"));
    }

    @Test
    void testGetAllGoals() throws Exception {
        // Arrange:
        Goal goal1 = new Goal();
        goal1.setGoalID(1L);
        goal1.setName("Goal 1");
        goal1.setTargetAmount(new BigDecimal("1000.00"));
        goal1.setSavedAmount(new BigDecimal("200.00"));
        goal1.setDeadline(LocalDate.of(2024, 12, 31).atStartOfDay());
        goal1.setCreatedAt(LocalDateTime.now());

        List<Goal> mockGoals = Arrays.asList(goal1);

        // Mock the service method
        when(goalService.getAllGoals()).thenReturn(mockGoals);

        // Act & Assert:
        mockMvc.perform(get("/api/goals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].goalID").value(1))
                .andExpect(jsonPath("$[0].name").value("Goal 1"))
                .andExpect(jsonPath("$[0].targetAmount").value(1000.00))
                .andExpect(jsonPath("$[0].savedAmount").value(200.00))
                .andExpect(jsonPath("$[0].deadline").exists())
                .andExpect(jsonPath("$[0].createdAt").exists());
    }

}
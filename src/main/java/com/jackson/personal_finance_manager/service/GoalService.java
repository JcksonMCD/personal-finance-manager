package com.jackson.personal_finance_manager.service;

import com.jackson.personal_finance_manager.model.Goal;

import java.util.Optional;

public interface GoalService {
    Goal saveGoal(Goal goal);

    Optional<Goal> getGoalById(Long goalId);
}

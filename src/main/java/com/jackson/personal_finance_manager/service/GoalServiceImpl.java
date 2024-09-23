package com.jackson.personal_finance_manager.service;

import com.jackson.personal_finance_manager.model.Goal;
import com.jackson.personal_finance_manager.repository.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoalServiceImpl implements GoalService{

    @Autowired
    private GoalRepository goalRepository;

    @Override
    public Goal saveGoal(Goal goal) {
        return goalRepository.save(goal);
    }

    @Override
    public Optional<Goal> getGoalById(Long goalId) {
        return goalRepository.findById(goalId);
    }

    @Override
    public List<Goal> getAllGoals() {
        return goalRepository.findAll();
    }
}

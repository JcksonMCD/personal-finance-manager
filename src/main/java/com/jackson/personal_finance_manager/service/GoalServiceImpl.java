package com.jackson.personal_finance_manager.service;

import com.jackson.personal_finance_manager.model.Goal;
import com.jackson.personal_finance_manager.repository.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoalServiceImpl implements GoalService{

    @Autowired
    private GoalRepository goalRepository;

    @Override
    public Goal saveGoal(Goal goal) {
        return goalRepository.save(goal);
    }
}

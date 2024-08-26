package com.jackson.personal_finance_manager.service;

import com.jackson.personal_finance_manager.dto.userdtos.UserRegistrationDTO;
import com.jackson.personal_finance_manager.model.User;
import com.jackson.personal_finance_manager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(UserRegistrationDTO userRegistrationDTO) {
        User user = new User(
                LocalDateTime.now(),
                LocalDateTime.now(),
                passwordEncoder.encode(userRegistrationDTO.getPassword()),
                userRegistrationDTO.getEmail(),
                userRegistrationDTO.getName(),
                userRegistrationDTO.getUsername());
        return userRepository.save(user);
    }
}

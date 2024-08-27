package com.jackson.personal_finance_manager.service;

import com.jackson.personal_finance_manager.dto.userdtos.UserRegistrationDTO;
import com.jackson.personal_finance_manager.model.User;
import com.jackson.personal_finance_manager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserRegistrationDTO createUser(UserRegistrationDTO userRegistrationDTO) {
        // Create user using dto information as only user types can be saved in the db
        User user = new User(
                LocalDateTime.now(),
                LocalDateTime.now(),
                passwordEncoder.encode(userRegistrationDTO.getPassword()),
                userRegistrationDTO.getEmail(),
                userRegistrationDTO.getName(),
                userRegistrationDTO.getUsername());

        // Save to repository
        userRepository.save(user);

        // Return dto for memory saving and efficiency
        return userRegistrationDTO;
    }
}

package com.jackson.personal_finance_manager.service;

import com.jackson.personal_finance_manager.dto.userdtos.UserRegistrationDTO;
import com.jackson.personal_finance_manager.exception.UserNotFoundException;
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
        // Validate input data and check for existing username/email if needed
        if (userRepository.findByUsername(userRegistrationDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.findByEmail(userRegistrationDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        // Create user using dto information as only user types can be saved in the db
        User user = new User();
        user.setUsername(userRegistrationDTO.getUsername());
        user.setName(userRegistrationDTO.getName());
        user.setEmail(userRegistrationDTO.getEmail());
        user.setPasswordHash(passwordEncoder.encode(userRegistrationDTO.getPassword()));

        // Save to repository
        userRepository.save(user);

        // Return dto for memory saving and efficiency
        return userRegistrationDTO;
    }

    @Override
    public User getUserById(long userID) {
        return userRepository.findById(userID).orElseThrow(
                () -> new UserNotFoundException("No User found at id: " + userID)
        );
    }

    @Override
    public User editUser(long id, UserRegistrationDTO user) {
        // Find the user by ID
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No User found at id: " + id));

        // Update user details
        if (user.getUsername() != null) {
            existingUser.setUsername(user.getUsername());
        }
        if (user.getName() != null) {
            existingUser.setName(user.getName());
        }
        if (user.getEmail() != null) {
            existingUser.setEmail(user.getEmail());
        }
        if (user.getPassword() != null) {
            existingUser.setPasswordHash(passwordEncoder.encode(user.getPassword()));
        }

        // Save updated user
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUserById(long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("No user found at this id so no deletion has taken place.")
        );

        userRepository.delete(user);
    }
}

package com.jackson.personal_finance_manager.repository;

import com.jackson.personal_finance_manager.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setName("Test");
        user.setEmail("test@example.com");
        user.setPasswordHash("hashedpassword");

        User savedUser = userRepository.save(user);

        assertNotNull(savedUser.getUserId());
        assertNotNull(savedUser.getCreatedAt());
        assertNotNull(savedUser.getUpdatedAt());
        assertEquals(savedUser.getCreatedAt(), savedUser.getUpdatedAt());
    }
}
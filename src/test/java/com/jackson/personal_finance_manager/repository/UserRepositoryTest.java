package com.jackson.personal_finance_manager.repository;

import com.jackson.personal_finance_manager.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        // Create a new user
        User user = new User();
        user.setUsername("testuser");
        user.setName("Test");
        user.setEmail("test@example.com");
        user.setPasswordHash("hashedpassword");

        // Save the user to the repository
        User savedUser = userRepository.save(user);

        // Verify that the user was saved correctly
        assertNotNull(savedUser.getUserId(), "User ID should not be null");
        assertNotNull(savedUser.getCreatedAt(), "CreatedAt should not be null");
        assertNotNull(savedUser.getUpdatedAt(), "UpdatedAt should not be null");
        assertEquals(user.getUsername(), savedUser.getUsername(), "Usernames should match");
        assertEquals(user.getName(), savedUser.getName(), "Names should match");
        assertEquals(user.getEmail(), savedUser.getEmail(), "Emails should match");
        assertEquals(user.getPasswordHash(), savedUser.getPasswordHash(), "Password hashes should match");
    }
}
package com.jackson.personal_finance_manager.repository;

import com.jackson.personal_finance_manager.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // Resets test context
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        // Setting up test data
        User user1 = new User();
        user1.setUsername("testuser1");
        user1.setName("Test User 1");
        user1.setEmail("test1@example.com");
        user1.setPasswordHash("hashedpassword1");
        user1.setCreatedAt(LocalDateTime.now());
        user1.setUpdatedAt(LocalDateTime.now());

        User user2 = new User();
        user2.setUsername("testuser2");
        user2.setName("Test User 2");
        user2.setEmail("test2@example.com");
        user2.setPasswordHash("hashedpassword2");
        user2.setCreatedAt(LocalDateTime.now());
        user2.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user1);
        userRepository.save(user2);
    }

    @Test
    public void testSaveUser() {
        // Create a new user
        User user = new User();
        user.setUsername("newuser");
        user.setName("New User");
        user.setEmail("newuser@example.com");
        user.setPasswordHash("newhashedpassword");

        // Save the user to the repository
        User savedUser = userRepository.save(user);

        // Verify the saved user
        assertNotNull(savedUser.getUserId(), "User ID should not be null");
        assertNotNull(savedUser.getCreatedAt(), "CreatedAt should not be null");
        assertNotNull(savedUser.getUpdatedAt(), "UpdatedAt should not be null");
        assertEquals(savedUser.getCreatedAt(), savedUser.getUpdatedAt(), "CreatedAt and UpdatedAt should be the same");
    }
    @Test
    public void testFindByUsername() {
        // Test finding by username
        Optional<User> foundUser = userRepository.findByUsername("testuser1");

        assertTrue(foundUser.isPresent(), "User should be found");
        assertEquals("testuser1", foundUser.get().getUsername(), "Username should match");
        assertEquals("test1@example.com", foundUser.get().getEmail(), "Email should match");
    }


}

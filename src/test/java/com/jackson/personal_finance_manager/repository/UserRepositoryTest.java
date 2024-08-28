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

    @Test
    public void testFindByEmail() {
        // Test finding by email
        Optional<User> foundUser = userRepository.findByEmail("test2@example.com");

        assertTrue(foundUser.isPresent(), "User should be found");
        assertEquals("testuser2", foundUser.get().getUsername(), "Username should match");
        assertEquals("test2@example.com", foundUser.get().getEmail(), "Email should match");
    }

    @Test
    public void testFindByUsernameNotFound() {
        // Test finding a non-existent username
        Optional<User> foundUser = userRepository.findByUsername("nonexistentuser");

        assertFalse(foundUser.isPresent(), "User should not be found");
    }

    @Test
    public void testFindByEmailNotFound() {
        // Test finding a non-existent email
        Optional<User> foundUser = userRepository.findByEmail("nonexistent@example.com");

        assertFalse(foundUser.isPresent(), "User should not be found");
    }

    @Test
    void testFindById() {
        // Act: Retrieve the user by ID
        Optional<User> foundUser = userRepository.findById(1L);

        // Assert: Verify the retrieved user matches the saved user
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("testuser1");
        assertThat(foundUser.get().getName()).isEqualTo("Test User 1");
        assertThat(foundUser.get().getEmail()).isEqualTo("test1@example.com");
    }

    @Test
    void testFindByIdWhenUserIsNotPresent() {
        // Act: Retrieve the user by ID
        Optional<User> foundUser = userRepository.findById(10L);

        // Assert: Verify the retrieved user matches the saved user
        assertFalse(foundUser.isPresent());
    }

    @Test
    public void testUpdateUser() {
        // Retrieve the user
        User user = userRepository.findById(1L).orElse(null);
        assert user != null;

        // Update user details
        user.setUsername("updateduser");
        user.setName("Updated Name");
        user.setEmail("updated@example.com");

        // Save the updated user
        userRepository.save(user);

        // Retrieve the updated user
        User updatedUser = userRepository.findById(1L).orElse(null);
        assert updatedUser != null;

        // Verify the updated details
        assertEquals("updateduser", updatedUser.getUsername());
        assertEquals("Updated Name", updatedUser.getName());
        assertEquals("updated@example.com", updatedUser.getEmail());
    }

    @Test
    public void testDeleteUserById() {
        // Verify the user exists before deletion
        Optional<User> foundUser = userRepository.findById(1L);
        assertTrue(foundUser.isPresent(), "User should be present before deletion");

        // Delete the user
        userRepository.deleteById(1L);

        // Verify deletion
        Optional<User> deletedUser = userRepository.findById(1L);
        assertFalse(deletedUser.isPresent(), "User should not be present after deletion");
    }
}

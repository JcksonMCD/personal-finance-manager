package com.jackson.personal_finance_manager.service;

import com.jackson.personal_finance_manager.dto.userdtos.UserRegistrationDTO;
import com.jackson.personal_finance_manager.exception.UserNotFoundException;
import com.jackson.personal_finance_manager.model.User;
import com.jackson.personal_finance_manager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser_Success() {
        // Given
        UserRegistrationDTO userDTO = new UserRegistrationDTO();
        userDTO.setUsername("newuser");
        userDTO.setName("New User");
        userDTO.setEmail("newuser@example.com");
        userDTO.setPassword("password");

        // Mocking repository and encoder behavior
        when(userRepository.findByUsername(userDTO.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("encodedpassword");

        // Mocking save behavior
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        // When
        UserRegistrationDTO result = userService.createUser(userDTO);

        // Then
        assertNotNull(result, "Result should not be null");
        assertEquals("newuser", result.getUsername(), "Username should match");
        assertEquals("New User", result.getName(), "Name should match");
        assertEquals("newuser@example.com", result.getEmail(), "Email should match");
        assertEquals("password", result.getPassword(), "Password should match");

        // Verify interactions
        verify(userRepository).findByUsername(userDTO.getUsername());
        verify(userRepository).findByEmail(userDTO.getEmail());
        verify(passwordEncoder).encode(userDTO.getPassword());
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void testCreateUser_UsernameAlreadyExists() {
        // Given
        UserRegistrationDTO userDTO = new UserRegistrationDTO();
        userDTO.setUsername("existinguser");
        userDTO.setName("Existing User");
        userDTO.setEmail("existinguser@example.com");
        userDTO.setPassword("password");

        // Mocking repository behavior
        when(userRepository.findByUsername(userDTO.getUsername())).thenReturn(Optional.of(new User()));

        // When & Then
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userService.createUser(userDTO);
        });

        assertEquals("Username already exists", thrown.getMessage());

        // Verify interactions
        verify(userRepository).findByUsername(userDTO.getUsername());
        verify(userRepository, never()).findByEmail(userDTO.getEmail());
        verify(userRepository, never()).save(any(User.class));
    }
    @Test
    public void testCreateUser_EmailAlreadyExists() {
        // Given
        UserRegistrationDTO userDTO = new UserRegistrationDTO();
        userDTO.setUsername("newuser");
        userDTO.setName("New User");
        userDTO.setEmail("existingemail@example.com");
        userDTO.setPassword("password");

        // Mocking repository behavior
        when(userRepository.findByUsername(userDTO.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.of(new User()));

        // When & Then
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userService.createUser(userDTO);
        });

        assertEquals("Email already exists", thrown.getMessage());

        // Verify interactions
        verify(userRepository).findByUsername(userDTO.getUsername());
        verify(userRepository).findByEmail(userDTO.getEmail());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testGetUserById_Success() {
        // Arrange
        User user = new User();
        user.setUserId(1L);
        user.setUsername("testuser");
        user.setName("Test");
        user.setEmail("test@example.com");

        // Mocking repository behavior
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        User result = userService.getUserById(1L);

        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals(1L, result.getUserId(), "User ID should match");
        assertEquals("testuser", result.getUsername(), "Username should match");
        assertEquals("Test", result.getName(), "Name should match");
        assertEquals("test@example.com", result.getEmail(), "Email should match");

        verify(userRepository).findById(1L);
    }
    @Test
    public void testGetUserById_NotFound() {
        // Act
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Assert
        UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () -> {
            userService.getUserById(1L);
        });

        assertEquals("No User found at id: 1", thrown.getMessage());

        verify(userRepository).findById(1L);
    }

    @Test
    public void testEditUser_Success() {
        // Arrange
        long userId = 1L;
        User existingUser = new User();
        existingUser.setUserId(userId);
        existingUser.setUsername("oldusername");
        existingUser.setName("Old Name");
        existingUser.setEmail("old@example.com");
        existingUser.setPasswordHash("oldhashedpassword");

        UserRegistrationDTO userDTO = new UserRegistrationDTO();
        userDTO.setUsername("newusername");
        userDTO.setName("New Name");
        userDTO.setEmail("new@example.com");
        userDTO.setPassword("newpassword");

        // Mock repository and encoder behavior
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("newhashedpassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        // Act
        User updatedUser = userService.editUser(userId, userDTO);

        // Assert
        assertNotNull(updatedUser, "Updated user should not be null");
        assertEquals("newusername", updatedUser.getUsername(), "Username should be updated");
        assertEquals("New Name", updatedUser.getName(), "Name should be updated");
        assertEquals("new@example.com", updatedUser.getEmail(), "Email should be updated");
        assertEquals("newhashedpassword", updatedUser.getPasswordHash(), "Password hash should be updated");

        verify(userRepository).findById(userId);
        verify(passwordEncoder).encode(userDTO.getPassword());
        verify(userRepository).save(existingUser);
    }
    
    @Test
    public void testEditUser_UserNotFound() {
        // Arrange
        long userId = 1L;
        UserRegistrationDTO userDTO = new UserRegistrationDTO();
        userDTO.setUsername("newusername");

        // Act
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Assert
        UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () -> {
            userService.editUser(userId, userDTO);
        });

        assertEquals("No User found at id: " + userId, thrown.getMessage());

        verify(userRepository).findById(userId);
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(passwordEncoder);
    }
}
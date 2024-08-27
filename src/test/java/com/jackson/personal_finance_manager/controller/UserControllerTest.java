package com.jackson.personal_finance_manager.controller;

import com.jackson.personal_finance_manager.dto.userdtos.UserRegistrationDTO;
import com.jackson.personal_finance_manager.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void createUser() throws Exception {
        // Mocking a User object and the service layer
        UserRegistrationDTO userDTO = new UserRegistrationDTO();
        userDTO.setUsername("testuser");
        userDTO.setName("Test");
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password123");

        when(userService.createUser(any(UserRegistrationDTO.class))).thenReturn(userDTO);

        // Convert the userDTO object to JSON format
        String userJson = "{ \"username\": \"testuser\", \"name\": \"Test\", \"email\": \"test@example.com\", \"password\": \"password123\" }";

        // Perform a POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("testuser"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test@example.com"));
    }
}
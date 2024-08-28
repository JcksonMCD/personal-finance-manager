package com.jackson.personal_finance_manager.controller;

import com.jackson.personal_finance_manager.dto.userdtos.UserRegistrationDTO;
import com.jackson.personal_finance_manager.model.User;
import com.jackson.personal_finance_manager.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.*;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
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

    @Test
    void getUserById() throws Exception {
        // Mocking a User object and the service layer
        User user = new User();
        user.setUserId(1L);
        user.setUsername("testuser");
        user.setName("Test");
        user.setEmail("test@example.com");

        when(userService.getUserById(1L)).thenReturn(user);

        // Perform a GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("testuser"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void editUserById() throws Exception {
        // Mocking a User object and the service layer
        UserRegistrationDTO userDTO = new UserRegistrationDTO();
        userDTO.setUsername("Editedtestuser");
        userDTO.setName("Edited Test");
        userDTO.setEmail("test@newexample.com");
        userDTO.setPassword("password123");

        User user = new User();
        user.setUserId(1L);
        user.setUsername("Editedtestuser");
        user.setName("Edited Test");
        user.setEmail("test@newexample.com");

        when(userService.editUser(eq(1L), any(UserRegistrationDTO.class))).thenReturn(user);

        // Convert the userDTO object to JSON format
        String userJson = "{ \"username\": \"Editedtestuser\", \"name\": \"Edited Test\", \"email\": \"test@newexample.com\", \"password\": \"password123\" }";

        // Perform a GET request
        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("Editedtestuser"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Edited Test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test@newexample.com"));
    }
}
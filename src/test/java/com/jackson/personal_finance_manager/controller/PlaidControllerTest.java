package com.jackson.personal_finance_manager.controller;

import com.jackson.personal_finance_manager.service.PlaidService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class PlaidControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PlaidService plaidService;

    @InjectMocks
    private PlaidController plaidController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(plaidController).build();
    }
    @Test
    public void testGetAccessTokenSuccess() throws Exception {
        String publicToken = "test_public_token";
        String accessToken = "test_access_token";

        when(plaidService.exchangePublicToken(publicToken)).thenReturn(accessToken);

        mockMvc.perform(post("/plaid/get_access_token")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("public_token", publicToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").value(accessToken))
                .andExpect(jsonPath("$.error").value(false));

        verify(plaidService, times(1)).exchangePublicToken(publicToken);
    }

}

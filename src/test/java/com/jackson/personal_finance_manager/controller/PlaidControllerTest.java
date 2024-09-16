package com.jackson.personal_finance_manager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jackson.personal_finance_manager.service.PlaidService;
import com.plaid.client.response.AuthGetResponse;
import com.plaid.client.response.TransactionsGetResponse;
import com.plaid.client.response.TransactionsGetResponse.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class PlaidControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PlaidService plaidService;

    private ObjectMapper objectMapper = new ObjectMapper();

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
    @Test
    public void testGetAccessTokenFailure() throws Exception {
        String publicToken = "test_public_token";

        when(plaidService.exchangePublicToken(publicToken)).thenThrow(new RuntimeException("Service error"));

        mockMvc.perform(post("/plaid/get_access_token")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("public_token", publicToken))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error exchanging token"));

        verify(plaidService, times(1)).exchangePublicToken(publicToken);
    }
    @Test
    public void testGetAccountSuccess() throws Exception {
        AuthGetResponse authGetResponse = mock(AuthGetResponse.class);

        when(plaidService.getAccountInfo()).thenReturn(authGetResponse);

        mockMvc.perform(get("/plaid/accounts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));

        verify(plaidService, times(1)).getAccountInfo();
    }

    @Test
    public void testGetAccountFailure() throws Exception {
        when(plaidService.getAccountInfo()).thenThrow(new RuntimeException("Service error"));

        mockMvc.perform(get("/plaid/accounts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error retrieving account info"));

        verify(plaidService, times(1)).getAccountInfo();
    }
    @Test
    public void testGetTransactionsSuccess() throws Exception {
        TransactionsGetResponse mockResponse = new TransactionsGetResponse();
        
        when(plaidService.getTransactions(any(Date.class), any(Date.class))).thenReturn(mockResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/plaid/transactions")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(mockResponse)));
    }

}

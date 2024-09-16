package com.jackson.personal_finance_manager.controller;

import com.jackson.personal_finance_manager.service.PlaidService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


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

}

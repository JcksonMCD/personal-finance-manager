package com.jackson.personal_finance_manager.service;

import com.plaid.client.PlaidClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
public class PlaidServiceImplTest {

    @Mock
    private PlaidClient plaidClient;

    @Mock
    private PlaidAuthService plaidAuthService;

    @InjectMocks
    private PlaidServiceImpl plaidService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

}

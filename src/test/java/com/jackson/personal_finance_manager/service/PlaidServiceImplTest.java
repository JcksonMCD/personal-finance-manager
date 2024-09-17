package com.jackson.personal_finance_manager.service;

import com.plaid.client.PlaidClient;
import com.plaid.client.request.*;
import com.plaid.client.response.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import retrofit2.Call;


@SpringJUnitConfig
public class PlaidServiceImplTest {

    @Mock
    private PlaidClient plaidClient;

    @Mock
    private PlaidAuthService plaidAuthService;

    @Mock
    private Call<ItemPublicTokenExchangeResponse> mockExchangeCall;

    @Mock
    private Call<AuthGetResponse> mockAuthGetCall;

    @Mock
    private Call<TransactionsGetResponse> mockTransactionsGetCall;

    @InjectMocks
    private PlaidServiceImpl plaidServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


}

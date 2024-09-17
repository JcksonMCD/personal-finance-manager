package com.jackson.personal_finance_manager.service;

import com.plaid.client.PlaidClient;
import com.plaid.client.request.*;
import com.plaid.client.PlaidApiService;
import com.plaid.client.response.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringJUnitConfig
public class PlaidServiceImplTest {

    @Mock
    private PlaidClient plaidClient;

    @Mock
    private PlaidAuthService plaidAuthService;

    @Mock
    private PlaidApiService plaidApiService;

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
        when(plaidClient.service()).thenReturn(plaidApiService);
    }

    @Test
    void testExchangePublicTokenSuccess() throws IOException {
        // Arrange
        String publicToken = "public-test-token";
        String expectedAccessToken = "access-test-token";
        String expectedItemId = "test-item-id";

        ItemPublicTokenExchangeResponse mockExchangeResponse = mock(ItemPublicTokenExchangeResponse.class);

        // Mock the getters on the response object to return expected values as no public setters
        when(mockExchangeResponse.getAccessToken()).thenReturn(expectedAccessToken);
        when(mockExchangeResponse.getItemId()).thenReturn(expectedItemId);

        Response<ItemPublicTokenExchangeResponse> mockResponse = Response.success(mockExchangeResponse);

        when(plaidClient.service().itemPublicTokenExchange(any(ItemPublicTokenExchangeRequest.class)))
                .thenReturn(mockExchangeCall);
        when(mockExchangeCall.execute()).thenReturn(mockResponse);

        // Act
        String actualAccessToken = plaidServiceImpl.exchangePublicToken(publicToken);

        // Assert
        assertEquals(expectedAccessToken, actualAccessToken);
        verify(plaidAuthService).setAccessToken(expectedAccessToken);
        verify(plaidAuthService).setItemId(expectedItemId);
    }





}

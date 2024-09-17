package com.jackson.personal_finance_manager.service;

import com.plaid.client.PlaidClient;
import com.plaid.client.request.*;
import com.plaid.client.PlaidApiService;
import com.plaid.client.response.*;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.ResponseBody;
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

    @Test
    void testExchangePublicTokenFailure() throws IOException {
        // Arrange
        String publicToken = "public-test-token";

        ResponseBody errorBody = ResponseBody.create(
                "{\"error\": \"Invalid public token\"}", MediaType.get("application/json")
        );

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("https://mock.plaid.com/item/public_token/exchange")
                .build();

        okhttp3.Response rawResponse = new okhttp3.Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(400)
                .message("Bad Request")
                .build();

        // Mock the Retrofit response
        Response<ItemPublicTokenExchangeResponse> mockResponse = Response.error(errorBody, rawResponse);

        // Act
        when(plaidApiService.itemPublicTokenExchange(any(ItemPublicTokenExchangeRequest.class)))
                .thenReturn(mockExchangeCall);
        when(mockExchangeCall.execute()).thenReturn(mockResponse);

        // Assert
        assertThrows(RuntimeException.class, () -> plaidServiceImpl.exchangePublicToken(publicToken));
    }



}

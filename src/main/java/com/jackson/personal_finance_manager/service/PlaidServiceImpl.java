package com.jackson.personal_finance_manager.service;

import com.plaid.client.PlaidClient;
import com.plaid.client.request.AuthGetRequest;
import com.plaid.client.request.ItemPublicTokenExchangeRequest;
import com.plaid.client.request.TransactionsGetRequest;
import com.plaid.client.response.AuthGetResponse;
import com.plaid.client.response.ItemPublicTokenExchangeResponse;
import com.plaid.client.response.TransactionsGetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;
import java.util.Date;


@Service
public class PlaidServiceImpl implements PlaidService{

    PlaidClient plaidClient;
    PlaidAuthService plaidAuthService;

    @Autowired
    public PlaidServiceImpl(PlaidAuthService plaidAuthService, PlaidClient plaidClient) {
        this.plaidAuthService = plaidAuthService;
        this.plaidClient = plaidClient;
    }

    public String exchangePublicToken(String publicToken) throws IOException {
        Response<ItemPublicTokenExchangeResponse> response = plaidClient.service()
                .itemPublicTokenExchange(new ItemPublicTokenExchangeRequest(publicToken))
                .execute();

        if (response.isSuccessful()) {
            plaidAuthService.setAccessToken(response.body().getAccessToken());
            plaidAuthService.setItemId(response.body().getItemId());
            return response.body().getAccessToken();
        } else {
            throw new RuntimeException("Error exchanging public token");
        }
    }

    public AuthGetResponse getAccountInfo() throws IOException {
        Response<AuthGetResponse> response = plaidClient.service()
                .authGet(new AuthGetRequest(plaidAuthService.getAccessToken()))
                .execute();

        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new RuntimeException("Error retrieving account info");
        }
    }

    public TransactionsGetResponse getTransactions(Date startDate, Date endDate) throws IOException {
        Response<TransactionsGetResponse> response = plaidClient.service()
                .transactionsGet(new TransactionsGetRequest(plaidAuthService.getAccessToken(), startDate, endDate)
                        .withCount(250)
                        .withOffset(0))
                .execute();

        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new RuntimeException("Error retrieving transactions");
        }
    }
}

package com.jackson.personal_finance_manager.service;


import com.plaid.client.response.AuthGetResponse;
import com.plaid.client.response.TransactionsGetResponse;

import java.io.IOException;
import java.util.Date;

public interface PlaidService {
    String exchangePublicToken(String publicToken) throws IOException;

    AuthGetResponse getAccountInfo() throws IOException;

    TransactionsGetResponse getTransactions(Date startDate, Date endDate) throws IOException;
}

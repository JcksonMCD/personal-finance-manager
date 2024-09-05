package com.jackson.personal_finance_manager.service;


import com.plaid.client.response.AuthGetResponse;

import java.io.IOException;

public interface PlaidService {
    String exchangePublicToken(String publicToken) throws IOException;

    AuthGetResponse getAccountInfo() throws IOException;

}

package com.jackson.personal_finance_manager.service;


import java.io.IOException;

public interface PlaidService {
    String exchangePublicToken(String publicToken) throws IOException;
}

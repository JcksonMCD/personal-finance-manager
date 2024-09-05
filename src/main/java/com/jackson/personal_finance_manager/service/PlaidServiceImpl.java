package com.jackson.personal_finance_manager.service;

import com.plaid.client.PlaidClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaidServiceImpl implements PlaidService{

    PlaidClient plaidClient;
    PlaidAuthService plaidAuthService;

    @Autowired
    public PlaidServiceImpl(PlaidAuthService plaidAuthService, PlaidClient plaidClient) {
        this.plaidAuthService = plaidAuthService;
        this.plaidClient = plaidClient;
    }
}

package com.jackson.personal_finance_manager.service;

import org.springframework.stereotype.Service;

// For storing plaid service tokens
@Service
public class PlaidAuthService {
    private String accessToken;
    private String itemId;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    // Clear the stored access token and item ID
    public void clearTokens() {
        this.accessToken = null;
        this.itemId = null;
    }
}

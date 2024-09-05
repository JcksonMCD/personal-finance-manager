package com.jackson.personal_finance_manager.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.plaid.client.PlaidClient;


@Configuration
public class PlaidConfig {

    @Value("${PLAID_CLIENT_ID}")
    private String plaidClientId;

    @Value("${PLAID_SECRET}")
    private String plaidSecret;

    @Value("${PLAID_PUBLIC_KEY}")
    private String plaidPublicKey;

    @Value("#{systemProperties['PLAID_ENV'] ?: 'sandbox'}") // set to sandbox for dev env
    private String plaidEnv;


    @Bean
    public PlaidClient plaidClient() {
        PlaidClient.Builder clientBuilder = PlaidClient.newBuilder()
                .clientIdAndSecret(plaidClientId, plaidSecret)
                .publicKey(plaidPublicKey);


        switch (plaidEnv.toLowerCase()) {
            case "sandbox":
                clientBuilder.sandboxBaseUrl();
                break;
            case "development":
                clientBuilder.developmentBaseUrl();
                break;
            case "production":
                clientBuilder.productionBaseUrl();
                break;
            default:
                clientBuilder.sandboxBaseUrl();
        }

        return clientBuilder.build();
    }

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}


package com.jackson.personal_finance_manager.controller;

import com.jackson.personal_finance_manager.service.PlaidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;
import static org.springframework.web.bind.annotation.RequestMethod.POST;



@RestController
@RequestMapping("/plaid")
public class PlaidController {

    PlaidService plaidService;

    @Autowired
    public PlaidController(PlaidService plaidService) {
        this.plaidService = plaidService;
    }

    @RequestMapping(value="/get_access_token", method=POST, consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public @ResponseBody ResponseEntity getAccessToken(@RequestParam("public_token") String publicToken) {
        try {
            String accessToken = plaidService.exchangePublicToken(publicToken);
            Map<String, Object> data = new HashMap<>();
            data.put("error", false);
            data.put("access_token", accessToken);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error exchanging token");
        }
    }
}


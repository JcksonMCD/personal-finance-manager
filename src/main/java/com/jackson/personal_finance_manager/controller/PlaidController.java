package com.jackson.personal_finance_manager.controller;

import com.jackson.personal_finance_manager.service.PlaidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/plaid")
public class PlaidController {

    PlaidService plaidService;

    @Autowired
    public PlaidController(PlaidService plaidService) {
        this.plaidService = plaidService;
    }
}


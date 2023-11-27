package com.bank.infrastructure.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping(AccountController.PATH)
public class AccountController {
    public static final String PATH = "/api/v1/account/";
}

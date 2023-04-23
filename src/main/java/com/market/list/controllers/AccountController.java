package com.market.list.controllers;

import com.market.list.entities.Account;
import com.market.list.entities.ApiResponse;
import com.market.list.exception.MarketException;
import com.market.list.handler.ApiHandler;
import com.market.list.services.AccountService;
import com.market.list.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountService accountService;

    private final ApiHandler<Account> apiHandler;

    @Autowired
    public AccountController(AccountService accountService, ApiHandler<Account> apiHandler) {
        this.accountService = accountService;
        this.apiHandler = apiHandler;
    }


    // ======== CREATE ========

    @PostMapping
    public ResponseEntity<ApiResponse<Account>> createAccount(@RequestBody Account account) {
        try {
            accountService.create(account);
            return apiHandler.handleSuccessCreation(account, Constants.ACCOUNT_CREATED);
        } catch (MarketException me) {
            return apiHandler.handleExceptionMessage(account, Constants.ACCOUNT_HAS_ERRORS(me.getMessage()));
        }
    }


    // ======== READ ========

    @GetMapping("/")
    public ResponseEntity<ApiResponse<Account>> getAccount(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "email", required = false) String email) {
        try {
            if (null == id && null == email) {
                return apiHandler.handleBadRequest(Constants.NO_PARAMS);
            }
            return apiHandler.handleSuccessGet(accountService.findAccount(id, email), Constants.ACCOUNT_FOUND);
        } catch (MarketException me) {
            return apiHandler.handleNotFound(me.getMessage());
        }
    }

    // ======== UPDATE ========

    @PutMapping
    public ResponseEntity<ApiResponse<Account>> updateAccount(@RequestBody Account account) {
        try {
            accountService.update(account);
            return apiHandler.handleSuccessModification(account, Constants.ACCOUNT_MODIFIED);
        } catch (MarketException me) {
            return apiHandler.handleExceptionMessage(account, Constants.ACCOUNT_HAS_ERRORS(me.getMessage()));
        }
    }

    // ======== DELETE ========

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Account>> deleteAccount(@PathVariable String id) {
        try {
            accountService.delete(id);
            return apiHandler.handleSuccessDeletion(Constants.ACCOUNT_DELETED);
        } catch (MarketException me) {
            return apiHandler.handleExceptionMessage(null, me.getMessage());
        }
    }
}

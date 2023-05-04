package com.market.list.controllers;

import com.market.list.entities.Account;
import com.market.list.entities.AccountRequest;
import com.market.list.entities.ApiResponse;
import com.market.list.exceptions.MarketException;
import com.market.list.handlers.ApiHandler;
import com.market.list.security.oauth.CustomOAuth2User;
import com.market.list.services.AccountService;
import com.market.list.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

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
        } catch (DataIntegrityViolationException dive) {
            return apiHandler.handleExceptionMessage(account, Constants.EXISTING_EMAIL);
        }
    }


    // ======== READ ========

    @GetMapping
    public ResponseEntity<ApiResponse<Account>> getAccount(@RequestParam(value = "id", required = false) String id,
                                                           @RequestParam(value = "email", required = false) String email) {
        try {
            if (isInvalidParam(id) && isInvalidParam(email)) {
                return apiHandler.handleBadRequest(Constants.NO_PARAMS);
            }
            return apiHandler.handleSuccessGet(accountService.findAccount(id, email), Constants.ACCOUNT_FOUND);
        } catch (MarketException me) {
            return apiHandler.handleNotFound(me.getMessage());
        }
    }

    // ======== UPDATE ========

    @PutMapping
    public ResponseEntity<ApiResponse<Account>> updateAccount(@RequestBody AccountRequest accountRequest, Authentication authentication) {
        if (isNotAuthenticated(authentication)) {
            return apiHandler.handleUnauthorized();
        }

        try {
            Account account = accountService.getViaAuthentication(authentication);
            if (isNotOwner(account.getId(),accountRequest.getId())){
                return apiHandler.handleUnauthorized();
            }

            account = accountService.updateAccount(accountRequest);
            return apiHandler.handleSuccessModification(account, Constants.ACCOUNT_MODIFIED);
        } catch (MarketException me) {
            return apiHandler.handleExceptionMessage(null, Constants.ACCOUNT_HAS_ERRORS(me.getMessage()));
        }
    }

    // ======== DELETE ========

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Account>> deleteAccount(Authentication authentication, @RequestParam(value = "id", required = false) String id) {
        if (isNotAuthenticated(authentication)){
            return apiHandler.handleUnauthorized();
        }

        try {
            if (isInvalidParam(id)) {
                return apiHandler.handleBadRequest(Constants.NO_PARAMS);
            }

            Account account = accountService.getViaAuthentication(authentication);
            if (isNotOwner(account.getId(),id)){
                return apiHandler.handleUnauthorized();
            }

            accountService.delete(id);
            return apiHandler.handleSuccessDeletion(Constants.ACCOUNT_DELETED);
        } catch (MarketException me) {
            return apiHandler.handleExceptionMessage(null, me.getMessage());
        }
    }


    // ======== PARAMS VALIDATORS ========

    private boolean isInvalidParam(String param) {
        return null == param || param.isBlank();
    }

    private boolean isNotAuthenticated(Authentication authentication) {
        return null == authentication || !authentication.isAuthenticated();
    }

    private boolean isNotOwner(String accountId, String paramId) {
        return !Objects.equals(accountId, paramId);
    }
}

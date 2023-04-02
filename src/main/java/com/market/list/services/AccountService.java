package com.market.list.services;


import com.market.list.entities.Account;
import com.market.list.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    // ======== CREATE ========

    @Transactional
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }


    // ======== READ ========

    @Transactional(readOnly = true)
    public List<Account> getAll() {
        return accountRepository.findAll();
    }
}

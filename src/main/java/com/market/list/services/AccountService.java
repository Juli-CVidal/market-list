package com.market.list.services;


import com.market.list.entities.Account;
import com.market.list.entities.Group;
import com.market.list.exception.MarketException;
import com.market.list.handler.EntityHandler;
import com.market.list.handler.ValidatorHandlerImpl;
import com.market.list.repositories.AccountRepository;
import com.market.list.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class AccountService {
    private final AccountRepository accountRepository;

    private final EntityHandler<Account> validationHandler;

    @Autowired
    public AccountService(AccountRepository accountRepository, ValidatorHandlerImpl<Account> validationHandler) {
        this.accountRepository = accountRepository;
        this.validationHandler = validationHandler;
    }


    // ======== CREATE ========

    @Transactional
    public Account create(Account account) throws MarketException {
        validationHandler.handle(account);
        return accountRepository.save(account);
    }


    // ======== READ ========

    @Transactional(readOnly = true)
    public Account findAccount(Integer id, String email) throws MarketException {
        Optional<Account> account = Optional.empty();
        if (null != id) {
            account = accountRepository.findById(id);
        }
        if (account.isEmpty() && null != email) {
            account = accountRepository.findByEmail(email);
        }
        return account.orElseThrow(() -> new MarketException(Constants.NOT_FOUND));
    }


    // ======== UPDATE ========

    @Transactional
    public Account update(Account account) throws MarketException {
        validationHandler.handle(account);
        return accountRepository.save(account);
    }

    // ======== DELETE ========

    @Transactional
    public void delete(Integer id) throws MarketException {
        if (null != findAccount(id, null)) {
            accountRepository.deleteById(id);
        }
    }

    // ======== RELATED TO GROUP MANAGEMENT ========

    @Transactional
    public void addGroupToAccount(Account account, Group group) throws MarketException {
        account.getGroups().add(group);
        this.update(account);
    }

    @Transactional
    public void removeGroupFromAccount(Account account, Group group) throws MarketException {
        if (account.getGroups().remove(group)){
            this.update(account);
        }
    }
}

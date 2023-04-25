package com.market.list.services;


import com.market.list.entities.Account;
import com.market.list.entities.AccountRequest;
import com.market.list.entities.Group;
import com.market.list.exception.MarketException;
import com.market.list.handler.EntityHandler;
import com.market.list.handler.ValidatorHandlerImpl;
import com.market.list.repositories.AccountRepository;
import com.market.list.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class AccountService {
    private final AccountRepository accountRepository;

    private final EntityHandler<Account> validationHandler;

    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public AccountService(AccountRepository accountRepository, ValidatorHandlerImpl<Account> validationHandler) {
        this.accountRepository = accountRepository;
        this.validationHandler = validationHandler;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }


    // ======== CREATE ========

    @Transactional
    public Account create(Account account) throws MarketException {
        validationHandler.handle(account);
        if (isInvalidPassword(account.getPassword())) {
            throw new MarketException(Constants.PASSWORD_REQUIREMENTS);
        }
        String password = passwordEncoder.encode(account.getPassword());
        System.out.println(password);
        account.setPassword(password);
        return accountRepository.save(account);
    }


    // ======== READ ========

    @Transactional(readOnly = true)
    public Account findAccount(String id, String email) throws MarketException {
        Optional<Account> account = Optional.empty();
        if (null != id && !id.isBlank()) {
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

    @Modifying
    @Transactional
    public Account updateAccount(AccountRequest changes) throws MarketException{
        Account account = findAccount(changes.getId(), null);
        if (wrongPassword(account.getPassword(),changes.getConfirmPassword())){
            throw new MarketException(Constants.INVALID_PASSWORD);
        }

        account.setEmail(changes.getEmail());
        account.setName(changes.getName());
        return update(account);
    }


    // ======== DELETE ========

    @Transactional
    public void delete(String id) throws MarketException {
        if (null != findAccount(id, null)) {
            accountRepository.deleteById(id);
        }
    }


    // ======== RELATED TO GROUP MANAGEMENT ========

    @Transactional
    public void addGroupToAccount(Account account, Group group) throws MarketException {
        account.getGroups().add(group);
        update(account);
    }

    @Transactional
    public void removeGroupFromAccount(Account account, Group group) throws MarketException {
        if (account.getGroups().remove(group)) {
            update(account);
        }
    }


    // ======== PASSWORD REQUIREMENTS VALIDATION ========


    /**
     * ^(?=.* [A-Z]) checks if the string contains at least one uppercase letter
     * (?=.*\d) checks if the string contains at least one digit (0-9)
     * [A-Za-z\d] permits any uppercase/lowercase/digits
     * {8,} checks if the string contains at least eight characters
     *
     * @param password the password attribute, without encryption
     * @return true if the password is valid (at least eight chars, with one number and one uppercase letter), otherwise false
     */
    private boolean isInvalidPassword(String password) {
        return !password.matches("^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$");
    }

    public boolean wrongPassword(String password, String repeat) {
        return !passwordEncoder.matches(repeat,password);
    }
}

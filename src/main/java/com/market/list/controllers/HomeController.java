package com.market.list.controllers;

import com.market.list.entities.Account;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String sayHi(HttpSession session){
        Account account = (Account) session.getAttribute("account");

        return "Hola " + account.getName();
    }
}

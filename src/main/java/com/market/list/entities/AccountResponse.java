package com.market.list.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountResponse {

    private final String id;

    private final String name;

    private final String email;

    private final String confirmPassword;
}

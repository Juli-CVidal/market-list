package com.market.list.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MarketException extends RuntimeException{
    private final String errorMessage;
}

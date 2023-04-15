package com.market.list.exception;



import lombok.Getter;


public class MarketException extends Exception {
    public MarketException() {}
    public MarketException(String message) {
        super(message);
    }
}

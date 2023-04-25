package com.market.list.handler;

import com.market.list.exception.MarketException;

public interface EntityHandler<T> {
    void handle(T object) throws MarketException;

}

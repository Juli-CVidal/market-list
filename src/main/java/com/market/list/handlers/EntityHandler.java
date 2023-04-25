package com.market.list.handlers;

import com.market.list.exceptions.MarketException;

public interface EntityHandler<T> {
    void handle(T object) throws MarketException;

}

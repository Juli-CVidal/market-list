package com.market.list.responses;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class EntityResponse<T> {
    private T entity;
    private String message;
}

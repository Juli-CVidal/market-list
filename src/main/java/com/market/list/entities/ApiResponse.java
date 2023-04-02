package com.market.list.entities;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class ApiResponse<T> {
    private T entity;
    private String message;
}

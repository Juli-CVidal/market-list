package com.market.list.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class ApiResponse<T> {
    private T entity;
    private String message;
}

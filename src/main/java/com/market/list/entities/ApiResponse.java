package com.market.list.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter
@Setter
@AllArgsConstructor
public class ApiResponse<T> {
    private T entity;

    private HttpStatus status;

    private String message;
}

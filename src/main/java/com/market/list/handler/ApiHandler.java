package com.market.list.handler;

import com.market.list.entities.ApiResponse;
import com.market.list.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ApiHandler<T> {

    public ResponseEntity<ApiResponse<T>> handleExceptionMessage(T entity, String message) {
        return handleResponse(HttpStatus.BAD_REQUEST, entity, message);
    }

    public ResponseEntity<ApiResponse<T>> handleSuccessCreation(T entity, String message) {
        return handleResponse(HttpStatus.CREATED, entity, message);
    }

    public ResponseEntity<ApiResponse<T>> handleSuccessGet(T entity, String message) {
        return handleResponse(HttpStatus.OK, entity, message);
    }

    public ResponseEntity<ApiResponse<T>> handleSuccessModification(T entity, String message) {
        return handleResponse(HttpStatus.OK, entity, message);
    }


    public ResponseEntity<ApiResponse<T>> handleSuccessDeletion(String message) {
        return handleResponse(HttpStatus.NO_CONTENT, null, message);
    }

    public ResponseEntity<ApiResponse<T>> handleSuccessAddition(String message) {
        return handleResponse(HttpStatus.OK, null, message);
    }

    public ResponseEntity<ApiResponse<T>> handleNotFound(String message) {
        return handleResponse(HttpStatus.NOT_FOUND, null, message);
    }

    public ResponseEntity<ApiResponse<T>> handleBadRequest(String message) {
        return handleResponse(HttpStatus.BAD_REQUEST, null, message);
    }

    public ResponseEntity<ApiResponse<T>> handleSuccessRemoving(String message) {
        return handleResponse(HttpStatus.OK, null, message);
    }

    public ResponseEntity<ApiResponse<T>> handleForbidden() {
        return handleResponse(HttpStatus.FORBIDDEN, null, Constants.FORBIDDEN);
    }

    private ResponseEntity<ApiResponse<T>> handleResponse(HttpStatus status, T entity, String message) {
        return ResponseEntity.status(status).body(new ApiResponse<>(entity, status, message));
    }


}

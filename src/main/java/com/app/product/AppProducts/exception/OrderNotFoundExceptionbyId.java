package com.app.product.AppProducts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderNotFoundExceptionbyId extends RuntimeException {
    public OrderNotFoundExceptionbyId(String message) {
        super(message);
    }
}
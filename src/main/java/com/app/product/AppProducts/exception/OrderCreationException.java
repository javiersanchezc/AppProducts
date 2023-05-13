package com.app.product.AppProducts.exception;

public class OrderCreationException extends RuntimeException {
    public OrderCreationException(String message, Throwable cause) {

        super(message, cause);
    }
}

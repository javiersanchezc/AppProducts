package com.app.product.AppProducts.exception;

public class CustomerUpdateException extends RuntimeException {
    public CustomerUpdateException(String message) {
        super(message);
    }

    public CustomerUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
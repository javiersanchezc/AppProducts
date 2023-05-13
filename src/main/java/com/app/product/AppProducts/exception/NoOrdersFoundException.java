package com.app.product.AppProducts.exception;

public class NoOrdersFoundException extends RuntimeException {
    public NoOrdersFoundException(String message) {
        super(message);
    }
}

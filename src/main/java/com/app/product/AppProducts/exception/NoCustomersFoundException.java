package com.app.product.AppProducts.exception;

public class NoCustomersFoundException extends RuntimeException {
    public NoCustomersFoundException(String message) {
        super(message);
    }
}

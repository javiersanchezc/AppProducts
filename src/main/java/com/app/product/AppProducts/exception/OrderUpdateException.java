package com.app.product.AppProducts.exception;

public class OrderUpdateException extends RuntimeException {
    public OrderUpdateException(String message) {
        super(message);
    }

    public OrderUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.app.product.AppProducts.exception;

public class OrderDeletionException extends RuntimeException {
    public OrderDeletionException(String message, Throwable cause) {
        super(message, cause);
    }
}
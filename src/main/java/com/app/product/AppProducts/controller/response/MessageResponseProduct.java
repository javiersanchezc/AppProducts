package com.app.product.AppProducts.controller.response;

public class MessageResponseProduct {
    private String message;

    public MessageResponseProduct(String message) {
        this.message = message;
    }

    // Getters and setters...

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
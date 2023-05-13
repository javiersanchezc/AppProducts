package com.app.product.AppProducts.controller.response;

public class ApiResponseOrder {
    private boolean success;
    private String message;

    // Getters and setters...

    public ApiResponseOrder(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
// You can add more fields if you want, such as a timestamp or a more detailed status.
}


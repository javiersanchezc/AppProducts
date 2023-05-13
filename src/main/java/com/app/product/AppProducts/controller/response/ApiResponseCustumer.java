package com.app.product.AppProducts.controller.response;

public class ApiResponseCustumer {
    private Boolean success;
    private String message;


    public ApiResponseCustumer(Boolean success, String message, Object data) {
        this.success = success;
        this.message = message;

    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}

package com.payment_tracker;

public class ErrorResponseCode {
    private String errorCode;
    private String description;

    public ErrorResponseCode(String errorCode, String description) {
        this.errorCode = errorCode;
        this.description = description;
    }
    // Getters
    public String getErrorCode() { return errorCode; }
    public String getDescription() { return description; }
}

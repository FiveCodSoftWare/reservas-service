package com.fivecods.infrastructure.exceptions;

public class DetailErrorResponse {
    private String errorCode;
    private String message;
    private String url;

    public DetailErrorResponse(String errorCode, String message, String url) {
        this.errorCode = errorCode;
        this.message = message;
        this.url = url;
    }

    public String getErrorCode() { return errorCode; }
    public String getMessage() { return message; }
    public String getUrl() { return url; }
}
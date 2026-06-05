package com.fivecods.infrastructure.exceptions;

import java.time.Instant;
import java.util.List;

public class ApiErrorResponse {
    private int statusCode;
    private String timestamp;
    private String userMessage;
    private Object data;
    private List<DetailErrorResponse> errors;

    public ApiErrorResponse(int statusCode, String userMessage,
                            List<DetailErrorResponse> errors) {
        this.statusCode = statusCode;
        this.timestamp = Instant.now().toString();
        this.userMessage = userMessage;
        this.data = null;
        this.errors = errors;
    }

    public int getStatusCode() { return statusCode; }
    public String getTimestamp() { return timestamp; }
    public String getUserMessage() { return userMessage; }
    public Object getData() { return data; }
    public List<DetailErrorResponse> getErrors() { return errors; }
}
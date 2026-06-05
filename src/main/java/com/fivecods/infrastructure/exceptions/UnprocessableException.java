package com.fivecods.infrastructure.exceptions;

public class UnprocessableException extends BusinessException {
    public UnprocessableException(String message) {
        super(message, 422);
    }
}
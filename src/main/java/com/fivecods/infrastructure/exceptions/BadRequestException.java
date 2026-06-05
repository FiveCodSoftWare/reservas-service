package com.fivecods.infrastructure.exceptions;

public class BadRequestException extends BusinessException {
    public BadRequestException(String message) {
        super(message, 400);
    }
}
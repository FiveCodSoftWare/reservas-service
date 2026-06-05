package com.fivecods.infrastructure.exceptions;

public class ConflictException extends BusinessException {
    public ConflictException(String message) {
        super(message, 409);
    }
}
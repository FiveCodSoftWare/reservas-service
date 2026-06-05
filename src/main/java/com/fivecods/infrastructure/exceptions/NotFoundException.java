package com.fivecods.infrastructure.exceptions;

public class NotFoundException extends BusinessException {
    public NotFoundException(String message) {
        super(message, 404);
    }
}

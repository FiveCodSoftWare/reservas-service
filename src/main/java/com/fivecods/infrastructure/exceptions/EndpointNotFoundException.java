package com.fivecods.infrastructure.exceptions;

public class EndpointNotFoundException extends BusinessException {
    public EndpointNotFoundException(String message) {
        super(message, 404);
    }
}

package com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.exceptions;

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
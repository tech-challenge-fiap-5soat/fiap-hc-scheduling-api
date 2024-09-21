package com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorResponseDto {
    private String message;

    public ErrorResponseDto(String message) {
        this.message = message;
    }

}
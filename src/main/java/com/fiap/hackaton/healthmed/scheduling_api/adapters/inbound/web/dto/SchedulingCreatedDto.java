package com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record SchedulingCreatedDto(
        UUID id
) {
}
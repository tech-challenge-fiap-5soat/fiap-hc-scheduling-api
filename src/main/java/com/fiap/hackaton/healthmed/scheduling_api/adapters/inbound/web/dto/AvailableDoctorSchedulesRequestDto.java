package com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.dto;

import java.time.LocalDateTime;

public record AvailableDoctorSchedulesRequestDto(
        LocalDateTime date
) {
}
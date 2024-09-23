package com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record AvailableDoctorSchedules(
        UUID doctorId,
        UUID doctorScheduleId,
        LocalDateTime scheduleDate,
        LocalDateTime scheduleStartTime,
        LocalDateTime scheduleEndTime
) {
}
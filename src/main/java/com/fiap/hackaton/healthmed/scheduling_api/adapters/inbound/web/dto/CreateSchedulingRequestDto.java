package com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record CreateSchedulingRequestDto(
        UUID patientId,
        UUID doctorId,
        UUID doctorScheduleId,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
        LocalDateTime schedulingDate
) {
}
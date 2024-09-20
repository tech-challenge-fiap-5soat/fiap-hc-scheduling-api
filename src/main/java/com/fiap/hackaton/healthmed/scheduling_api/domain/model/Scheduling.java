package com.fiap.hackaton.healthmed.scheduling_api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Scheduling {
    private UUID id;
    private UUID doctorId;
    private UUID patientId;
    private UUID doctorScheduleId;
    private LocalDateTime schedulingDate;
}
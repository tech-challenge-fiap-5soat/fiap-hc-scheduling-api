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
public class DoctorSchedule {
    private UUID patientId;
    private UUID doctorId;
    private UUID doctorScheduleId;
    private LocalDateTime scheduleDate;
    private LocalDateTime scheduleStartTime;
    private LocalDateTime scheduleEndTime;
    private String status;
}
package com.fiap.hackaton.healthmed.scheduling_api.application.services;

import com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.dto.AvailableDoctorSchedules;
import com.fiap.hackaton.healthmed.scheduling_api.domain.model.Scheduling;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface SchedulingService {
    Scheduling createScheduling(Scheduling scheduling);
    List<AvailableDoctorSchedules> getAvailableDoctorSchedules(UUID doctorId, LocalDateTime date);
}
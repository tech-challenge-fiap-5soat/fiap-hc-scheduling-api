package com.fiap.hackaton.healthmed.scheduling_api.application.ports.input.useCase.impl;

import com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.dto.AvailableDoctorSchedules;
import com.fiap.hackaton.healthmed.scheduling_api.application.ports.input.useCase.CreateSchedulingUseCase;
import com.fiap.hackaton.healthmed.scheduling_api.application.services.SchedulingService;
import com.fiap.hackaton.healthmed.scheduling_api.domain.model.Scheduling;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CreateSchedulingUseCaseImpl implements CreateSchedulingUseCase {

    @Autowired
    private final SchedulingService schedulingService;

    @Override
    public Scheduling createScheduling(Scheduling scheduling) {
        return schedulingService.createScheduling(scheduling);
    }

    @Override
    public List<AvailableDoctorSchedules> getAvailableDoctorSchedules(UUID doctorId, LocalDateTime date) {
        return schedulingService.getAvailableDoctorSchedules(doctorId, date);
    }
}
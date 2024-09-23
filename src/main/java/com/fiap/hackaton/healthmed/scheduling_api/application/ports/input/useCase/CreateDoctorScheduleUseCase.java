package com.fiap.hackaton.healthmed.scheduling_api.application.ports.input.useCase;

import com.fiap.hackaton.healthmed.scheduling_api.domain.model.DoctorSchedule;

import java.util.List;

public interface CreateDoctorScheduleUseCase {
    void createDoctorSchedules(List<DoctorSchedule> doctorSchedules);
}
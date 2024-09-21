package com.fiap.hackaton.healthmed.scheduling_api.application.ports.input.useCase.impl;

import com.fiap.hackaton.healthmed.scheduling_api.application.ports.input.useCase.CreateDoctorScheduleUseCase;
import com.fiap.hackaton.healthmed.scheduling_api.application.services.DoctorScheduleService;
import com.fiap.hackaton.healthmed.scheduling_api.domain.model.DoctorSchedule;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CreateDoctorScheduleUseCaseImpl implements CreateDoctorScheduleUseCase {

    @Autowired
    private final DoctorScheduleService doctorScheduleService;

    @Override
    public void createDoctorSchedules(List<DoctorSchedule> doctorSchedules) {
        doctorScheduleService.createDoctorSchedule(doctorSchedules);
    }
}
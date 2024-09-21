package com.fiap.hackaton.healthmed.scheduling_api.application.services;

import com.fiap.hackaton.healthmed.scheduling_api.domain.model.DoctorSchedule;

import java.util.List;

public interface DoctorScheduleService {
    void createDoctorSchedule(List<DoctorSchedule> doctorSchedules);
}
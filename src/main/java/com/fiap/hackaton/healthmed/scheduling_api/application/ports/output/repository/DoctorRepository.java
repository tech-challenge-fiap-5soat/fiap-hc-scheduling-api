package com.fiap.hackaton.healthmed.scheduling_api.application.ports.output.repository;

import com.fiap.hackaton.healthmed.scheduling_api.domain.model.Doctor;

import java.util.List;

public interface DoctorRepository {
    List<Doctor> FindAll();
}
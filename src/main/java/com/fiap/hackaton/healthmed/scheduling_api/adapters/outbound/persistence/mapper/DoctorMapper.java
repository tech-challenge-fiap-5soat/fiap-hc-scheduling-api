package com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.mapper;

import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.entity.DoctorEntity;
import com.fiap.hackaton.healthmed.scheduling_api.domain.model.Doctor;

import java.util.List;
import java.util.stream.Collectors;


public class DoctorMapper {
    public static Doctor entityToDoctor(DoctorEntity doctorEntity) {
        return Doctor.builder()
                .id(doctorEntity.getId())
                .build();
    }

    public static List<Doctor> doctorEntityListToDoctorList(List<DoctorEntity> doctorEntity) {
        return doctorEntity.stream()
                .map(DoctorMapper::entityToDoctor)
                .collect(Collectors.toList());
    }
}
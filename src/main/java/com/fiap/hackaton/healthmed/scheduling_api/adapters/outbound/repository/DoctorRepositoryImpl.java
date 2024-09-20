package com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.repository;

import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.entity.DoctorEntity;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.mapper.DoctorMapper;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.repository.jpa.JpaDoctorRepository;
import com.fiap.hackaton.healthmed.scheduling_api.application.ports.output.repository.DoctorRepository;
import com.fiap.hackaton.healthmed.scheduling_api.domain.model.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DoctorRepositoryImpl implements DoctorRepository {

    @Autowired
    private JpaDoctorRepository jpaDoctorRepository;

    @Override
    public List<Doctor> FindAll() {
        List<DoctorEntity> doctorList = jpaDoctorRepository.findAll();
        return DoctorMapper.doctorEntityListToDoctorList(doctorList);
    }
}
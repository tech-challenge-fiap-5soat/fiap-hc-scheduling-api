package com.fiap.hackaton.healthmed.scheduling_api.application.services.impl;

import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.entity.DoctorScheduleEntity;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.entity.DoctorScheduleStatusEnum;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.mapper.DoctorScheduleMapper;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.repository.jpa.JpaDoctorScheduleRepository;
import com.fiap.hackaton.healthmed.scheduling_api.application.services.DoctorScheduleService;
import com.fiap.hackaton.healthmed.scheduling_api.domain.model.DoctorSchedule;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DoctorScheduleServiceImpl implements DoctorScheduleService {

    @Autowired
    private final JpaDoctorScheduleRepository jpaDoctorScheduleRepository;

    public void createDoctorSchedule(List<DoctorSchedule> doctorSchedules) {
        List<DoctorScheduleEntity> entities = DoctorScheduleMapper.toEntityList(doctorSchedules, DoctorScheduleStatusEnum.AVAILABLE);
        jpaDoctorScheduleRepository.saveAll(entities);
    }
}
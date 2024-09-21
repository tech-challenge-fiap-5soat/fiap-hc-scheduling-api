package com.fiap.hackaton.healthmed.scheduling_api.application.services.impl;

import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.entity.DoctorScheduleEntity;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.entity.DoctorScheduleStatusEnum;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.entity.SchedulingEntity;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.mapper.SchedulingMapper;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.repository.jpa.JpaDoctorScheduleRepository;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.repository.jpa.JpaSchedulingRepository;
import com.fiap.hackaton.healthmed.scheduling_api.application.services.SchedulingService;
import com.fiap.hackaton.healthmed.scheduling_api.domain.model.Scheduling;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SchedulingServiceImpl implements SchedulingService {

    @Autowired
    private final JpaSchedulingRepository jpaSchedulingRepository;

    @Autowired
    private final JpaDoctorScheduleRepository jpaDoctorScheduleRepository;

    @Override
    public Scheduling createScheduling(Scheduling scheduling) {
        SchedulingEntity saved = saveScheduling(scheduling);
        DoctorScheduleEntity doctorScheduleEntity = saveDoctorSchedule(scheduling);
        return SchedulingMapper.toDomain(saved);
    }

    private SchedulingEntity saveScheduling(Scheduling scheduling) {
        SchedulingEntity schedulingEntity = SchedulingMapper.toEntity(scheduling);
        return jpaSchedulingRepository.save(schedulingEntity);
    }

    private DoctorScheduleEntity saveDoctorSchedule(Scheduling scheduling) {
        DoctorScheduleEntity doctorScheduleEntity = jpaDoctorScheduleRepository.findById(scheduling.getDoctorScheduleId()).get();
        doctorScheduleEntity.setStatus(DoctorScheduleStatusEnum.UNAVAILABLE);
        return jpaDoctorScheduleRepository.save(doctorScheduleEntity);
    }
}
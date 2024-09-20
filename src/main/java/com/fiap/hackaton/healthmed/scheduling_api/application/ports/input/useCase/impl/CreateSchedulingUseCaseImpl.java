package com.fiap.hackaton.healthmed.scheduling_api.application.ports.input.useCase.impl;

import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.entity.DoctorScheduleEntity;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.entity.DoctorScheduleStatusEnum;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.entity.SchedulingEntity;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.mapper.SchedulingMapper;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.repository.jpa.JpaDoctorScheduleRepository;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.repository.jpa.JpaSchedulingRepository;
import com.fiap.hackaton.healthmed.scheduling_api.application.ports.input.useCase.CreateSchedulingUseCase;
import com.fiap.hackaton.healthmed.scheduling_api.domain.model.Scheduling;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateSchedulingUseCaseImpl implements CreateSchedulingUseCase {

    @Autowired
    private final JpaSchedulingRepository jpaSchedulingRepository;

    @Autowired
    private final JpaDoctorScheduleRepository jpaDoctorScheduleRepository;

    @Override
    public Scheduling createScheduling(Scheduling scheduling) {
        SchedulingEntity schedulingEntity = SchedulingMapper.toEntity(scheduling);
        SchedulingEntity saved = jpaSchedulingRepository.save(schedulingEntity);

        DoctorScheduleEntity doctorScheduleEntity = jpaDoctorScheduleRepository.findById(scheduling.getDoctorScheduleId()).get();
        doctorScheduleEntity.setStatus(DoctorScheduleStatusEnum.UNAVAILABLE);
        jpaDoctorScheduleRepository.save(doctorScheduleEntity);

        return SchedulingMapper.toDomain(saved);
    }
}
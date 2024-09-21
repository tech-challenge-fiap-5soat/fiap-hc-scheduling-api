package com.fiap.hackaton.healthmed.scheduling_api.application.services.impl;

import com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.dto.AvailableDoctorSchedules;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.entity.DoctorScheduleEntity;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.entity.DoctorScheduleStatusEnum;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.entity.SchedulingEntity;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.mapper.DoctorScheduleMapper;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.mapper.SchedulingMapper;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.repository.jpa.JpaDoctorScheduleRepository;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.repository.jpa.JpaSchedulingRepository;
import com.fiap.hackaton.healthmed.scheduling_api.application.services.SchedulingService;
import com.fiap.hackaton.healthmed.scheduling_api.domain.model.Scheduling;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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

    @Override
    public List<AvailableDoctorSchedules> getAvailableDoctorSchedules(UUID doctorId, LocalDateTime date) {
        List<DoctorScheduleEntity> doctorScheduleEntities = jpaDoctorScheduleRepository.findByDoctorIdAndStatusAndScheduleDate(
                doctorId, DoctorScheduleStatusEnum.AVAILABLE, date
        );
        return DoctorScheduleMapper.toAvailableDoctorSchedulesList(doctorScheduleEntities);
    }

    private SchedulingEntity saveScheduling(Scheduling scheduling) {
        SchedulingEntity schedulingEntity = SchedulingMapper.toEntity(scheduling);
        if (!isValidSchedule(scheduling)) {
            throw new IllegalArgumentException("Invalid schedule");
        }
        return jpaSchedulingRepository.save(schedulingEntity);
    }

    private DoctorScheduleEntity saveDoctorSchedule(Scheduling scheduling) {
        DoctorScheduleEntity doctorScheduleEntity = jpaDoctorScheduleRepository.findById(scheduling.getDoctorScheduleId()).get();
        doctorScheduleEntity.setStatus(DoctorScheduleStatusEnum.UNAVAILABLE);
        return jpaDoctorScheduleRepository.save(doctorScheduleEntity);
    }

    private Boolean isValidSchedule(Scheduling scheduling) {
        return scheduling.getSchedulingDate().isAfter(LocalDateTime.now()) &&
                scheduling.getDoctorId() != null &&
                scheduling.getDoctorScheduleId() != null &&
                scheduling.getPatientId() != null;
    }
}
package com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.repository.jpa;

import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.entity.DoctorScheduleEntity;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.entity.DoctorScheduleStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface JpaDoctorScheduleRepository extends JpaRepository<DoctorScheduleEntity, UUID> {
    List<DoctorScheduleEntity> findByDoctorId(UUID doctorId);
    List<DoctorScheduleEntity> findByDoctorIdAndStatusAndScheduleDate(
            UUID doctorId,
            DoctorScheduleStatusEnum status,
            LocalDateTime scheduleDate
    );
    List<DoctorScheduleEntity> findByDoctorIdAndStatusAndScheduleDateAfter(
            UUID doctorId,
            DoctorScheduleStatusEnum status,
            LocalDateTime scheduleDate
    );

    List<DoctorScheduleEntity> findByDoctorIdAndStatusAndScheduleStartTimeAfter(
            UUID doctorId,
            DoctorScheduleStatusEnum status,
            LocalDateTime scheduleStartTime
    );

}
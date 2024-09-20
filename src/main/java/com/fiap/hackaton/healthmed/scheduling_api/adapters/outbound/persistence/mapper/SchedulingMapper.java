package com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.mapper;

import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.entity.DoctorScheduleStatusEnum;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.entity.SchedulingEntity;
import com.fiap.hackaton.healthmed.scheduling_api.domain.model.Scheduling;

public class SchedulingMapper {
    public static SchedulingEntity toEntity(Scheduling scheduling) {
        return SchedulingEntity.builder()
                .id(scheduling.getId())
                .patientId(scheduling.getPatientId())
                .doctorId(scheduling.getDoctorId())
                .doctorScheduleId(scheduling.getDoctorScheduleId())
                .schedulingDate(scheduling.getSchedulingDate())
                .build();
    }

    public static Scheduling toDomain(SchedulingEntity schedulingEntity) {
        return Scheduling.builder()
                .id(schedulingEntity.getId())
                .patientId(schedulingEntity.getPatientId())
                .doctorId(schedulingEntity.getDoctorId())
                .doctorScheduleId(schedulingEntity.getDoctorScheduleId())
                .schedulingDate(schedulingEntity.getSchedulingDate())
                .build();
    }
}
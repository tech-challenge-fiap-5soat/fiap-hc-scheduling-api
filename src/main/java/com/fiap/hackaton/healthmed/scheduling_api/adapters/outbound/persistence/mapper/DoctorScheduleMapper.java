package com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.mapper;

import com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.dto.AvailableDoctorSchedules;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.entity.DoctorScheduleEntity;

import java.util.List;
import java.util.stream.Collectors;

public class DoctorScheduleMapper {
    public static AvailableDoctorSchedules toAvailableDoctorSchedules(DoctorScheduleEntity entity) {
        return AvailableDoctorSchedules.builder()
                .doctorScheduleId(entity.getId())
                .doctorId(entity.getDoctorId())
                .scheduleDate(entity.getScheduleDate())
                .scheduleStartTime(entity.getScheduleStartTime())
                .scheduleEndTime(entity.getScheduleEndTime())
                .build();
    }

    public static List<AvailableDoctorSchedules> toAvailableDoctorSchedulesList(List<DoctorScheduleEntity> entities) {
        return entities.stream()
                .map(DoctorScheduleMapper::toAvailableDoctorSchedules)
                .collect(Collectors.toList());
    }
}
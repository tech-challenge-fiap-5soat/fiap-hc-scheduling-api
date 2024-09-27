package com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.mapper;

import com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.dto.AvailableDoctorSchedules;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.dto.CreateDoctorScheduleRequestDto;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.dto.CreateSchedulingRequestDto;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.entity.DoctorScheduleEntity;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.entity.DoctorScheduleStatusEnum;
import com.fiap.hackaton.healthmed.scheduling_api.domain.model.DoctorSchedule;

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

    public static DoctorScheduleEntity toEntity(DoctorSchedule doctorSchedule, DoctorScheduleStatusEnum status) {
        return DoctorScheduleEntity.builder()
                .doctorId(doctorSchedule.getDoctorId())
                .scheduleDate(doctorSchedule.getScheduleDate())
                .scheduleStartTime(doctorSchedule.getScheduleStartTime())
                .scheduleEndTime(doctorSchedule.getScheduleEndTime())
                .status(status)
                .build();
    }

    public static List<DoctorScheduleEntity> toEntityList(List<DoctorSchedule> doctorSchedules, DoctorScheduleStatusEnum status) {
        return doctorSchedules.stream()
                .map(doctorSchedule -> toEntity(doctorSchedule, status))
                .collect(Collectors.toList());
    }

    public static DoctorSchedule createDoctorScheduleRequestDtotoDoctorSchedule(CreateDoctorScheduleRequestDto dto) {
        return DoctorSchedule.builder()
                .doctorId(dto.doctorId())
                .scheduleDate(dto.date())
                .scheduleStartTime(dto.startTime())
                .scheduleEndTime(dto.endTime())
                .build();
    }

    public static List<DoctorSchedule> CreateDoctorScheduleRequestDtoListToDoctorScheduleList(List<CreateDoctorScheduleRequestDto> dtos) {
        return dtos.stream()
                .map(DoctorScheduleMapper::createDoctorScheduleRequestDtotoDoctorSchedule)
                .collect(Collectors.toList());
    }

}
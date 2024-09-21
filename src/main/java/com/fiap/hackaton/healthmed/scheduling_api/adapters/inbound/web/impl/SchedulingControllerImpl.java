package com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.impl;

import com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.SchedulingController;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.dto.AvailableDoctorSchedules;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.dto.AvailableDoctorSchedulesRequestDto;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.dto.CreateSchedulingRequestDto;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.dto.SchedulingCreatedDto;
import com.fiap.hackaton.healthmed.scheduling_api.application.ports.input.useCase.CreateSchedulingUseCase;
import com.fiap.hackaton.healthmed.scheduling_api.domain.model.Scheduling;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class SchedulingControllerImpl implements SchedulingController {

    @Autowired
    private final CreateSchedulingUseCase createSchedulingUseCase;

    @Override
    public ResponseEntity<SchedulingCreatedDto> createScheduleAppointment(CreateSchedulingRequestDto request) {
        Scheduling scheduling = Scheduling.builder()
                .patientId(request.patientId())
                .doctorId(request.doctorId())
                .doctorScheduleId(request.doctorScheduleId())
                .schedulingDate(request.schedulingDate())
                .build();

        Scheduling scheduled = createSchedulingUseCase.createScheduling(scheduling);

        SchedulingCreatedDto createdDto = SchedulingCreatedDto.builder()
                .id(scheduled.getId())
                .build();

        return ResponseEntity.ok(createdDto);
    }

    @Override
    public ResponseEntity<List<AvailableDoctorSchedules>> availableSchedules(UUID doctorId,
                                                                             AvailableDoctorSchedulesRequestDto requestDto) {

        List<AvailableDoctorSchedules> availableDoctorSchedules = createSchedulingUseCase.getAvailableDoctorSchedules(doctorId, requestDto.date());
        return ResponseEntity.ok(availableDoctorSchedules);
    }

}
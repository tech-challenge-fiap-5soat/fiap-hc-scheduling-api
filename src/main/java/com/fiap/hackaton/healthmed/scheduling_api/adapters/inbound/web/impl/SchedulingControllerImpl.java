package com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.impl;

import com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.SchedulingController;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.dto.*;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.mapper.DoctorScheduleMapper;
import com.fiap.hackaton.healthmed.scheduling_api.application.ports.input.useCase.CreateDoctorScheduleUseCase;
import com.fiap.hackaton.healthmed.scheduling_api.application.ports.input.useCase.CreateSchedulingUseCase;
import com.fiap.hackaton.healthmed.scheduling_api.domain.model.DoctorSchedule;
import com.fiap.hackaton.healthmed.scheduling_api.domain.model.Scheduling;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.UUID;

@CrossOrigin

@RestController
@RequiredArgsConstructor
public class SchedulingControllerImpl implements SchedulingController {

    @Autowired
    private final CreateSchedulingUseCase createSchedulingUseCase;

    @Autowired
    private final CreateDoctorScheduleUseCase createDoctorScheduleUseCase;

    @Override
    public ResponseEntity<?> createScheduleAppointment(Jwt jwt, CreateSchedulingRequestDto request) {
        try {
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
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<List<AvailableDoctorSchedules>> availableSchedules(Jwt jwt,
                                                                             UUID doctorId,
                                                                             AvailableDoctorSchedulesRequestDto requestDto) {
        List<AvailableDoctorSchedules> availableDoctorSchedules = createSchedulingUseCase.getAvailableDoctorSchedules(doctorId, requestDto.date());
        return ResponseEntity.ok(availableDoctorSchedules);
    }

    @Override
    public ResponseEntity<?> createDoctorSchedules(Jwt jwt, UUID doctorId, List<CreateDoctorScheduleRequestDto> schedulingRequests) {
        try {
            List<DoctorSchedule> schedules = DoctorScheduleMapper.CreateDoctorScheduleRequestDtoListToDoctorScheduleList(schedulingRequests);
            createDoctorScheduleUseCase.createDoctorSchedules(schedules);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }
}
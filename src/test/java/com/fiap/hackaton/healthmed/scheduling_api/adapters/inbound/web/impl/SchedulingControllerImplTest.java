package com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.impl;

import com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.dto.*;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.mapper.DoctorScheduleMapper;
import com.fiap.hackaton.healthmed.scheduling_api.application.ports.input.useCase.CreateDoctorScheduleUseCase;
import com.fiap.hackaton.healthmed.scheduling_api.application.ports.input.useCase.CreateSchedulingUseCase;
import com.fiap.hackaton.healthmed.scheduling_api.domain.model.DoctorSchedule;
import com.fiap.hackaton.healthmed.scheduling_api.domain.model.Scheduling;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SchedulingControllerImplTest {

    @Mock
    private CreateSchedulingUseCase createSchedulingUseCase;

    @Mock
    private CreateDoctorScheduleUseCase createDoctorScheduleUseCase;

    @Mock
    private Jwt jwt;

    @Mock
    private CreateSchedulingRequestDto request;

    @InjectMocks
    private SchedulingControllerImpl schedulingController;

    @Test
    void createScheduleAppointment_shouldReturnOk_whenValidRequestProvided() {
        Scheduling scheduling = new Scheduling(UUID.randomUUID(), request.patientId(), request.doctorId(), request.doctorScheduleId(), request.schedulingDate());

        when(createSchedulingUseCase.createScheduling(any(Scheduling.class))).thenReturn(scheduling);

        ResponseEntity<?> response = schedulingController.createScheduleAppointment(jwt, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void availableSchedules_shouldReturnSchedules_whenValidDoctorIdAndDateProvided() {
        UUID doctorId = UUID.randomUUID();
        UUID doctorScheduleId = UUID.randomUUID();
        var now = LocalDateTime.now();
        var nowPlusOneDay = now.plusDays(1);
        AvailableDoctorSchedulesRequestDto requestDto = new AvailableDoctorSchedulesRequestDto(nowPlusOneDay);
        List<AvailableDoctorSchedules> availableSchedules = List.of(new AvailableDoctorSchedules(doctorId, doctorScheduleId, requestDto.date(), requestDto.date().plusHours(1), requestDto.date().plusHours(2)));

        when(createSchedulingUseCase.getAvailableDoctorSchedules(any(UUID.class), any(LocalDateTime.class))).thenReturn(availableSchedules);

        ResponseEntity<List<AvailableDoctorSchedules>> response = schedulingController.availableSchedules(mock(Jwt.class), doctorId, requestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(availableSchedules, response.getBody());
    }

    @Test
    void createDoctorSchedules_shouldReturnOk_whenValidSchedulesProvided() {
        UUID randomId = UUID.randomUUID();
        var now = LocalDateTime.now();
        var nowPlusOneDay = now.plusDays(1);
        List<CreateDoctorScheduleRequestDto> schedulingRequests = List.of(new CreateDoctorScheduleRequestDto(randomId, now, nowPlusOneDay, nowPlusOneDay));

        ResponseEntity<?> response = schedulingController.createDoctorSchedules(mock(Jwt.class), UUID.randomUUID(), schedulingRequests);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void createDoctorSchedules_shouldReturnBadRequest_whenExceptionThrown() {
        UUID randomId = UUID.randomUUID();
        var now = LocalDateTime.now();
        var nowPlusOneDay = now.plusDays(1);
        List<CreateDoctorScheduleRequestDto> schedulingRequests = List.of(new CreateDoctorScheduleRequestDto(randomId, now, nowPlusOneDay, nowPlusOneDay));

        when(schedulingController.createDoctorSchedules(mock(Jwt.class), UUID.randomUUID(), schedulingRequests)).thenThrow(new RuntimeException("Error"));
        ResponseEntity<?> response = schedulingController.createDoctorSchedules(mock(Jwt.class), UUID.randomUUID(), schedulingRequests);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error", ((ErrorResponseDto) response.getBody()).getMessage());
    }
}
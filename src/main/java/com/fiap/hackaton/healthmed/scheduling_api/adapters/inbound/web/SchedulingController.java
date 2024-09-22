package com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web;

import com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.dto.AvailableDoctorSchedules;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.dto.AvailableDoctorSchedulesRequestDto;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.dto.CreateDoctorScheduleRequestDto;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.dto.CreateSchedulingRequestDto;
import com.fiap.hackaton.healthmed.scheduling_api.common.constants.PathConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(path = PathConstants.SCHEDULES, produces = APPLICATION_JSON_VALUE)
public interface SchedulingController {
    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR')")
    @PostMapping(path = PathConstants.SCHEDULES_APPOINTMENT, consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<?> createScheduleAppointment(@RequestBody CreateSchedulingRequestDto request);

    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR')")
    @GetMapping(path = PathConstants.AVAILABLE_SCHEDULES_DOCTORS, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<List<AvailableDoctorSchedules>> availableSchedules(@AuthenticationPrincipal Jwt jwt, @PathVariable("doctorId") UUID doctorId,
                                                                      @RequestBody AvailableDoctorSchedulesRequestDto requestDto);

    @PreAuthorize("hasAnyRole('DOCTOR')")
    @PostMapping(path = PathConstants.SCHEDULES_DOCTORS, consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<?> createDoctorSchedules(@PathVariable("doctorId") UUID doctorId, @RequestBody List<CreateDoctorScheduleRequestDto> schedulingRequests);

}
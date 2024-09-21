package com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web;

import com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.dto.AvailableDoctorSchedules;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.dto.AvailableDoctorSchedulesRequestDto;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.dto.CreateSchedulingRequestDto;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.dto.SchedulingCreatedDto;
import com.fiap.hackaton.healthmed.scheduling_api.common.constants.PathConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(path = PathConstants.SCHEDULES, produces = APPLICATION_JSON_VALUE)
public interface SchedulingController {
    @PostMapping(path = PathConstants.SCHEDULES_APPOINTMENT, consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<SchedulingCreatedDto> createScheduleAppointment(@RequestBody CreateSchedulingRequestDto request);

    @GetMapping(path = PathConstants.AVAILABLE_SCHEDULES_DOCTORS, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<List<AvailableDoctorSchedules>> availableSchedules(@PathVariable("doctorId") UUID doctorId,
                                                                      @RequestBody AvailableDoctorSchedulesRequestDto requestDto);

}
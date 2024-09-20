package com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web;

import com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.dto.CreateSchedulingRequestDto;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.dto.SchedulingCreatedDto;
import com.fiap.hackaton.healthmed.scheduling_api.common.constants.PathConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(path = PathConstants.SCHEDULE_APPOINTMENT, produces = APPLICATION_JSON_VALUE)
public interface SchedulingController {
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<SchedulingCreatedDto> create(@RequestBody CreateSchedulingRequestDto request);
}
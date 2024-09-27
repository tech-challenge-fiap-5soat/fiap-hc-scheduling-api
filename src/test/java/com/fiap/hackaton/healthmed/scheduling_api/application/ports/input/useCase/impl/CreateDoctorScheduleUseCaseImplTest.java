package com.fiap.hackaton.healthmed.scheduling_api.application.ports.input.useCase.impl;

import com.fiap.hackaton.healthmed.scheduling_api.application.services.DoctorScheduleService;
import com.fiap.hackaton.healthmed.scheduling_api.domain.model.DoctorSchedule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class CreateDoctorScheduleUseCaseImplTest {

    @Mock
    private DoctorScheduleService doctorScheduleService;

    @InjectMocks
    private  CreateDoctorScheduleUseCaseImpl createDoctorScheduleUseCase;

    @Test
    void shouldCreateDoctorSchedules_whenValidSchedulesProvided() {
        var now = LocalDateTime.now();
        var nowPlusOneDay = now.plusDays(1L);
        var nowPlusTwoDays = now.plusDays(2L);
        List<DoctorSchedule> doctorSchedules = List.of(
                new DoctorSchedule(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), nowPlusOneDay, nowPlusOneDay, nowPlusOneDay, "status"),
                new DoctorSchedule(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), nowPlusTwoDays, nowPlusTwoDays, nowPlusTwoDays, "status")
        );

        createDoctorScheduleUseCase.createDoctorSchedules(doctorSchedules);

        Mockito.verify(doctorScheduleService, Mockito.times(1)).createDoctorSchedule(doctorSchedules);
    }


}
package com.fiap.hackaton.healthmed.scheduling_api.application.ports.input.useCase.impl;

import com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.dto.AvailableDoctorSchedules;
import com.fiap.hackaton.healthmed.scheduling_api.application.services.SchedulingService;
import com.fiap.hackaton.healthmed.scheduling_api.domain.model.Scheduling;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateSchedulingUseCaseImplTest {

    @Mock
    private SchedulingService schedulingService;

    @InjectMocks
    private CreateSchedulingUseCaseImpl createSchedulingUseCase;

    @Test
    void createScheduling_shouldSaveScheduling_whenValidSchedulingProvided() {
        Scheduling scheduling = new Scheduling(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), LocalDateTime.now().plusDays(1));

        when(schedulingService.createScheduling(any(Scheduling.class))).thenReturn(scheduling);

        Scheduling result = createSchedulingUseCase.createScheduling(scheduling);

        assertEquals(scheduling, result);
        verify(schedulingService, times(1)).createScheduling(any(Scheduling.class));
    }

    @Test
    void getAvailableDoctorSchedules_shouldReturnSchedules_whenValidDoctorIdAndDateProvided() {
        UUID randomId = UUID.randomUUID();
        LocalDateTime date = LocalDateTime.now().plusDays(1);
        List<AvailableDoctorSchedules> availableSchedules = List.of(
                new AvailableDoctorSchedules(randomId,randomId, date.plusHours(1), date.plusHours(1), date.plusHours(1))
        );

        when(schedulingService.getAvailableDoctorSchedules(any(UUID.class), any(LocalDateTime.class))).thenReturn(availableSchedules);

        List<AvailableDoctorSchedules> result = createSchedulingUseCase.getAvailableDoctorSchedules(randomId, date);

        assertEquals(availableSchedules, result);
        verify(schedulingService, times(1)).getAvailableDoctorSchedules(any(UUID.class), any(LocalDateTime.class));
    }

}
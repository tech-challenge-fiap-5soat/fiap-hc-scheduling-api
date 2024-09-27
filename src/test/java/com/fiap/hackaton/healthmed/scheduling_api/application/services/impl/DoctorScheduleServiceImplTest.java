package com.fiap.hackaton.healthmed.scheduling_api.application.services.impl;

import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.entity.DoctorScheduleEntity;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.entity.DoctorScheduleStatusEnum;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.mapper.DoctorScheduleMapper;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.repository.jpa.JpaDoctorScheduleRepository;
import com.fiap.hackaton.healthmed.scheduling_api.application.services.DoctorScheduleService;
import com.fiap.hackaton.healthmed.scheduling_api.domain.model.DoctorSchedule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorScheduleServiceImplTest {

    @Mock
    private JpaDoctorScheduleRepository jpaDoctorScheduleRepository;

    @InjectMocks
    private DoctorScheduleServiceImpl doctorScheduleService;

    @Test
    void createDoctorSchedule_shouldSaveAllSchedules_whenValidSchedulesProvided() {
        var now = LocalDateTime.now();
        var nowPlusOneDay = now.plusDays(1L);
        var nowPluTwoDays = now.plusDays(2L);
        List<DoctorSchedule> doctorSchedules = List.of(
                new DoctorSchedule(UUID.randomUUID(),UUID.randomUUID(),UUID.randomUUID(), nowPlusOneDay, nowPlusOneDay, nowPlusOneDay, "status"),
                new DoctorSchedule(UUID.randomUUID(),UUID.randomUUID(),UUID.randomUUID(), nowPluTwoDays, nowPluTwoDays, nowPluTwoDays, "status")
        );

        List<DoctorScheduleEntity> entities = DoctorScheduleMapper.toEntityList(doctorSchedules, DoctorScheduleStatusEnum.AVAILABLE);

        when(jpaDoctorScheduleRepository.saveAll(anyList())).thenReturn(entities);

        doctorScheduleService.createDoctorSchedule(doctorSchedules);

        verify(jpaDoctorScheduleRepository, times(1)).saveAll(anyList());
    }

    @Test
    void createDoctorSchedule_shouldThrowException_whenNullScheduleListProvided() {
        assertThrows(NullPointerException.class, () -> doctorScheduleService.createDoctorSchedule(null));
        verify(jpaDoctorScheduleRepository, never()).saveAll(anyList());
    }
}
package com.fiap.hackaton.healthmed.scheduling_api.application.services.impl;

import com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.dto.AvailableDoctorSchedules;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.exceptions.BusinessException;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.exceptions.SchedulingAppointmentLockedException;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.entity.DoctorScheduleEntity;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.entity.DoctorScheduleStatusEnum;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.entity.SchedulingEntity;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.repository.jpa.JpaDoctorScheduleRepository;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.repository.jpa.JpaSchedulingRepository;
import com.fiap.hackaton.healthmed.scheduling_api.domain.model.Scheduling;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SchedulingServiceImplTest {
    @Mock
    private JpaSchedulingRepository jpaSchedulingRepository;

    @Mock
    private JpaDoctorScheduleRepository jpaDoctorScheduleRepository;

    @Mock
    private RedissonClient redissonClient;

    @Mock
    private RLock lock;

    @InjectMocks
    private SchedulingServiceImpl schedulingService;

    @Test
    void createScheduling_shouldThrowException_whenLockNotAcquired() throws InterruptedException {
        Scheduling scheduling = new Scheduling();
        scheduling.setDoctorScheduleId(UUID.randomUUID());
        scheduling.setSchedulingDate(LocalDateTime.now());

        when(redissonClient.getLock("doctor_schedule_lock_" + scheduling.getDoctorScheduleId())).thenReturn(lock);
        when(lock.tryLock(10, 9, TimeUnit.SECONDS)).thenReturn(false);

        assertThrows(SchedulingAppointmentLockedException.class, () -> schedulingService.createScheduling(scheduling));
        verify(redissonClient).getLock("doctor_schedule_lock_" + scheduling.getDoctorScheduleId());
    }

    @Test
    void createScheduling_shouldThrowException_whenDoctorScheduleNotAvailable() throws InterruptedException {
        Scheduling scheduling = new Scheduling();
        scheduling.setDoctorScheduleId(UUID.randomUUID());
        scheduling.setSchedulingDate(LocalDateTime.now());

        DoctorScheduleEntity doctorScheduleEntity = new DoctorScheduleEntity();
        doctorScheduleEntity.setStatus(DoctorScheduleStatusEnum.UNAVAILABLE);

        when(redissonClient.getLock("doctor_schedule_lock_" + scheduling.getDoctorScheduleId())).thenReturn(lock);
        when(lock.tryLock(10, 9, TimeUnit.SECONDS)).thenReturn(true);

        assertThrows(BusinessException.class, () -> schedulingService.createScheduling(scheduling));
    }

    @Test
    void createScheduling_shouldThrowException_whenDoctorScheduleIsInThePast() throws InterruptedException {
        Scheduling scheduling = new Scheduling();
        scheduling.setDoctorScheduleId(UUID.randomUUID());
        scheduling.setSchedulingDate(LocalDateTime.now());

        DoctorScheduleEntity doctorScheduleEntity = new DoctorScheduleEntity();
        doctorScheduleEntity.setStatus(DoctorScheduleStatusEnum.AVAILABLE);
        doctorScheduleEntity.setScheduleStartTime(LocalDateTime.now().minusDays(1));
        when(redissonClient.getLock("doctor_schedule_lock_" + scheduling.getDoctorScheduleId())).thenReturn(lock);
        when(lock.tryLock(10, 9, TimeUnit.SECONDS)).thenReturn(true);

        assertThrows(BusinessException.class, () -> schedulingService.createScheduling(scheduling));
    }

    @Test
    void getAvailableDoctorSchedules_shouldReturnEmptyList_whenNoSchedulesAvailable() {
        UUID doctorId = UUID.randomUUID();
        LocalDateTime date = LocalDateTime.now();

        when(jpaDoctorScheduleRepository.findByDoctorIdAndStatusAndScheduleStartTimeAfter(any(), any(), any()))
                .thenReturn(List.of());

        List<AvailableDoctorSchedules> availableSchedules = schedulingService.getAvailableDoctorSchedules(doctorId, date);

        assertTrue(availableSchedules.isEmpty());
        verify(jpaDoctorScheduleRepository, times(1)).findByDoctorIdAndStatusAndScheduleStartTimeAfter(any(), any(), any());
    }

    @Test
    void createScheduling_shouldSaveScheduling_whenAllConditionsAreMet() throws InterruptedException {
        Scheduling scheduling = new Scheduling();
        scheduling.setDoctorScheduleId(UUID.randomUUID());
        scheduling.setSchedulingDate(LocalDateTime.now().plusDays(1));
        scheduling.setDoctorId(UUID.randomUUID());
        scheduling.setPatientId(UUID.randomUUID());

        DoctorScheduleEntity doctorScheduleEntity = new DoctorScheduleEntity();
        doctorScheduleEntity.setStatus(DoctorScheduleStatusEnum.AVAILABLE);
        doctorScheduleEntity.setScheduleStartTime(LocalDateTime.now().plusDays(1));

        when(jpaDoctorScheduleRepository.findById(scheduling.getDoctorScheduleId())).thenReturn(Optional.of(doctorScheduleEntity));
        when(redissonClient.getLock("doctor_schedule_lock_" + scheduling.getDoctorScheduleId())).thenReturn(lock);
        when(lock.tryLock(10, 9, TimeUnit.SECONDS)).thenReturn(true);
        when(jpaSchedulingRepository.save(any(SchedulingEntity.class))).thenReturn(new SchedulingEntity());

        Scheduling result = schedulingService.createScheduling(scheduling);

        assertNotNull(result);
        verify(jpaSchedulingRepository).save(any(SchedulingEntity.class));
        verify(jpaDoctorScheduleRepository).save(any(DoctorScheduleEntity.class));
    }

    @Test
    void createScheduling_shouldThrowException_whenDoctorIdIsNull() throws InterruptedException {
        Scheduling scheduling = new Scheduling();
        scheduling.setDoctorScheduleId(UUID.randomUUID());
        scheduling.setSchedulingDate(LocalDateTime.now().plusDays(1));
        scheduling.setPatientId(UUID.randomUUID());

        when(redissonClient.getLock("doctor_schedule_lock_" + scheduling.getDoctorScheduleId())).thenReturn(lock);
        when(lock.tryLock(10, 9, TimeUnit.SECONDS)).thenReturn(true);

        assertThrows(BusinessException.class, () -> schedulingService.createScheduling(scheduling));
    }

    @Test
    void createScheduling_shouldThrowException_whenPatientIdIsNull() throws InterruptedException {
        Scheduling scheduling = new Scheduling();
        scheduling.setDoctorScheduleId(UUID.randomUUID());
        scheduling.setSchedulingDate(LocalDateTime.now().plusDays(1));
        scheduling.setDoctorId(UUID.randomUUID());

        when(redissonClient.getLock("doctor_schedule_lock_" + scheduling.getDoctorScheduleId())).thenReturn(lock);
        when(lock.tryLock(10, 9, TimeUnit.SECONDS)).thenReturn(true);

        assertThrows(BusinessException.class, () -> schedulingService.createScheduling(scheduling));
    }
}
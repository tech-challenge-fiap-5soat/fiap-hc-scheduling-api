package com.fiap.hackaton.healthmed.scheduling_api.application.services.impl;

import com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.dto.AvailableDoctorSchedules;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.exceptions.BusinessException;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.exceptions.SchedulingAppointmentLockedException;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.entity.DoctorScheduleEntity;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.entity.DoctorScheduleStatusEnum;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.entity.SchedulingEntity;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.mapper.DoctorScheduleMapper;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.mapper.SchedulingMapper;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.repository.jpa.JpaDoctorScheduleRepository;
import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.repository.jpa.JpaSchedulingRepository;
import com.fiap.hackaton.healthmed.scheduling_api.application.services.SchedulingService;
import com.fiap.hackaton.healthmed.scheduling_api.domain.model.Scheduling;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class SchedulingServiceImpl implements SchedulingService {

    @Autowired
    private final JpaSchedulingRepository jpaSchedulingRepository;

    @Autowired
    private final JpaDoctorScheduleRepository jpaDoctorScheduleRepository;

    @Autowired
    private RedissonClient redissonClient;

    @Transactional
    @Override
    public Scheduling createScheduling(Scheduling scheduling) {
        return createSchedulingWithLock(scheduling);
    }

    @Override
    public List<AvailableDoctorSchedules> getAvailableDoctorSchedules(UUID doctorId, LocalDateTime date) {
        List<DoctorScheduleEntity> doctorScheduleEntities = jpaDoctorScheduleRepository.findByDoctorIdAndStatusAndScheduleStartTimeAfter(
                doctorId, DoctorScheduleStatusEnum.AVAILABLE, date
        );
        return DoctorScheduleMapper.toAvailableDoctorSchedulesList(doctorScheduleEntities);
    }

    private Scheduling createSchedulingWithLock(Scheduling scheduling) {
        RLock lock = redissonClient.getLock("doctor_schedule_lock_" + scheduling.getDoctorScheduleId());
        try {
            System.out.println("Trying to acquire lock");
            if (lock.tryLock(10, 9, TimeUnit.SECONDS)) {
                try {
                    System.out.println("Sleeping for 6 seconds");
                    Thread.sleep(3000);
                    System.out.println("Finished sleeping");

                    SchedulingEntity saved = saveScheduling(scheduling);
                    DoctorScheduleEntity doctorScheduleEntity = saveDoctorSchedule(scheduling);
                    return SchedulingMapper.toDomain(saved);
                } finally {
                    System.out.println("Trying to unlock");
                    lock.unlock();
                }
            } else {
                throw new SchedulingAppointmentLockedException("Não foi possível adquirir o lock. Tente novamente mais tarde.");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Falha ao tentar adquirir o lock.", e);
        }
    }

    private SchedulingEntity saveScheduling(Scheduling scheduling) {
        SchedulingEntity schedulingEntity = SchedulingMapper.toEntity(scheduling);
        if (!isValidSchedule(scheduling)) {
            throw new BusinessException("Invalid schedule");
        }

        DoctorScheduleEntity doctorSchedule = jpaDoctorScheduleRepository.findById(scheduling.getDoctorScheduleId()).get();
        if (!isStillAvailable(doctorSchedule)) {
            throw new BusinessException("doctor schedule is not available");
        }

        if(!isFutureSchedule(doctorSchedule)) {
            throw new BusinessException("doctor schedule its already past");
        }
        return jpaSchedulingRepository.save(schedulingEntity);
    }

    private DoctorScheduleEntity saveDoctorSchedule(Scheduling scheduling) {
        DoctorScheduleEntity doctorScheduleEntity = jpaDoctorScheduleRepository.findById(scheduling.getDoctorScheduleId()).get();
        doctorScheduleEntity.setStatus(DoctorScheduleStatusEnum.UNAVAILABLE);
        return jpaDoctorScheduleRepository.save(doctorScheduleEntity);
    }


    private Boolean isStillAvailable(DoctorScheduleEntity doctorSchedule) {
        return doctorSchedule.getStatus().equals(DoctorScheduleStatusEnum.AVAILABLE);
    }

    private Boolean isFutureSchedule(DoctorScheduleEntity doctorSchedule) {
        return doctorSchedule.getScheduleStartTime().isAfter(LocalDateTime.now());
    }

    private Boolean isValidSchedule(Scheduling scheduling) {
        return  !scheduling.getSchedulingDate().toLocalDate().isBefore(LocalDate.now()) &&
                scheduling.getDoctorId() != null &&
                scheduling.getDoctorScheduleId() != null &&
                scheduling.getPatientId() != null;
    }
}
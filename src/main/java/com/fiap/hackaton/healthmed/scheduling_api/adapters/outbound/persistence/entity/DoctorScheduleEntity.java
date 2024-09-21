package com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

import static jakarta.persistence.TemporalType.TIMESTAMP;

@Entity
@Table(name = "doctor_schedule")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "doctor_id")
    private UUID doctorId;

    @Column(name = "schedule_date")
    @Temporal(TIMESTAMP)
    private LocalDateTime scheduleDate;

    @Column(name = "schedule_start_time")
    @Temporal(TIMESTAMP)
    private LocalDateTime scheduleStartTime;

    @Column(name = "schedule_end_time")
    @Temporal(TIMESTAMP)
    private LocalDateTime scheduleEndTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private DoctorScheduleStatusEnum status;

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TIMESTAMP)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TIMESTAMP)
    private LocalDateTime updatedAt;
}
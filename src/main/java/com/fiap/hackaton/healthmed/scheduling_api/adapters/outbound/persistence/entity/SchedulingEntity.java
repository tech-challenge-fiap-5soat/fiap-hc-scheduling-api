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
@Table(name = "scheduling")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SchedulingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "doctor_id")
    private UUID doctorId;

    @Column(name = "patient_id")
    private UUID patientId;

    @Column(name = "doctor_schedule_id")
    private UUID doctorScheduleId;

    @Column(name = "scheduling_date")
    private LocalDateTime schedulingDate;

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TIMESTAMP)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TIMESTAMP)
    private LocalDateTime updatedAt;
}
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
@Table(name = "doctor")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorEntity {
    @Id
    private UUID id;

    @Column(name = "active")
    private Boolean active;

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TIMESTAMP)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TIMESTAMP)
    private LocalDateTime updatedAt;
}
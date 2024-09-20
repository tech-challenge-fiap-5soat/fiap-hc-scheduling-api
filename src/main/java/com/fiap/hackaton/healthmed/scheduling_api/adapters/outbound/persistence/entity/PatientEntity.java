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
@Table(name = "patient")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "email")
    private String email;

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TIMESTAMP)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TIMESTAMP)
    private LocalDateTime updatedAt;
}
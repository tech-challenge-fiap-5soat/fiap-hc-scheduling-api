package com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.repository.jpa;

import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.entity.SchedulingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaSchedulingRepository extends JpaRepository<SchedulingEntity, UUID> { }
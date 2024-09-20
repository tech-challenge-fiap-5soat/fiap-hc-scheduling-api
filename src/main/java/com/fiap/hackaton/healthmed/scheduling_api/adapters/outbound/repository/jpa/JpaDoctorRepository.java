package com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.repository.jpa;

import com.fiap.hackaton.healthmed.scheduling_api.adapters.outbound.persistence.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDoctorRepository extends JpaRepository<DoctorEntity, Long> { }
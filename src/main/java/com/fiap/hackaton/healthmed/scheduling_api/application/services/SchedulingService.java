package com.fiap.hackaton.healthmed.scheduling_api.application.services;

import com.fiap.hackaton.healthmed.scheduling_api.domain.model.Scheduling;

public interface SchedulingService {
    Scheduling createScheduling(Scheduling scheduling);
}
package com.fiap.hackaton.healthmed.scheduling_api.application.ports.input.useCase;

import com.fiap.hackaton.healthmed.scheduling_api.domain.model.Scheduling;

public interface CreateSchedulingUseCase {
    Scheduling createScheduling(Scheduling scheduling);
}
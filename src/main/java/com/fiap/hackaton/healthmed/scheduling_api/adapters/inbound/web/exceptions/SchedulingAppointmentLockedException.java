package com.fiap.hackaton.healthmed.scheduling_api.adapters.inbound.web.exceptions;

public class SchedulingAppointmentLockedException extends RuntimeException {
    public SchedulingAppointmentLockedException(String message) {
        super(message);
    }
}
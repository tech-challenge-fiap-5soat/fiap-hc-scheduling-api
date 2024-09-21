package com.fiap.hackaton.healthmed.scheduling_api.common.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PathConstants {

    public static final String SCHEDULES = "/schedules";
    public static final String SCHEDULES_APPOINTMENT = "/appointment";
    public static final String AVAILABLE_SCHEDULES_DOCTORS = "/doctors/{doctorId}/availables";
}
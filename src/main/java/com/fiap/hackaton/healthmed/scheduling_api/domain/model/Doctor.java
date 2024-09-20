package com.fiap.hackaton.healthmed.scheduling_api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Doctor {
    private UUID id;
    private String name;
    private String cpf;
    private String crm;
    private String email;
}
package com.fivecods.infrastructure.input.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProfesionalRequest {

    @NotBlank(message = "El nombre es obligatorio")
    public String nombres;

    @NotBlank(message = "Los apellidos son obligatorios")
    public String apellidos;

    @NotBlank(message = "La especialidad es obligatoria")
    public String especialidad;

    @NotNull(message = "El estado activo es obligatorio")
    public Boolean estadoActivo;
}
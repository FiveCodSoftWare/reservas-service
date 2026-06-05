package com.fivecods.infrastructure.input.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservaRequest {

    @NotNull(message = "La fecha es obligatoria")
    public LocalDate fecha;

    @NotNull(message = "La hora de inicio es obligatoria")
    public LocalTime horaInicio;

    @NotNull(message = "La hora de fin es obligatoria")
    public LocalTime horaFin;

    @NotBlank(message = "El id del cliente es obligatorio")
    public String clienteId;

    @NotBlank(message = "El id del profesional es obligatorio")
    public String profesionalId;
}
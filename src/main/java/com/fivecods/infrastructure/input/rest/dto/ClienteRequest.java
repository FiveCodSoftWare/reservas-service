package com.fivecods.infrastructure.input.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ClienteRequest {

    @NotBlank(message = "El nombre es obligatorio")
    public String nombres;

    @NotBlank(message = "Los apellidos son obligatorios")
    public String apellidos;

    @Email(message = "El email debe ser válido")
    @NotBlank(message = "El email es obligatorio")
    public String email;

    @NotBlank(message = "El teléfono es obligatorio")
    public String telefono;

    @NotNull(message = "El estado activo es obligatorio")
    public Boolean estadoActivo;
}
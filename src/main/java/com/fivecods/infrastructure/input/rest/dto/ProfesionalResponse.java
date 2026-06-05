package com.fivecods.infrastructure.input.rest.dto;

import com.fivecods.domain.model.Profesional;

public class ProfesionalResponse {
    public String id;
    public String nombres;
    public String apellidos;
    public String especialidad;
    public Boolean estadoActivo;

    public static ProfesionalResponse from(Profesional domain) {
        ProfesionalResponse response = new ProfesionalResponse();
        response.id = domain.getId();
        response.nombres = domain.getNombres();
        response.apellidos = domain.getApellidos();
        response.especialidad = domain.getEspecialidad();
        response.estadoActivo = domain.getEstadoActivo();
        return response;
    }
}
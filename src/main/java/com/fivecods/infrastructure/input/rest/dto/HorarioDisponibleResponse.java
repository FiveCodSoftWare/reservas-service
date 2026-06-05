package com.fivecods.infrastructure.input.rest.dto;


import com.fivecods.domain.model.HorarioDisponible;

import java.time.LocalDate;
import java.time.LocalTime;


public class HorarioDisponibleResponse {
    public String id;
    public String profesionalId;
    public String profesionalNombre;
    public LocalDate fecha;
    public LocalTime horaInicio;
    public LocalTime horaFin;
    public Boolean estado;

    public static HorarioDisponibleResponse from(HorarioDisponible domain) {
        HorarioDisponibleResponse response = new HorarioDisponibleResponse();
        response.id = domain.getId();
        response.profesionalId = domain.getProfesional().getId();
        response.profesionalNombre = domain.getProfesional().getNombres()
                + " " + domain.getProfesional().getApellidos();
        response.fecha = domain.getFecha();
        response.horaInicio = domain.getHoraInicio();
        response.horaFin = domain.getHoraFin();
        response.estado = domain.getEstado();
        return response;
    }
}
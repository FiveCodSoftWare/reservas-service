package com.fivecods.infrastructure.input.rest.dto;

import com.fivecods.domain.model.Reserva;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservaResponse {
    public String id;
    public LocalDate fecha;
    public LocalTime horaInicio;
    public LocalTime horaFin;
    public String clienteId;
    public String clienteNombre;
    public String profesionalId;
    public String profesionalNombre;
    public String estado;

    public static ReservaResponse from(Reserva domain) {
        ReservaResponse response = new ReservaResponse();
        response.id = domain.getId();
        response.fecha = domain.getFecha();
        response.horaInicio = domain.getHoraInicio();
        response.horaFin = domain.getHoraFin();
        response.clienteId = domain.getCliente().getId();
        response.clienteNombre = domain.getCliente().getNombres()
                + " " + domain.getCliente().getApellidos();
        response.profesionalId = domain.getProfesional().getId();
        response.profesionalNombre = domain.getProfesional().getNombres()
                + " " + domain.getProfesional().getApellidos();
        response.estado = domain.getEstado().name();
        return response;
    }
}




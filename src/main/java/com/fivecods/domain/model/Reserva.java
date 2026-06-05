package com.fivecods.domain.model;


import java.time.LocalDate;
import java.time.LocalTime;

public class Reserva {
    private String id;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Cliente cliente;
    private Profesional profesional;
    private EstadoReserva estado;

    public enum EstadoReserva {
        CREADA, CANCELADA, COMPLETADA
    }

    public Reserva() {}

    public Reserva(String id, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin,
                   Cliente cliente, Profesional profesional, EstadoReserva estado) {
        this.id = id;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.cliente = cliente;
        this.profesional = profesional;
        this.estado = estado;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }
    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Profesional getProfesional() { return profesional; }
    public void setProfesional(Profesional profesional) { this.profesional = profesional; }
    public EstadoReserva getEstado() { return estado; }
    public void setEstado(EstadoReserva estado) { this.estado = estado; }
}
package com.fivecods.domain.model;


import java.time.LocalDate;
import java.time.LocalTime;

public class HorarioDisponible {
    private String id;
    private Profesional profesional;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Boolean estado;

    public HorarioDisponible() {}

    public HorarioDisponible(String id, Profesional profesional, LocalDate fecha,
                             LocalTime horaInicio, LocalTime horaFin, Boolean estado) {
        this.id = id;
        this.profesional = profesional;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.estado = estado;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Profesional getProfesional() { return profesional; }
    public void setProfesional(Profesional profesional) { this.profesional = profesional; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }
    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }
    public Boolean getEstado() { return estado; }
    public void setEstado(Boolean estado) { this.estado = estado; }
}
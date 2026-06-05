package com.fivecods.domain.model;

public class Profesional {
    private String id;
    private String nombres;
    private String apellidos;
    private String especialidad;
    private Boolean estadoActivo;

    public Profesional() {}

    public Profesional(String id, String nombres, String apellidos, String especialidad, Boolean estadoActivo) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.especialidad = especialidad;
        this.estadoActivo = estadoActivo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Boolean getEstadoActivo() {
        return estadoActivo;
    }

    public void setEstadoActivo(Boolean estadoActivo) {
        this.estadoActivo = estadoActivo;
    }
}

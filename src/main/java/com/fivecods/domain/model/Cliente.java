package com.fivecods.domain.model;


public class Cliente {
    private String id;
    private String nombres;
    private String apellidos;
    private String email;
    private String telefono;
    private Boolean estadoActivo;

    public Cliente() {}

    public Cliente(String id, String nombres, String apellidos,
                   String email, String telefono, Boolean estadoActivo) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.estadoActivo = estadoActivo;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public Boolean getEstadoActivo() { return estadoActivo; }
    public void setEstadoActivo(Boolean estadoActivo) { this.estadoActivo = estadoActivo; }
}
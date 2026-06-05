package com.fivecods.infrastructure.input.rest.dto;

import com.fivecods.domain.model.Cliente;

public class ClienteResponse {
    public String id;
    public String nombres;
    public String apellidos;
    public String email;
    public String telefono;
    public Boolean estadoActivo;

    public static ClienteResponse from(Cliente domain) {
        ClienteResponse response = new ClienteResponse();
        response.id = domain.getId();
        response.nombres = domain.getNombres();
        response.apellidos = domain.getApellidos();
        response.email = domain.getEmail();
        response.telefono = domain.getTelefono();
        response.estadoActivo = domain.getEstadoActivo();
        return response;
    }
}
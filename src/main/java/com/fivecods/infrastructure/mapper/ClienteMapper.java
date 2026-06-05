package com.fivecods.infrastructure.mapper;

import com.fivecods.domain.model.Cliente;
import com.fivecods.infrastructure.output.persistence.entity.ClienteEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClienteMapper {

    public Cliente toDomain(ClienteEntity entity) {
        if (entity == null) return null;
        return new Cliente(
                entity.id,
                entity.nombres,
                entity.apellidos,
                entity.email,
                entity.telefono,
                entity.estadoActivo
        );
    }

    public ClienteEntity toEntity(Cliente domain) {
        if (domain == null) return null;
        ClienteEntity entity = new ClienteEntity();
        entity.id = domain.getId();
        entity.nombres = domain.getNombres();
        entity.apellidos = domain.getApellidos();
        entity.email = domain.getEmail();
        entity.telefono = domain.getTelefono();
        entity.estadoActivo = domain.getEstadoActivo();
        return entity;
    }
}
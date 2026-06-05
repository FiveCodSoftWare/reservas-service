package com.fivecods.infrastructure.mapper;

import com.fivecods.domain.model.Profesional;
import com.fivecods.infrastructure.output.persistence.entity.ProfesionalEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProfesionalMapper {


    public Profesional toDomain(ProfesionalEntity entity) {
        if (entity == null) return null;
        return new Profesional(
                entity.id,
                entity.nombres,
                entity.apellidos,
                entity.especialidad,
                entity.estadoActivo
        );
    }

    public ProfesionalEntity toEntity(Profesional domain) {
        if (domain == null) return null;
        ProfesionalEntity entity = new ProfesionalEntity();
        entity.id = domain.getId();
        entity.nombres = domain.getNombres();
        entity.apellidos = domain.getApellidos();
        entity.especialidad = domain.getEspecialidad();
        entity.estadoActivo = domain.getEstadoActivo();
        return entity;
    }
}

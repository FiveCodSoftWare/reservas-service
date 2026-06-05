package com.fivecods.infrastructure.mapper;

import com.fivecods.domain.model.HorarioDisponible;
import com.fivecods.infrastructure.output.persistence.entity.HorarioDisponibleEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class HorarioDisponibleMapper {

    @Inject
    ProfesionalMapper profesionalMapper;

    public HorarioDisponible toDomain(HorarioDisponibleEntity entity) {
        if (entity == null) return null;
        return new HorarioDisponible(
                entity.id,
                profesionalMapper.toDomain(entity.profesional),
                entity.fecha,
                entity.horaInicio,
                entity.horaFin,
                entity.estado
        );
    }

    public HorarioDisponibleEntity toEntity(HorarioDisponible domain) {
        if (domain == null) return null;
        HorarioDisponibleEntity entity = new HorarioDisponibleEntity();
        entity.id = domain.getId();
        entity.profesional = profesionalMapper.toEntity(domain.getProfesional());
        entity.fecha = domain.getFecha();
        entity.horaInicio = domain.getHoraInicio();
        entity.horaFin = domain.getHoraFin();
        entity.estado = domain.getEstado();
        return entity;
    }
}
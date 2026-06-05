package com.fivecods.infrastructure.mapper;

import com.fivecods.domain.model.Reserva;
import com.fivecods.infrastructure.output.persistence.entity.ReservaEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ReservaMapper {

    @Inject
    ClienteMapper clienteMapper;

    @Inject
    ProfesionalMapper profesionalMapper;

    public Reserva toDomain(ReservaEntity entity) {
        if (entity == null) return null;
        Reserva.EstadoReserva estado = entity.estado != null
                ? Reserva.EstadoReserva.valueOf(entity.estado.name())
                : Reserva.EstadoReserva.CREADA;
        return new Reserva(
                entity.id,
                entity.fecha,
                entity.horaInicio,
                entity.horaFin,
                clienteMapper.toDomain(entity.cliente),
                profesionalMapper.toDomain(entity.profesional),
                estado
        );
    }

    public ReservaEntity toEntity(Reserva domain) {
        if (domain == null) return null;
        ReservaEntity entity = new ReservaEntity();
        entity.id = domain.getId();
        entity.fecha = domain.getFecha();
        entity.horaInicio = domain.getHoraInicio();
        entity.horaFin = domain.getHoraFin();
        entity.cliente = clienteMapper.toEntity(domain.getCliente());
        entity.profesional = profesionalMapper.toEntity(domain.getProfesional());
        if (domain.getEstado() != null) {
            entity.estado = ReservaEntity.EstadoReserva.valueOf(domain.getEstado().name());
        }
        return entity;
    }
}
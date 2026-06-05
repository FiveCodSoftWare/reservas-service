package com.fivecods.infrastructure.output.persistence.repository;

import com.fivecods.infrastructure.output.persistence.entity.ReservaEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@ApplicationScoped
public class ReservaRepository implements PanacheRepositoryBase<ReservaEntity, String> {

    public Uni<List<ReservaEntity>> listarTodas() {
        return list("FROM ReservaEntity r LEFT JOIN FETCH r.profesional LEFT JOIN FETCH r.cliente");
    }


    public Uni<List<ReservaEntity>> buscarSolapamientosProfesional(
            String profesionalId, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
        return list("""
            FROM ReservaEntity r
            WHERE r.profesional.id = ?1
            AND r.fecha = ?2
            AND r.estado = ?5
            AND r.horaInicio < ?4
            AND r.horaFin > ?3
            """, profesionalId, fecha, horaInicio, horaFin, ReservaEntity.EstadoReserva.CREADA);
    }

    public Uni<ReservaEntity> buscarPorId(String id) {
        return find("FROM ReservaEntity r LEFT JOIN FETCH r.cliente LEFT JOIN FETCH r.profesional WHERE r.id = ?1",
                id).firstResult();
    }
}
package com.fivecods.infrastructure.output.persistence.repository;

import com.fivecods.infrastructure.output.persistence.entity.HorarioDisponibleEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@ApplicationScoped
public class HorarioDisponibleRepository implements PanacheRepositoryBase<HorarioDisponibleEntity, String> {


    public Uni<List<HorarioDisponibleEntity>> buscarPorProfesional(String profesionalId) {
        return list("FROM HorarioDisponibleEntity h LEFT JOIN FETCH h.profesional WHERE h.profesional.id = ?1 AND h.estado = true",
                profesionalId);
    }

    public Uni<List<HorarioDisponibleEntity>> buscarSolapamientos(
            String profesionalId, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
        return list("""
                FROM HorarioDisponibleEntity h
                WHERE h.profesional.id = ?1
                AND h.fecha = ?2
                AND h.estado = true
                AND h.horaInicio < ?4
                AND h.horaFin > ?3
                """, profesionalId, fecha, horaInicio, horaFin);
    }

    public Uni<List<HorarioDisponibleEntity>> buscarHorarioQueCobreIntervalo(
            String profesionalId, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
        return list("""
                FROM HorarioDisponibleEntity h
                WHERE h.profesional.id = ?1
                AND h.fecha = ?2
                AND h.estado = true
                AND h.horaInicio <= ?3
                AND h.horaFin >= ?4
                """, profesionalId, fecha, horaInicio, horaFin);
    }

    public Uni<HorarioDisponibleEntity> buscarPorIdConProfesional(String id) {
        return find("FROM HorarioDisponibleEntity h LEFT JOIN FETCH h.profesional WHERE h.id = ?1", id)
                .firstResult();
    }
}
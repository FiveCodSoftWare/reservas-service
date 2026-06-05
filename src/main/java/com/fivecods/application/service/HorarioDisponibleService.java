package com.fivecods.application.service;

import com.fivecods.domain.model.HorarioDisponible;
import com.fivecods.domain.service.HorarioDisponibleDomainService;
import com.fivecods.infrastructure.exceptions.BadRequestException;
import com.fivecods.infrastructure.exceptions.ConflictException;
import com.fivecods.infrastructure.exceptions.UnprocessableException;
import com.fivecods.infrastructure.mapper.HorarioDisponibleMapper;
import com.fivecods.infrastructure.output.persistence.repository.HorarioDisponibleRepository;
import com.fivecods.infrastructure.output.persistence.repository.ProfesionalRepository;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class HorarioDisponibleService implements HorarioDisponibleDomainService {

    @Inject
    HorarioDisponibleRepository horarioRepository;

    @Inject
    ProfesionalRepository profesionalRepository;

    @Inject
    HorarioDisponibleMapper horarioMapper;

    @Override
    @WithTransaction
    public Uni<HorarioDisponible> registrar(HorarioDisponible horario) {
        if (horario.getHoraInicio().isAfter(horario.getHoraFin()) ||
                horario.getHoraInicio().equals(horario.getHoraFin())) {
            return Uni.createFrom().failure(
                    new BadRequestException("La hora de inicio debe ser anterior a la hora de fin"));
        }

        return profesionalRepository.buscarPorId(horario.getProfesional().getId())
                .onItem().ifNull().failWith(() ->
                        new UnprocessableException("Profesional no encontrado"))
                .flatMap(profesional -> {
                    if (!profesional.estadoActivo) {
                        return Uni.createFrom().failure(
                                new BadRequestException("El profesional no está activo"));
                    }
                    return horarioRepository.buscarSolapamientos(
                            profesional.id,
                            horario.getFecha(),
                            horario.getHoraInicio(),
                            horario.getHoraFin()
                    ).flatMap(solapamientos -> {
                        if (!solapamientos.isEmpty()) {
                            return Uni.createFrom().failure(
                                    new ConflictException("Ya existe un horario que se solapa en ese intervalo"));
                        }
                        var entity = horarioMapper.toEntity(horario);
                        entity.profesional = profesional;
                        return horarioRepository.persist(entity)
                                .flatMap(saved -> horarioRepository.buscarPorIdConProfesional(saved.id));
                    });
                })
                .map(horarioMapper::toDomain);
    }

    @Override
    @WithSession
    public Uni<List<HorarioDisponible>> listarPorProfesional(String profesionalId) {
        return horarioRepository.buscarPorProfesional(profesionalId)
                .map(entities -> entities.stream()
                        .map(horarioMapper::toDomain)
                        .collect(Collectors.toList()));
    }

    @Override
    @WithSession
    public Uni<HorarioDisponible> buscarPorId(String id) {
        return horarioRepository.buscarPorIdConProfesional(id)
                .onItem().ifNull().failWith(() ->
                        new UnprocessableException("Horario no encontrado con id: " + id))
                .map(horarioMapper::toDomain);
    }
}
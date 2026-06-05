package com.fivecods.application.service;

import com.fivecods.domain.model.Reserva;
import com.fivecods.domain.service.ReservaDomainService;
import com.fivecods.infrastructure.exceptions.BadRequestException;
import com.fivecods.infrastructure.exceptions.ConflictException;
import com.fivecods.infrastructure.exceptions.UnprocessableException;
import com.fivecods.infrastructure.mapper.ReservaMapper;
import com.fivecods.infrastructure.output.persistence.entity.ReservaEntity;
import com.fivecods.infrastructure.output.persistence.repository.ClienteRepository;
import com.fivecods.infrastructure.output.persistence.repository.HorarioDisponibleRepository;
import com.fivecods.infrastructure.output.persistence.repository.ProfesionalRepository;
import com.fivecods.infrastructure.output.persistence.repository.ReservaRepository;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class ReservaService implements ReservaDomainService {

    @Inject
    ReservaRepository reservaRepository;

    @Inject
    ProfesionalRepository profesionalRepository;

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    HorarioDisponibleRepository horarioRepository;

    @Inject
    ReservaMapper reservaMapper;

    @Override
    @WithTransaction
    public Uni<Reserva> registrar(Reserva reserva) {
        if (reserva.getHoraInicio().isAfter(reserva.getHoraFin()) ||
                reserva.getHoraInicio().equals(reserva.getHoraFin())) {
            return Uni.createFrom().failure(
                    new BadRequestException("La hora de inicio debe ser anterior a la hora de fin"));
        }

        var profesionalId = reserva.getProfesional().getId();
        var clienteId = reserva.getCliente().getId();

        return profesionalRepository.buscarPorId(profesionalId)
                .onItem().ifNull().failWith(() ->
                        new UnprocessableException("Profesional no encontrado"))
                .flatMap(profesional -> {
                    if (!profesional.estadoActivo) {
                        return Uni.createFrom().failure(
                                new BadRequestException("El profesional no está activo"));
                    }
                    return clienteRepository.buscarPorId(clienteId)
                            .onItem().ifNull().failWith(() ->
                                    new UnprocessableException("Cliente no encontrado"))
                            .flatMap(cliente -> {
                                if (!cliente.estadoActivo) {
                                    return Uni.createFrom().failure(
                                            new BadRequestException("El cliente no está activo"));
                                }
                                return horarioRepository.buscarHorarioQueCobreIntervalo(
                                        profesionalId,
                                        reserva.getFecha(),
                                        reserva.getHoraInicio(),
                                        reserva.getHoraFin()
                                ).flatMap(horarios -> {
                                    if (horarios.isEmpty()) {
                                        return Uni.createFrom().failure(
                                                new BadRequestException(
                                                        "No existe horario disponible que cubra el intervalo solicitado"));
                                    }
                                    return reservaRepository.buscarSolapamientosProfesional(
                                            profesionalId,
                                            reserva.getFecha(),
                                            reserva.getHoraInicio(),
                                            reserva.getHoraFin()
                                    ).flatMap(solapamientos -> {
                                        if (!solapamientos.isEmpty()) {
                                            return Uni.createFrom().failure(
                                                    new ConflictException(
                                                            "El profesional ya tiene una reserva activa en ese intervalo"));
                                        }
                                        var entity = reservaMapper.toEntity(reserva);
                                        entity.profesional = profesional;
                                        entity.cliente = cliente;
                                        entity.estado = ReservaEntity.EstadoReserva.CREADA;
                                        return reservaRepository.persist(entity)
                                                .flatMap(saved -> reservaRepository.buscarPorId(saved.id));
                                    });
                                });
                            });
                })
                .map(reservaMapper::toDomain);
    }

    @Override
    @WithTransaction
    public Uni<Reserva> cancelar(String id) {
        return reservaRepository.buscarPorId(id)
                .onItem().ifNull().failWith(() ->
                        new UnprocessableException("Reserva no encontrada con id: " + id))
                .flatMap(entity -> {
                    if (entity.estado == ReservaEntity.EstadoReserva.CANCELADA) {
                        return Uni.createFrom().failure(
                                new BadRequestException("La reserva ya está cancelada"));
                    }
                    entity.estado = ReservaEntity.EstadoReserva.CANCELADA;
                    return reservaRepository.persist(entity)
                            .flatMap(saved -> reservaRepository.buscarPorId(saved.id));
                })
                .map(reservaMapper::toDomain);
    }

    @Override
    @WithSession
    public Uni<List<Reserva>> listarTodas() {
        return reservaRepository.listarTodas()
                .map(entities -> entities.stream()
                        .map(reservaMapper::toDomain)
                        .collect(Collectors.toList()));
    }

    @Override
    @WithSession
    public Uni<Map<String, List<Reserva>>> listarAgrupadasPorFecha() {
        return reservaRepository.listarTodas()
                .map(entities -> entities.stream()
                        .map(reservaMapper::toDomain)
                        .collect(Collectors.groupingBy(
                                r -> r.getFecha().toString()
                        ))
                );
    }
}
package com.fivecods.application.service;

import com.fivecods.domain.model.Profesional;
import com.fivecods.domain.service.ProfesionalDomainService;
import com.fivecods.infrastructure.exceptions.UnprocessableException;
import com.fivecods.infrastructure.mapper.ProfesionalMapper;
import com.fivecods.infrastructure.output.persistence.entity.ReservaEntity;
import com.fivecods.infrastructure.output.persistence.repository.ProfesionalRepository;
import com.fivecods.infrastructure.output.persistence.repository.ReservaRepository;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.jboss.logging.Logger;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProfesionalService implements ProfesionalDomainService {

    private static final Logger LOG = Logger.getLogger(ProfesionalService.class);

    @Inject
    ProfesionalRepository profesionalRepository;

    @Inject
    ReservaRepository reservaRepository;

    @Inject
    ProfesionalMapper profesionalMapper;

    @Override
    @WithTransaction
    public Uni<Profesional> crear(Profesional profesional) {
        var entity = profesionalMapper.toEntity(profesional);
        return profesionalRepository.persist(entity)
                .map(profesionalMapper::toDomain);
    }

    @Override
    @WithTransaction
    public Uni<Profesional> actualizar(String id, Profesional profesional) {
        return profesionalRepository.buscarPorId(id)
                .onItem().ifNull().failWith(() ->
                        new UnprocessableException("Profesional no encontrado con id: " + id))
                .flatMap(entity -> {
                    entity.nombres = profesional.getNombres();
                    entity.apellidos = profesional.getApellidos();
                    entity.especialidad = profesional.getEspecialidad();
                    entity.estadoActivo = profesional.getEstadoActivo();
                    return profesionalRepository.persist(entity);
                })
                .map(profesionalMapper::toDomain);
    }

    @Override
    @WithSession
    public Uni<Profesional> buscarPorId(String id) {
        return profesionalRepository.buscarPorId(id)
                .onItem().ifNull().failWith(() ->
                        new UnprocessableException("Profesional no encontrado con id: " + id))
                .map(profesionalMapper::toDomain);
    }

    @Override
    @WithSession
    public Uni<List<Profesional>> listarTodos() {
        return profesionalRepository.listAll()
                .map(entities -> entities.stream()
                        .map(profesionalMapper::toDomain)
                        .collect(Collectors.toList()));
    }

    @Override
    @WithTransaction
    public Uni<Void> eliminar(String id) {
        return profesionalRepository.buscarPorId(id)
                .onItem().ifNull().failWith(() ->
                        new UnprocessableException("Profesional no encontrado con id: " + id))
                .flatMap(entity -> profesionalRepository.delete(entity));
    }

    @Override
    @WithSession
    @Retry(maxRetries = 3, delay = 200, delayUnit = ChronoUnit.MILLIS)
    @Fallback(fallbackMethod = "listarOrdenadosFallback")
    public Uni<List<Profesional>> listarOrdenadosPorReservasActivas() {
        return reservaRepository.listarTodas()
                .flatMap(reservas -> profesionalRepository.listAll()
                        .map(profesionales -> {
                            var conteoReservas = reservas.stream()
                                    .filter(r -> r.estado == ReservaEntity.EstadoReserva.CREADA)
                                    .collect(Collectors.groupingBy(
                                            r -> r.profesional.id,
                                            Collectors.counting()
                                    ));
                            return profesionales.stream()
                                    .sorted(Comparator.comparingLong(
                                            (p) -> -conteoReservas.getOrDefault(p.id, 0L)
                                    ))
                                    .map(profesionalMapper::toDomain)
                                    .collect(Collectors.toList());
                        })
                )
                .onFailure().invoke(e -> LOG.errorf("Error en listarOrdenadosPorReservasActivas: %s", e.getMessage()));
    }

    @WithTransaction
    public Uni<List<Profesional>> listarOrdenadosFallback() {
        LOG.warn("Fallback activado para listarOrdenadosPorReservasActivas");
        return profesionalRepository.listAll()
                .map(entities -> entities.stream()
                        .map(profesionalMapper::toDomain)
                        .collect(Collectors.toList()));
    }
}
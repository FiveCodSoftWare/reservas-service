package com.fivecods.application.service;

import com.fivecods.domain.model.Cliente;
import com.fivecods.domain.service.ClienteDomainService;
import com.fivecods.infrastructure.exceptions.ConflictException;
import com.fivecods.infrastructure.exceptions.UnprocessableException;
import com.fivecods.infrastructure.mapper.ClienteMapper;
import com.fivecods.infrastructure.output.persistence.repository.ClienteRepository;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ClienteService implements ClienteDomainService {

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    ClienteMapper clienteMapper;

    @Override
    @WithTransaction
    public Uni<Cliente> crear(Cliente cliente) {
        return clienteRepository.buscarPorEmail(cliente.getEmail())
                .flatMap(existente -> {
                    if (existente != null) {
                        return Uni.createFrom().failure(
                                new ConflictException("Ya existe un cliente con email: " + cliente.getEmail()));
                    }
                    var entity = clienteMapper.toEntity(cliente);
                    return clienteRepository.persist(entity);
                })
                .map(clienteMapper::toDomain);
    }

    @Override
    @WithTransaction
    public Uni<Cliente> actualizar(String id, Cliente cliente) {
        return clienteRepository.buscarPorId(id)
                .onItem().ifNull().failWith(() ->
                        new UnprocessableException("Cliente no encontrado con id: " + id))
                .flatMap(entity -> clienteRepository.buscarPorEmail(cliente.getEmail())
                        .flatMap(existente -> {
                            if (existente != null && !existente.id.equals(id)) {
                                return Uni.createFrom().failure(
                                        new ConflictException("Ya existe un cliente con email: " + cliente.getEmail()));
                            }
                            entity.nombres = cliente.getNombres();
                            entity.apellidos = cliente.getApellidos();
                            entity.email = cliente.getEmail();
                            entity.telefono = cliente.getTelefono();
                            entity.estadoActivo = cliente.getEstadoActivo();
                            return clienteRepository.persist(entity);
                        })
                )
                .map(clienteMapper::toDomain);
    }

    @Override
    @WithSession
    public Uni<Cliente> buscarPorId(String id) {
        return clienteRepository.buscarPorId(id)
                .onItem().ifNull().failWith(() ->
                        new UnprocessableException("Cliente no encontrado con id: " + id))
                .map(clienteMapper::toDomain);
    }

    @Override
    @WithSession
    public Uni<List<Cliente>> listarTodos() {
        return clienteRepository.listAll()
                .map(entities -> entities.stream()
                        .map(clienteMapper::toDomain)
                        .collect(Collectors.toList()));
    }

    @Override
    @WithTransaction
    public Uni<Void> eliminar(String id) {
        return clienteRepository.buscarPorId(id)
                .onItem().ifNull().failWith(() ->
                        new UnprocessableException("Cliente no encontrado con id: " + id))
                .flatMap(entity -> clienteRepository.delete(entity));
    }
}
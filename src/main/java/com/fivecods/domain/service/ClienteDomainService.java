package com.fivecods.domain.service;

import com.fivecods.domain.model.Cliente;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface ClienteDomainService {
    Uni<Cliente> crear(Cliente cliente);
    Uni<Cliente> actualizar(String id, Cliente cliente);
    Uni<Cliente> buscarPorId(String id);
    Uni<List<Cliente>> listarTodos();
    Uni<Void> eliminar(String id);
}
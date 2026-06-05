package com.fivecods.domain.service;


import com.fivecods.domain.model.Profesional;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface ProfesionalDomainService {
    Uni<Profesional> crear(Profesional profesional);
    Uni<Profesional> actualizar(String id, Profesional profesional);
    Uni<Profesional> buscarPorId(String id);
    Uni<List<Profesional>> listarTodos();
    Uni<Void> eliminar(String id);
    Uni<List<Profesional>> listarOrdenadosPorReservasActivas();
}
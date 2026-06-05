package com.fivecods.infrastructure.output.persistence.repository;

import com.fivecods.infrastructure.output.persistence.entity.ProfesionalEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ProfesionalRepository implements PanacheRepositoryBase<ProfesionalEntity, String> {

    public Uni<List<ProfesionalEntity>> listarActivos() {
        return list("estadoActivo = true");
    }

    public Uni<ProfesionalEntity> buscarPorId(String id) {
        return findById(id);
    }
}
package com.fivecods.infrastructure.output.persistence.repository;

import com.fivecods.infrastructure.output.persistence.entity.ClienteEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClienteRepository implements PanacheRepositoryBase<ClienteEntity, String> {

    public Uni<ClienteEntity> buscarPorId(String id) {
        return findById(id);
    }

    public Uni<ClienteEntity> buscarPorEmail(String email) {
        return find("email", email).firstResult();
    }
}

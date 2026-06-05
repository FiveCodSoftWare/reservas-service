package com.fivecods.infrastructure.output.persistence.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tbl_cliente")
public class ClienteEntity extends PanacheEntityBase {

    @Id
    @Column(name = "cli_id", length = 36)
    public String id;

    @Column(name = "cli_nombres", nullable = false, length = 100)
    public String nombres;

    @Column(name = "cli_apellidos", nullable = false, length = 100)
    public String apellidos;

    @Column(name = "cli_email", nullable = false, length = 150, unique = true)
    public String email;

    @Column(name = "cli_telefono", nullable = false, length = 20)
    public String telefono;

    @Column(name = "cli_estado_activo", nullable = false)
    public Boolean estadoActivo;

    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }
}

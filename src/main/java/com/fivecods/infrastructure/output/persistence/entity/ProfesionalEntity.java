package com.fivecods.infrastructure.output.persistence.entity;


import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "tbl_profesional")
public class ProfesionalEntity extends PanacheEntityBase {
    @Id
    @Column(name = "pro_id", length = 36)
    public String id;

    @Column(name = "pro_nombres", nullable = false, length = 100)
    public String nombres;

    @Column(name = "pro_apellidos", nullable = false, length = 100)
    public String apellidos;

    @Column(name = "pro_especialidad", nullable = false, length = 100)
    public String especialidad;

    @Column(name = "pro_estado_activo", nullable = false)
    public Boolean estadoActivo;

    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }
}



package com.fivecods.infrastructure.output.persistence.entity;


import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "tbl_horario_disponible")
public class HorarioDisponibleEntity extends PanacheEntityBase {

    @Id
    @Column(name = "hor_id", length = 36)
    public String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hor_profesional_id", nullable = false)
    public ProfesionalEntity profesional;

    @Column(name = "hor_fecha", nullable = false)
    public LocalDate fecha;

    @Column(name = "hor_hora_inicio", nullable = false)
    public LocalTime horaInicio;

    @Column(name = "hor_hora_fin", nullable = false)
    public LocalTime horaFin;

    @Column(name = "hor_estado", nullable = false)
    public Boolean estado;

    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
        if (this.estado == null) {
            this.estado = true;
        }
    }

}

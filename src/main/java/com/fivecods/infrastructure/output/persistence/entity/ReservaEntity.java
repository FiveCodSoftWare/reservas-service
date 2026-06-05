package com.fivecods.infrastructure.output.persistence.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "tbl_reserva")
public class ReservaEntity extends PanacheEntityBase {

    @Id
    @Column(name = "res_id", length = 36)
    public String id;

    @Column(name = "res_fecha", nullable = false)
    public LocalDate fecha;

    @Column(name = "res_hora_inicio", nullable = false)
    public LocalTime horaInicio;

    @Column(name = "res_hora_fin", nullable = false)
    public LocalTime horaFin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "res_cliente_id", nullable = false)
    public ClienteEntity cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "res_profesional_id", nullable = false)
    public ProfesionalEntity profesional;

    @Enumerated(EnumType.STRING)
    @Column(name = "res_estado", nullable = false, length = 20)
    public EstadoReserva estado;

    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
        if (this.estado == null) {
            this.estado = EstadoReserva.CREADA;
        }
    }

    public enum EstadoReserva {
        CREADA, CANCELADA, COMPLETADA
    }
}

package com.fivecods.domain.service;

import com.fivecods.domain.model.Reserva;
import io.smallrye.mutiny.Uni;

import java.util.List;
import java.util.Map;

public interface ReservaDomainService {
    Uni<Reserva> registrar(Reserva reserva);
    Uni<Reserva> cancelar(String id);
    Uni<List<Reserva>> listarTodas();
    Uni<Map<String, List<Reserva>>> listarAgrupadasPorFecha();
}

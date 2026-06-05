package com.fivecods.domain.service;

import com.fivecods.domain.model.HorarioDisponible;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface HorarioDisponibleDomainService {
    Uni<HorarioDisponible> registrar(HorarioDisponible horario);
    Uni<List<HorarioDisponible>> listarPorProfesional(String profesionalId);
    Uni<HorarioDisponible> buscarPorId(String id);
}
package com.fivecods.infrastructure.input.rest;

import com.fivecods.application.service.HorarioDisponibleService;
import com.fivecods.domain.model.HorarioDisponible;
import com.fivecods.domain.model.Profesional;
import com.fivecods.infrastructure.input.rest.dto.HorarioDisponibleRequest;
import com.fivecods.infrastructure.input.rest.dto.HorarioDisponibleResponse;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;

@Path("/api/v1/horarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Horarios", description = "Gestión de horarios disponibles")
public class HorarioDisponibleResource {

    @Inject
    HorarioDisponibleService horarioService;

    @POST
    @Operation(summary = "Registrar horario disponible")
    public Uni<Response> registrar(@Valid HorarioDisponibleRequest request) {
        HorarioDisponible horario = new HorarioDisponible();
        Profesional profesional = new Profesional();
        profesional.setId(request.profesionalId);
        horario.setProfesional(profesional);
        horario.setFecha(request.fecha);
        horario.setHoraInicio(request.horaInicio);
        horario.setHoraFin(request.horaFin);
        horario.setEstado(true);

        return horarioService.registrar(horario)
                .map(h -> Response.status(Response.Status.CREATED)
                        .entity(HorarioDisponibleResponse.from(h)).build());
    }

    @GET
    @Path("/profesional/{profesionalId}")
    @Operation(summary = "Listar horarios por profesional")
    public Uni<Response> listarPorProfesional(@PathParam("profesionalId") String profesionalId) {
        return horarioService.listarPorProfesional(profesionalId)
                .map(lista -> {
                    List<HorarioDisponibleResponse> response = lista.stream()
                            .map(HorarioDisponibleResponse::from)
                            .collect(Collectors.toList());
                    return Response.ok(response).build();
                });
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar horario por id")
    public Uni<Response> buscarPorId(@PathParam("id") String id) {
        return horarioService.buscarPorId(id)
                .map(h -> Response.ok(HorarioDisponibleResponse.from(h)).build());
    }
}
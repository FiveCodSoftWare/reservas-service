package com.fivecods.infrastructure.input.rest;

import com.fivecods.application.service.ReservaService;
import com.fivecods.domain.model.Cliente;
import com.fivecods.domain.model.Profesional;
import com.fivecods.domain.model.Reserva;
import com.fivecods.infrastructure.input.rest.dto.ReservaRequest;
import com.fivecods.infrastructure.input.rest.dto.ReservaResponse;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/api/v1/reservas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Reservas", description = "Gestión de reservas")
public class ReservaResource {

    @Inject
    ReservaService reservaService;

    @POST
    @Operation(summary = "Registrar reserva")
    public Uni<Response> registrar(@Valid ReservaRequest request) {
        Reserva reserva = new Reserva();
        reserva.setFecha(request.fecha);
        reserva.setHoraInicio(request.horaInicio);
        reserva.setHoraFin(request.horaFin);

        Cliente cliente = new Cliente();
        cliente.setId(request.clienteId);
        reserva.setCliente(cliente);

        Profesional profesional = new Profesional();
        profesional.setId(request.profesionalId);
        reserva.setProfesional(profesional);

        return reservaService.registrar(reserva)
                .map(r -> Response.status(Response.Status.CREATED)
                        .entity(ReservaResponse.from(r)).build());
    }

    @PATCH
    @Path("/{id}/cancelar")
    @Operation(summary = "Cancelar reserva")
    public Uni<Response> cancelar(@PathParam("id") String id) {
        return reservaService.cancelar(id)
                .map(r -> Response.ok(ReservaResponse.from(r)).build());
    }

    @GET
    @Operation(summary = "Listar todas las reservas")
    public Uni<Response> listarTodas() {
        return reservaService.listarTodas()
                .map(lista -> {
                    List<ReservaResponse> response = lista.stream()
                            .map(ReservaResponse::from)
                            .collect(Collectors.toList());
                    return Response.ok(response).build();
                });
    }

    @GET
    @Path("/agrupadas/por-fecha")
    @Operation(summary = "Listar reservas agrupadas por fecha")
    public Uni<Response> listarAgrupadasPorFecha() {
        return reservaService.listarAgrupadasPorFecha()
                .map(mapa -> {
                    Map<String, List<ReservaResponse>> response = mapa.entrySet().stream()
                            .collect(Collectors.toMap(
                                    Map.Entry::getKey,
                                    e -> e.getValue().stream()
                                            .map(ReservaResponse::from)
                                            .collect(Collectors.toList())
                            ));
                    return Response.ok(response).build();
                });
    }
}
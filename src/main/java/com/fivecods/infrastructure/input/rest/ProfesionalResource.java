package com.fivecods.infrastructure.input.rest;

import com.fivecods.application.service.ProfesionalService;
import com.fivecods.domain.model.Profesional;
import com.fivecods.infrastructure.input.rest.dto.ProfesionalRequest;
import com.fivecods.infrastructure.input.rest.dto.ProfesionalResponse;
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

@Path("/api/v1/profesionales")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Profesionales", description = "Gestión de profesionales")
public class ProfesionalResource {

    @Inject
    ProfesionalService profesionalService;

    @POST
    @Operation(summary = "Crear profesional")
    public Uni<Response> crear(@Valid ProfesionalRequest request) {
        Profesional profesional = new Profesional();
        profesional.setNombres(request.nombres);
        profesional.setApellidos(request.apellidos);
        profesional.setEspecialidad(request.especialidad);
        profesional.setEstadoActivo(request.estadoActivo);

        return profesionalService.crear(profesional)
                .map(p -> Response.status(Response.Status.CREATED)
                        .entity(ProfesionalResponse.from(p)).build());
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Actualizar profesional")
    public Uni<Response> actualizar(@PathParam("id") String id,
                                    @Valid ProfesionalRequest request) {
        Profesional profesional = new Profesional();
        profesional.setNombres(request.nombres);
        profesional.setApellidos(request.apellidos);
        profesional.setEspecialidad(request.especialidad);
        profesional.setEstadoActivo(request.estadoActivo);

        return profesionalService.actualizar(id, profesional)
                .map(p -> Response.ok(ProfesionalResponse.from(p)).build());
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar profesional por id")
    public Uni<Response> buscarPorId(@PathParam("id") String id) {
        return profesionalService.buscarPorId(id)
                .map(p -> Response.ok(ProfesionalResponse.from(p)).build());
    }

    @GET
    @Operation(summary = "Listar todos los profesionales")
    public Uni<Response> listarTodos() {
        return profesionalService.listarTodos()
                .map(lista -> {
                    List<ProfesionalResponse> response = lista.stream()
                            .map(ProfesionalResponse::from)
                            .collect(Collectors.toList());
                    return Response.ok(response).build();
                });
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar profesional")
    public Uni<Response> eliminar(@PathParam("id") String id) {
        return profesionalService.eliminar(id)
                .map(v -> Response.noContent().build());
    }

    @GET
    @Path("/ranking/reservas-activas")
    @Operation(summary = "Listar profesionales ordenados por reservas activas")
    public Uni<Response> listarPorReservasActivas() {
        return profesionalService.listarOrdenadosPorReservasActivas()
                .map(lista -> {
                    List<ProfesionalResponse> response = lista.stream()
                            .map(ProfesionalResponse::from)
                            .collect(Collectors.toList());
                    return Response.ok(response).build();
                });
    }
}
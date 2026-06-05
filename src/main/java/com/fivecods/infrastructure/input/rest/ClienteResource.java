package com.fivecods.infrastructure.input.rest;

import com.fivecods.application.service.ClienteService;
import com.fivecods.domain.model.Cliente;
import com.fivecods.infrastructure.input.rest.dto.ClienteRequest;
import com.fivecods.infrastructure.input.rest.dto.ClienteResponse;
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

@Path("/api/v1/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Clientes", description = "Gestión de clientes")
public class ClienteResource {

    @Inject
    ClienteService clienteService;

    @POST
    @Operation(summary = "Crear cliente")
    public Uni<Response> crear(@Valid ClienteRequest request) {
        Cliente cliente = new Cliente();
        cliente.setNombres(request.nombres);
        cliente.setApellidos(request.apellidos);
        cliente.setEmail(request.email);
        cliente.setTelefono(request.telefono);
        cliente.setEstadoActivo(request.estadoActivo);

        return clienteService.crear(cliente)
                .map(c -> Response.status(Response.Status.CREATED)
                        .entity(ClienteResponse.from(c)).build());
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Actualizar cliente")
    public Uni<Response> actualizar(@PathParam("id") String id,
                                    @Valid ClienteRequest request) {
        Cliente cliente = new Cliente();
        cliente.setNombres(request.nombres);
        cliente.setApellidos(request.apellidos);
        cliente.setEmail(request.email);
        cliente.setTelefono(request.telefono);
        cliente.setEstadoActivo(request.estadoActivo);

        return clienteService.actualizar(id, cliente)
                .map(c -> Response.ok(ClienteResponse.from(c)).build());
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar cliente por id")
    public Uni<Response> buscarPorId(@PathParam("id") String id) {
        return clienteService.buscarPorId(id)
                .map(c -> Response.ok(ClienteResponse.from(c)).build());
    }

    @GET
    @Operation(summary = "Listar todos los clientes")
    public Uni<Response> listarTodos() {
        return clienteService.listarTodos()
                .map(lista -> {
                    List<ClienteResponse> response = lista.stream()
                            .map(ClienteResponse::from)
                            .collect(Collectors.toList());
                    return Response.ok(response).build();
                });
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar cliente")
    public Uni<Response> eliminar(@PathParam("id") String id) {
        return clienteService.eliminar(id)
                .map(v -> Response.noContent().build());
    }
}
package com.fivecods.infrastructure.exceptions;

import com.fivecods.infrastructure.util.ErrorCodes;
import com.fivecods.infrastructure.util.ErrorUserMessages;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;
import java.util.List;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {

    private static final Logger LOG = Logger.getLogger(GlobalExceptionHandler.class);

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(Exception exception) {
        LOG.errorf("Exception capturada: %s", exception.getMessage());

        String url = uriInfo != null ? uriInfo.getRequestUri().toString() : "";

        // Unwrap excepciones envueltas por Mutiny
        Throwable cause = exception.getCause();
        if (cause instanceof UnprocessableException ex) {
            return buildResponse(422,
                    ErrorUserMessages.UNPROCESSABLE,
                    ErrorCodes.UNPROCESSABLE,
                    ex.getMessage(), url);
        }
        if (cause instanceof ConflictException ex) {
            return buildResponse(409,
                    ErrorUserMessages.CONFLICT,
                    ErrorCodes.CONFLICT,
                    ex.getMessage(), url);
        }
        if (cause instanceof BadRequestException ex) {
            return buildResponse(400,
                    ErrorUserMessages.BAD_REQUEST,
                    ErrorCodes.BAD_REQUEST,
                    ex.getMessage(), url);
        }

        // 404 — Endpoint no existe
        if (exception instanceof jakarta.ws.rs.NotFoundException ||
                exception instanceof EndpointNotFoundException) {
            return buildResponse(404,
                    ErrorUserMessages.NOT_FOUND,
                    ErrorCodes.NOT_FOUND,
                    "El endpoint solicitado no existe", url);
        }

        // 422 — Registro no encontrado en BD
        if (exception instanceof UnprocessableException ex) {
            return buildResponse(422,
                    ErrorUserMessages.UNPROCESSABLE,
                    ErrorCodes.UNPROCESSABLE,
                    ex.getMessage(), url);
        }

        // 409 — Conflicto de negocio
        if (exception instanceof ConflictException ex) {
            return buildResponse(409,
                    ErrorUserMessages.CONFLICT,
                    ErrorCodes.CONFLICT,
                    ex.getMessage(), url);
        }

        // 400 — Request inválido
        if (exception instanceof BadRequestException ex) {
            return buildResponse(400,
                    ErrorUserMessages.BAD_REQUEST,
                    ErrorCodes.BAD_REQUEST,
                    ex.getMessage(), url);
        }

        // 500 — Error interno
        return buildResponse(500,
                ErrorUserMessages.INTERNAL_ERROR,
                ErrorCodes.INTERNAL_ERROR,
                "An unexpected error occurred on the server.", url);
    }

    private Response buildResponse(int status, String userMessage,
                                   String errorCode, String message, String url) {
        ApiErrorResponse response = new ApiErrorResponse(
                status,
                userMessage,
                List.of(new DetailErrorResponse(errorCode, message, url))
        );
        return Response.status(status).entity(response).build();
    }
}
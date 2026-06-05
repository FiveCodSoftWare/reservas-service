package com.fivecods.infrastructure.exceptions;

import com.fivecods.infrastructure.util.ErrorCodes;
import com.fivecods.infrastructure.util.ErrorUserMessages;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.List;
import java.util.stream.Collectors;


@Provider
public class ConstraintViolationExceptionMapper
        implements ExceptionMapper<ConstraintViolationException> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        String url = uriInfo != null ? uriInfo.getRequestUri().toString() : "";

        List<DetailErrorResponse> errors = exception.getConstraintViolations()
                .stream()
                .map(v -> {
                    String path = v.getPropertyPath().toString();
                    String field = path.contains(".") ?
                            path.substring(path.lastIndexOf(".") + 1) : path;
                    return new DetailErrorResponse(
                            ErrorCodes.VALIDATION,
                            field + ": " + v.getMessage(),
                            url
                    );
                })
                .collect(Collectors.toList());

        ApiErrorResponse response = new ApiErrorResponse(
                400,
                ErrorUserMessages.BAD_REQUEST,
                errors
        );
        return Response.status(400).entity(response).build();
    }
}
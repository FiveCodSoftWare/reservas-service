package com.fivecods.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.*;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;
import java.io.IOException;
import java.util.UUID;

@Provider
@Priority(1)
public class LoggingInterceptor implements ContainerRequestFilter, ContainerResponseFilter {

    private static final Logger LOG = Logger.getLogger(LoggingInterceptor.class);
    private static final String TRACE_ID_KEY = "X-Trace-Id";

    @Inject
    ObjectMapper mapper;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String traceId = requestContext.getHeaderString(TRACE_ID_KEY);
        if (traceId == null || traceId.isBlank()) {
            traceId = UUID.randomUUID().toString();
        }
        requestContext.setProperty(TRACE_ID_KEY, traceId);

        LOG.infof(">>> REQUEST  | TraceId: %s | Metodo: %s | URI: %s | Content-Type: %s",
                traceId,
                requestContext.getMethod(),
                requestContext.getUriInfo().getRequestUri(),
                requestContext.getHeaderString("Content-Type"));
    }

    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {
        String traceId = (String) requestContext.getProperty(TRACE_ID_KEY);
        responseContext.getHeaders().add(TRACE_ID_KEY, traceId);

        String responseBody = "(sin body)";
        try {
            if (responseContext.getEntity() != null) {
                responseBody = mapper.writeValueAsString(responseContext.getEntity());
            }
        } catch (Exception e) {
            responseBody = responseContext.getEntity().toString();
        }

        LOG.infof("<<< RESPONSE | TraceId: %s | Metodo: %s | URI: %s | Status: %d | Body: %s",
                traceId,
                requestContext.getMethod(),
                requestContext.getUriInfo().getRequestUri(),
                responseContext.getStatus(),
                responseBody);
    }
}
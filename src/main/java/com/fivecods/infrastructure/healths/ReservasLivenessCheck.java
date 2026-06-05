package com.fivecods.infrastructure.healths;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.health.Readiness;

@Liveness
@ApplicationScoped
public class ReservasLivenessCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.named("Reservas Service Liveness")
                .up()
                .withData("service", "reservas-service")
                .withData("version", "1.0.0")
                .withData("status", "running")
                .build();
    }
}
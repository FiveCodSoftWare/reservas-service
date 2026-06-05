package com.fivecods;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GreetingResourceTest {

    static String profesionalId;
    static String clienteId;
    static final String EMAIL_TEST = "marco." + System.currentTimeMillis() + "@test.com";

    @Test
    @Order(1)
    public void testCrearProfesional() {
        profesionalId = given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                            "nombres": "Carlos",
                            "apellidos": "Mendoza",
                            "especialidad": "Mentoria",
                            "estadoActivo": true
                        }
                        """)
                .when().post("/api/v1/profesionales")
                .then()
                .statusCode(201)
                .body("nombres", equalTo("Carlos"))
                .body("apellidos", equalTo("Mendoza"))
                .body("id", notNullValue())
                .extract().path("id");
    }

    @Test
    @Order(2)
    public void testCrearProfesionalSinNombre() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                            "nombres": "",
                            "apellidos": "Mendoza",
                            "especialidad": "Mentoria",
                            "estadoActivo": true
                        }
                        """)
                .when().post("/api/v1/profesionales")
                .then()
                .statusCode(400)
                .body("statusCode", equalTo(400))
                .body("errors", not(empty()))
                .body("errors[0].errorCode", equalTo("E400"));
    }

    @Test
    @Order(3)
    public void testCrearCliente() {
        clienteId = given()
                .contentType(ContentType.JSON)
                .body(String.format("""
                    {
                        "nombres": "Marco",
                        "apellidos": "Diaz",
                        "email": "%s",
                        "telefono": "987654321",
                        "estadoActivo": true
                    }
                    """, EMAIL_TEST))
                .when().post("/api/v1/clientes")
                .then()
                .statusCode(201)
                .body("nombres", equalTo("Marco"))
                .body("email", equalTo(EMAIL_TEST))
                .body("id", notNullValue())
                .extract().path("id");
    }

    @Test
    @Order(4)
    public void testCrearClienteEmailInvalido() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                            "nombres": "Marco",
                            "apellidos": "Diaz",
                            "email": "email-invalido",
                            "telefono": "987654321",
                            "estadoActivo": true
                        }
                        """)
                .when().post("/api/v1/clientes")
                .then()
                .statusCode(400)
                .body("statusCode", equalTo(400))
                .body("errors", not(empty()))
                .body("errors[0].errorCode", equalTo("E400"));
    }

    @Test
    @Order(5)
    public void testListarProfesionales() {
        given()
                .when().get("/api/v1/profesionales")
                .then()
                .statusCode(200)
                .body("$", not(empty()));
    }

    @Test
    @Order(6)
    public void testListarClientes() {
        given()
                .when().get("/api/v1/clientes")
                .then()
                .statusCode(200)
                .body("$", not(empty()));
    }

    @Test
    @Order(7)
    public void testRegistrarHorario() {
        given()
                .contentType(ContentType.JSON)
                .body(String.format("""
                        {
                            "profesionalId": "%s",
                            "fecha": "2026-07-01",
                            "horaInicio": "08:00:00",
                            "horaFin": "12:00:00"
                        }
                        """, profesionalId))
                .when().post("/api/v1/horarios")
                .then()
                .statusCode(201)
                .body("profesionalNombre", notNullValue());
    }

    @Test
    @Order(8)
    public void testRegistrarReserva() {
        given()
                .contentType(ContentType.JSON)
                .body(String.format("""
                        {
                            "fecha": "2026-07-01",
                            "horaInicio": "08:00:00",
                            "horaFin": "10:00:00",
                            "clienteId": "%s",
                            "profesionalId": "%s"
                        }
                        """, clienteId, profesionalId))
                .when().post("/api/v1/reservas")
                .then()
                .statusCode(201)
                .body("estado", equalTo("CREADA"))
                .body("clienteNombre", notNullValue())
                .body("profesionalNombre", notNullValue());
    }


    @Test
    @Order(9)
    public void testProfesionalNoEncontrado() {
        given()
                .when().get("/api/v1/profesionales/uuid-falso-123")
                .then()
                .statusCode(422)
                .body("statusCode", equalTo(422))
                .body("errors", not(empty()))
                .body("errors[0].errorCode", equalTo("E422"));
    }

    @Test
    @Order(10)
    public void testRankingProfesionales() {
        given()
                .when().get("/api/v1/profesionales/ranking/reservas-activas")
                .then()
                .statusCode(200)
                .body("$", not(empty()));
    }

    @Test
    @Order(11)
    public void testReservasAgrupadasPorFecha() {
        given()
                .when().get("/api/v1/reservas/agrupadas/por-fecha")
                .then()
                .statusCode(200);
    }
}
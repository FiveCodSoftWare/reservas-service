# Reservas Service 🗓️

Sistema de gestión de reservas y disponibilidad de profesionales (psicología, mentorías, asesorías, tutorías) desarrollado con Quarkus.

---

## ⚙️ Requisitos previos

- Java 21
- Maven 3.9+
- Docker Desktop

---

## 🚀 Cómo ejecutar el proyecto

### 1. Clonar el repositorio

    git clone <url-del-repositorio>
    cd reservas-service

### 2. Levantar la base de datos

    docker-compose up -d mysql

### 3. Ejecutar la aplicación

    mvn quarkus:dev

La app arranca en: http://localhost:8080

> Flyway ejecuta las migraciones automáticamente al iniciar.

### ⚠️ Windows — logs con tildes y ñ

Si los logs muestran caracteres corruptos, ejecuta esto antes de correr la app:

    chcp 65001

---

## 📖 Documentación de endpoints

    http://localhost:8080/q/swagger-ui

---

## ❤️ Health Check

    http://localhost:8080/q/health

---

## 🧪 Ejecutar tests

    mvn test

---

## 🐳 Docker

Levantar todo (app + base de datos):

    docker-compose up -d

Generar imagen JVM:

    docker build -f src/main/docker/Dockerfile.jvm -t reservas-service:jvm .

Generar imagen Nativa (GraalVM):

    docker build -f src/main/docker/Dockerfile.native -t reservas-service:native .

---

## 📬 Colección Postman

Importar el archivo `reservas-service.postman_collection.json` en Postman.

---

## 🏗️ Arquitectura

DDD ligero con tres capas:

    com.fivecods
    ├── domain          # Modelos e interfaces de negocio
    ├── application     # Casos de uso (lógica de negocio)
    └── infrastructure  # REST, BD, excepciones, mappers, config

---

## 📋 Endpoints

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | /api/v1/profesionales | Crear profesional |
| GET | /api/v1/profesionales | Listar profesionales |
| GET | /api/v1/profesionales/{id} | Buscar por id |
| PUT | /api/v1/profesionales/{id} | Actualizar |
| DELETE | /api/v1/profesionales/{id} | Eliminar |
| GET | /api/v1/profesionales/ranking/reservas-activas | Ranking por reservas activas |
| POST | /api/v1/clientes | Crear cliente |
| GET | /api/v1/clientes | Listar clientes |
| GET | /api/v1/clientes/{id} | Buscar por id |
| PUT | /api/v1/clientes/{id} | Actualizar |
| DELETE | /api/v1/clientes/{id} | Eliminar |
| POST | /api/v1/horarios | Registrar horario disponible |
| GET | /api/v1/horarios/profesional/{id} | Horarios por profesional |
| GET | /api/v1/horarios/{id} | Buscar horario por id |
| POST | /api/v1/reservas | Registrar reserva |
| PATCH | /api/v1/reservas/{id}/cancelar | Cancelar reserva |
| GET | /api/v1/reservas | Listar todas las reservas |
| GET | /api/v1/reservas/agrupadas/por-fecha | Reservas agrupadas por fecha |

---

## 🔒 Reglas de negocio

- No se permiten horarios solapados para un mismo profesional.
- Una reserva solo puede crearse si existe un horario disponible que cubra el intervalo.
- Un profesional no puede tener solapamientos con otras reservas activas.
- Cliente y profesional deben estar activos para crear una reserva.
- Cancelar una reserva libera la disponibilidad del profesional.

---

## 🛠️ Decisiones técnicas

| Decisión | Detalle |
|----------|---------|
| **Mutiny + Hibernate Reactive** | Todos los endpoints son reactivos, sin bloquear el IO thread |
| **@WithTransaction / @WithSession** | Escritura con transacción, lectura con sesión |
| **Flyway** | Migraciones automáticas al iniciar |
| **BIT en vez de BOOLEAN** | MySQL mapea BOOLEAN como tinyint, Hibernate Reactive espera BIT |
| **Prefijos en BD** | tbl_ para tablas, pro_, cli_, hor_, res_ para columnas |
| **Fault Tolerance** | Ranking protegido con @Retry y @Fallback |
| **TraceId** | Cada request genera X-Trace-Id para trazabilidad en logs |
| **Errores estandarizados** | Formato consistente con statusCode, userMessage, errors, timestamp |
| **Programación funcional** | Ranking y agrupación por fecha procesados con Streams |

---

## 📦 Stack tecnológico

- Quarkus 3.33.2
- Java 21
- Hibernate Reactive + Panache
- MySQL 8.0
- Flyway
- SmallRye OpenAPI
- SmallRye Fault Tolerance
- Docker + Docker Compose

---

## 📝 Notas adicionales

- **Logs centralizados**: cada request y response queda registrado con un `TraceId` único, método HTTP, URI, status y body, lo que facilita el rastreo de problemas en cualquier entorno.
- **Mensajes de respuesta estandarizados**: tanto los errores como los datos siguen un formato consistente inspirado en patrones de APIs reales de producción.
- **Proyecto educativo**: este proyecto fue desarrollado con fines educativos como evaluación final del curso de Quarkus de MitoCode. No debe usarse en producción sin las debidas revisiones de seguridad.
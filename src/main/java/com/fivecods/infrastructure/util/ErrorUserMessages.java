package com.fivecods.infrastructure.util;

public class ErrorUserMessages {
    public static final String BAD_REQUEST =
            "La solicitud posee una sintaxis incorrecta o falta parámetro(s) requerido(s).";
    public static final String NOT_FOUND =
            "El recurso solicitado no se encontró en este servidor.";
    public static final String CONFLICT =
            "La solicitud no pudo ser procesada debido a un conflicto.";
    public static final String UNPROCESSABLE =
            "La solicitud no pudo ser procesada debido a errores de validación.";
    public static final String INTERNAL_ERROR =
            "El servidor encontró una condición inesperada que le impidió cumplir con la solicitud.";

}

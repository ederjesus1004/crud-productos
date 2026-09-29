package eder.dev.productos.exception;

import java.time.LocalDateTime;
import java.util.Map;

// Formato único para todos los errores de la API.
// Así el frontend siempre sabe qué forma tiene un error.
public record ErrorResponse(
        LocalDateTime fecha,
        int estado,
        String mensaje,
        Map<String, String> errores
) {
}

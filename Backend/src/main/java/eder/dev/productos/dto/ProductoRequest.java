package eder.dev.productos.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

// DTO de ENTRADA: datos que el frontend envía para crear o editar.
// "record" ya trae constructor, getters, equals y toString (no hace falta Lombok).
public record ProductoRequest(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "EL nombre no puede tener mas de 100 caracteres")
        String nombre,

        @Size(max = 255, message = "La descripcion no puede tener mas de 255 caracteres")
        String descripcion,

        @NotNull(message = "El precio es obligatiorio")
        @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
        BigDecimal precio,

        @NotNull(message = "El stock es obligatorio")
        @PositiveOrZero(message = "El stock no puede ser negativo")
        Integer stock
) {
}

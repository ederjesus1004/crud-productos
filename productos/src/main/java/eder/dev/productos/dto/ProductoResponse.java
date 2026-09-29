package eder.dev.productos.dto;

import eder.dev.productos.model.Producto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// DTO de SALIDA: datos que devolvemos al frontend
public record ProductoResponse(
        Long id,
        String nombre,
        String descripcion,
        BigDecimal precio,
        Integer stock,
        LocalDateTime fechaCreacion

) {
    // Convierte una entidad en un DTO de respuesta
    public static ProductoResponse desdeEntidad(Producto producto){
    return new ProductoResponse(
            producto.getId(),
            producto.getNombre(),
            producto.getDescripcion(),
            producto.getPrecio(),
            producto.getStock(),
            producto.getFechaCreacion()
        );
    }
}

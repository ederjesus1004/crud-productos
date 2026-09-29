package eder.dev.productos.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// @Entity indica que esta clase es una tabla en la base de datos
@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor  // constructor vacío que JPA necesita
public class Producto {
    // Llave primaria autoincremental
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 255)
    private String descripcion;

    // Para dinero se usa BigDecimal, nunca double (evita errores de redondeo)
    @Column(nullable = false, precision = 10,scale = 2)
    private BigDecimal precio;

    @Column(nullable = false)
    private Integer stock;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    // Se ejecuta justo antes de guardar por primera vez
    @PrePersist
    void alCrear(){
        this.fechaCreacion = LocalDateTime.now();
    }
}

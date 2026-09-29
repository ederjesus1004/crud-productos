package eder.dev.productos.controller;

import eder.dev.productos.dto.ProductoRequest;
import eder.dev.productos.dto.ProductoResponse;
import eder.dev.productos.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController: todas las respuestas salen como JSON
// @RequestMapping: ruta base de todos los endpoints
@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {
    // Depende de la interfaz, no de la implementación
    private final ProductoService productoService;

    // GET /api/productos -> lista todos
    @GetMapping
    public ResponseEntity<List<ProductoResponse>> listar(){
        return ResponseEntity.ok(productoService.listar());
    }

    // GET /api/productos/5 -> trae uno por id
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> obtenerPorId(@PathVariable Long id){
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }
    // POST /api/productos -> crea uno nuevo
    // @Valid activa las validaciones de ProductoRequest
    @PostMapping
    public ResponseEntity<ProductoResponse> crear (@Valid @RequestBody ProductoRequest request){
        ProductoResponse creado = productoService.crear(request);
        // 201 Created es el código correcto al crear
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }
    // PUT /api/productos/5 -> actualiza uno existente
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> actualizar(@PathVariable Long id, @Valid @RequestBody ProductoRequest request){
        return ResponseEntity.ok(productoService.actualizar(id, request));
    }
    // DELETE /api/productos/5 -> elimina uno
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        productoService.eliminar(id);
        // 204 No Content: se eliminó y no hay nada que devolver
        return ResponseEntity.noContent().build();
    }
}

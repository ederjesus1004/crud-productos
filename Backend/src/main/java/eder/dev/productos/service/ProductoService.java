package eder.dev.productos.service;

import eder.dev.productos.dto.ProductoRequest;
import eder.dev.productos.dto.ProductoResponse;

import java.util.List;

public interface ProductoService {
    List<ProductoResponse> listar();
    ProductoResponse obtenerPorId(Long id);
    ProductoResponse crear(ProductoRequest request);
    ProductoResponse actualizar(Long id, ProductoRequest request);
    void eliminar(Long id);
}

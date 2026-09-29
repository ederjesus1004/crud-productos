package eder.dev.productos.service;

import eder.dev.productos.dto.ProductoRequest;
import eder.dev.productos.dto.ProductoResponse;
import eder.dev.productos.exception.RecursoNoEncontradoException;
import eder.dev.productos.model.Producto;
import eder.dev.productos.repository.ProductoRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // Lombok crea un constructor con todos los atributos "final"
public class ProductoServiceImpl implements ProductoService{
    // Inyección por constructor (Lombok escribe el constructor por ti)
    private final ProductoRepository productoRepository;

    @Override
    @Transactional (readOnly = true)
    public List<ProductoResponse> listar(){
        return productoRepository.findAll()
                .stream()
                .map(ProductoResponse::desdeEntidad)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponse obtenerPorId(Long id){
        return ProductoResponse.desdeEntidad(buscarProducto(id));
    }

    @Override
    @Transactional
    public ProductoResponse crear(ProductoRequest request) {
        Producto producto = new Producto();
        copiarDatos(request, producto);
        Producto guardado = productoRepository.save(producto);
        return ProductoResponse.desdeEntidad(guardado);
    }

    @Override
    @Transactional
    public ProductoResponse actualizar(Long id, ProductoRequest request) {
        Producto producto = buscarProducto(id);
        copiarDatos(request, producto);
        Producto actualizado = productoRepository.save(producto);
        return ProductoResponse.desdeEntidad(actualizado);
    }

    @Override
    @Transactional
    public void eliminar (Long id){
        // Primero verificamos que exista para devolver un 404 claro
        Producto producto = buscarProducto(id);
        productoRepository.delete(producto);

    }
    // ===== Métodos privados de ayuda =====

    // Busca el producto o lanza una excepción que termina en 404

    private Producto buscarProducto(Long id){
        return productoRepository.findById(id)
                .orElseThrow(()-> new RecursoNoEncontradoException("No existe el producto con id " + id));

    }

    // Pasa los datos del DTO a la entidad (se usa al crear y al actualizar)
    private void copiarDatos(ProductoRequest request, Producto producto){
        producto.setNombre(request.nombre().trim());
        producto.setDescripcion(request.descripcion());
        producto.setPrecio(request.precio());
        producto.setStock(request.stock());

    }
}

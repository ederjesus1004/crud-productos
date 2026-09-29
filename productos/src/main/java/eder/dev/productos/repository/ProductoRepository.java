package eder.dev.productos.repository;

import eder.dev.productos.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// <Producto, Long> = <entidad, tipo de la llave primaria>
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

}

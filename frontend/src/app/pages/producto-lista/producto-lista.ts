import { CurrencyPipe } from '@angular/common';
import { Component, OnInit, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';

import { Producto } from '../../models/producto.model';
import { ProductoService } from '../../services/producto.service';

@Component({
  selector: 'app-producto-lista',
  imports: [RouterLink, CurrencyPipe],
  templateUrl: './producto-lista.html',
  styleUrl: './producto-lista.css',
})
export class ProductoLista implements OnInit {
  private readonly productoService = inject(ProductoService);

  // signal = variable reactiva. Cuando cambia, la pantalla se actualiza sola.
  productos = signal<Producto[]>([]);
  cargando = signal(false);
  error = signal<string | null>(null);

  // Se ejecuta una vez cuando el componente aparece en pantalla
  ngOnInit(): void {
    this.cargarProductos();
  }

  cargarProductos(): void {
    this.cargando.set(true);
    this.error.set(null);

    this.productoService.listar().subscribe({
      next: (datos) => {
        this.productos.set(datos);
        this.cargando.set(false);
      },
      error: () => {
        this.error.set('No se pudo conectar con el servidor. ¿Está prendido el backend?');
        this.cargando.set(false);
      },
    });
  }

  eliminar(producto: Producto): void {
    // Pedimos confirmación antes de borrar
    const confirmado = confirm(`¿Seguro que quieres eliminar "${producto.nombre}"?`);
    if (!confirmado) {
      return;
    }

    this.productoService.eliminar(producto.id).subscribe({
      // Quitamos el producto de la lista sin volver a llamar al backend
      next: () => this.productos.update((lista) => lista.filter((p) => p.id !== producto.id)),
      error: () => this.error.set('No se pudo eliminar el producto.'),
    });
  }
}
import { Component, inject, OnInit, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ProductoService } from '../../services/producto.service';
import { ErrorApi, ProductoRequest } from '../../models/producto.model';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  imports: [ReactiveFormsModule, RouterLink],
  selector: 'app-producto-form',
  styleUrl: './producto-form.css',
  templateUrl: './producto-form.html',
})
export class ProductoForm implements OnInit{
  private readonly fb = inject(FormBuilder);
  private readonly productoService = inject(ProductoService);
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
   // id del producto que editamos (null si es nuevo)
   idProducto = signal<number | null>(null);
   guardando = signal(false);
   error = signal<string | null>(null);
 
   // Formulario con las mismas reglas que el backend.
   // El front ayuda al usuario y el back protege los datos.
   form = this.fb.nonNullable.group({
     nombre: ['', [Validators.required, Validators.maxLength(100)]],
     descripcion: ['', [Validators.maxLength(255)]],
     precio: [0, [Validators.required, Validators.min(0.01)]],
     stock: [0, [Validators.required, Validators.min(0)]],
   });
 
   ngOnInit(): void {
     // Leemos el :id de la ruta /producto/editar/:id
     const id = this.route.snapshot.paramMap.get('id');
     if (id) {
       this.idProducto.set(Number(id));
       this.cargarProducto(Number(id));
     }
   }
    // Trae el producto del backend y llena el formulario
  private cargarProducto(id: number): void {
    this.productoService.ObtenerPorId(id).subscribe({
      next: (producto) => {
        this.form.patchValue({
          nombre: producto.nombre,
          descripcion: producto.descripcion ?? '',
          precio: producto.precio,
          stock: producto.stock,
        });
      },
      error: () => this.error.set('No se encontró el producto.'),
    });
  }

  guardar(): void {
    // Si hay errores, marcamos todos los campos para que se vean los mensajes
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.guardando.set(true);
    this.error.set(null);

    const valores = this.form.getRawValue();
    const datos: ProductoRequest = {
      nombre: valores.nombre,
      descripcion: valores.descripcion || null,
      precio: valores.precio,
      stock: valores.stock,
    };

    const id = this.idProducto();
    // Si hay id actualizamos, si no creamos
    const peticion = id
      ? this.productoService.actualizar(id, datos)
      : this.productoService.crear(datos);

    peticion.subscribe({
      next: () => this.router.navigate(['/producto']),
      error: (err: HttpErrorResponse) => {
        this.guardando.set(false);
        this.error.set(this.leerMensajeError(err));
      },
    });
  }

  // Revisa si un campo tiene un error y el usuario ya lo tocó
  tieneError(campo: 'nombre' | 'descripcion' | 'precio' | 'stock', tipo: string): boolean {
    const control = this.form.controls[campo];
    return control.touched && control.hasError(tipo);
  }

  // Convierte el error del backend en un texto que el usuario entienda
  private leerMensajeError(err: HttpErrorResponse): string {
    const errorApi = err.error as ErrorApi | null;
    if (errorApi?.errores && Object.keys(errorApi.errores).length > 0) {
      return Object.values(errorApi.errores).join('. ');
    }
    return errorApi?.mensaje ?? 'No se pudo guardar el producto.';
  }
}

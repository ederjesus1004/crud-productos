import { Routes } from '@angular/router';
import { ProductoLista } from './pages/producto-lista/producto-lista';
import { ProductoForm } from './pages/producto-form/producto-form';

export const routes: Routes = [
    // Si entras a http://localhost:4200 te manda a /producto
    {path: '', redirectTo: 'producto', pathMatch: 'full'},
    {path: 'producto', component: ProductoLista },
    {path: 'producto/nuevo', component: ProductoForm},
    {path: 'producto/editar/:id', component: ProductoForm},
    // Cualquier URL que no exista vuelve a la lista
    {path: '**', redirectTo: 'producto' },
];

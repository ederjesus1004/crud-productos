// Forma de un producto tal como lo devuelve el backend (ProductoResponse en Java)
export interface Producto{
    id:number,
    nombre: string,
    descripcion: string|null,
    precio: number,
    stock:number,
    fechaCreacion: string
}
// Datos que enviamos al backend para crear o editar (ProductoRequest en Java)

export interface ProductoRequest{
    nombre: string,
    descripcion: string|null,
    precio:number,
    stock:number
}

// Forma de los errores que devuelve el GlobalExceptionHandler del backend
export interface ErrorApi{
    fecha: string,
    estado: number,
    mensaje: string,
    errores: Record<string, string>;
}
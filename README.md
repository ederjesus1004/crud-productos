# CRUD de Productos · Angular + Spring Boot + PostgreSQL

Aplicación web para registrar, listar, editar y eliminar productos. La hice para practicar cómo se conecta un frontend en Angular con una API REST en Spring Boot que guarda los datos en PostgreSQL.

![Lista de productos](docs/lista.png)

## Tecnologías

| Capa | Tecnologías |
|------|-------------|
| Frontend | Angular, TypeScript, formularios reactivos, signals |
| Backend | Java 21, Spring Boot, Spring Data JPA, Bean Validation, Lombok |
| Base de datos | PostgreSQL 16 |
| Herramientas | Docker, Maven, Git |

## Funcionalidades

- Listar todos los productos en una tabla
- Crear un producto nuevo con validación de campos
- Editar un producto existente usando el mismo formulario
- Eliminar un producto con confirmación
- Mensajes de error claros tanto en el formulario como cuando falla el servidor

## Estructura del proyecto

```
crud-productos/
├── docker-compose.yml     # PostgreSQL en un contenedor
├── backend/               # API REST con Spring Boot
│   └── src/main/java/com/ejemplo/productos/
│       ├── model/         # entidad Producto
│       ├── dto/           # datos de entrada y salida
│       ├── repository/    # acceso a la base de datos
│       ├── service/       # lógica de negocio
│       ├── controller/    # endpoints REST
│       ├── exception/     # manejo global de errores
│       └── config/        # configuración de CORS
└── frontend/              # aplicación Angular
    └── src/app/
        ├── models/        # interfaces TypeScript
        ├── services/      # llamadas HTTP a la API
        └── pages/         # pantallas de lista y formulario
```

## Requisitos

- Java 21
- Node.js 20 o superior
- Angular CLI (`npm install -g @angular/cli`)
- Docker Desktop

## Cómo ejecutarlo

### 1. Clonar el repositorio

```bash
git clone https://github.com/TU_USUARIO/crud-productos.git
cd crud-productos
```

### 2. Levantar la base de datos

```bash
docker compose up -d
```

Esto crea una base `productos_db` en `localhost:5432` con usuario `postgres` y contraseña `postgres`.

### 3. Levantar el backend

```bash
cd backend
./mvnw spring-boot:run
```

En Windows usa `mvnw.cmd spring-boot:run`. La API queda en `http://localhost:8080`. La tabla `productos` se crea sola al arrancar.

### 4. Levantar el frontend

En otra terminal:

```bash
cd frontend
npm install
ng serve
```

Abre `http://localhost:4200` en el navegador.

## Endpoints de la API

| Método | Ruta | Descripción | Respuesta |
|--------|------|-------------|-----------|
| GET | `/api/productos` | Lista todos los productos | 200 |
| GET | `/api/productos/{id}` | Obtiene un producto | 200 / 404 |
| POST | `/api/productos` | Crea un producto | 201 / 400 |
| PUT | `/api/productos/{id}` | Actualiza un producto | 200 / 400 / 404 |
| DELETE | `/api/productos/{id}` | Elimina un producto | 204 / 404 |

Ejemplo de body para crear o actualizar:

```json
{
  "nombre": "Teclado mecánico",
  "descripcion": "Switches rojos",
  "precio": 159.90,
  "stock": 12
}
```

Ejemplo de respuesta cuando los datos no son válidos:

```json
{
  "fecha": "2026-09-29T16:30:00",
  "estado": 400,
  "mensaje": "Datos inválidos",
  "errores": {
    "nombre": "El nombre es obligatorio"
  }
}
```

## Variables de entorno

El backend tiene valores por defecto para trabajar en local. Si quieres cambiarlos, define estas variables:

| Variable | Valor por defecto |
|----------|-------------------|
| `DB_URL` | `jdbc:postgresql://localhost:5432/productos_db` |
| `DB_USER` | `postgres` |
| `DB_PASSWORD` | `postgres` |
| `FRONTEND_URL` | `http://localhost:4200` |

## Buenas prácticas que apliqué

**Backend**
- Separación en capas: controller, service y repository
- DTOs para no exponer la entidad directamente
- Validaciones con `@Valid` y mensajes en español
- Manejo global de errores con `@RestControllerAdvice`
- Códigos HTTP correctos (201 al crear, 204 al eliminar, 404 si no existe)
- Inyección de dependencias por constructor con Lombok
- `BigDecimal` para el precio
- Credenciales leídas desde variables de entorno
- CORS permitido solo para el frontend

**Frontend**
- Componentes standalone
- Estado de la pantalla con signals
- Un servicio único para hablar con la API
- Formularios reactivos con las mismas reglas que el backend
- URL de la API en `environments`
- Un solo formulario para crear y editar

## Capturas

| Lista | Formulario |
|-------|------------|
| ![Lista](docs/lista.png) | ![Formulario](docs/formulario.png) |

## Lo que aprendí

- Cómo se comunican Angular y Spring Boot a través de una API REST
- Por qué hace falta configurar CORS cuando el frontend y el backend usan puertos distintos
- Cómo usar Docker para no instalar PostgreSQL en mi computadora
- La diferencia entre validar en el frontend y validar en el backend

## Autor

**Eder Cuaresma**
Estudiante de Ingeniería de Sistemas e Informática, UTP

- GitHub: [@ederjesus1004](https://github.com/ederjesus1004)

😊😊😊😊😊

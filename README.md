# CRUD MVC con Thymeleaf — RA3

## 1) Datos del alumno/a

- Entidad elegida (ej. Producto, Libro...): Película

## 2) Repositorio (fork) y gestión de versiones
- Repositorio base: https://github.com/profeInformatica101/tareasSpringBoot
- Enlace a MI fork: https://github.com/clarafp8/tareasSpringBoot
- Nº de commits realizados: (mínimo 5)

## 3) Arquitectura
Explica brevemente cómo has organizado:
- Controller: peliculaController
- Service: peliculaService
- Repository: peliculaRepository
- Entity: peliculaEntity

## 4) Base de datos elegida (marca una)
- [X] H2
- [ ] MySQL
- [ ] PostgreSQL

## 5) Configuración de la base de datos
### 5.1 Dependencias añadidas
(Indica la dependencia del driver que has usado)
org.h2.Driver
### 5.2 application.properties / application.yml
(Pega aquí tu configuración SIN contraseñas reales si es necesario)
spring.h2.console.enabled=true
spring.h2.console.path=/h2
spring.datasource.url=jdbc:h2:mem:testdb 
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.driver-class-name=org.h2.Driver
server.port=8090
### 5.3 Pasos para crear la BD (si aplica)
- MySQL: CREATE DATABASE ...
- PostgreSQL: createdb ...

## 6) Cómo ejecutar el proyecto
1. Requisitos (Java versión, Maven/Gradle, DB instalada si aplica)
2. Comando de arranque:
   - ./mvnw spring-boot:run   (o equivalente)
3. URL de acceso:
   - http://localhost:8080/...

## 7) Pantallas / Rutas MVC
- GET /entidad (listar)
- GET /entidad/nuevo (formulario alta)
- POST /entidad (crear)
- GET /entidad/{id}/editar (editar)
- POST /entidad/{id} (actualizar)
- POST /entidad/{id}/borrar (eliminar)


## 8) Mejoras extra (opcional)
- Validaciones
- Estilos Bootstrap
- Búsqueda
- Pruebas
- Paginación

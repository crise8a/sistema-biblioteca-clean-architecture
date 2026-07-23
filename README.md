# library-clean-architecture

Servicio BE en Java + Spring Boot que gestiona **Autores** y **Libros**, construido con Arquitectura Limpia
(hexagonal / ports & adapters) siguiendo los principios de Robert C. Martin.

Relación de dominio: **un Autor tiene varios Libros** (uno-a-muchos). Un Libro pertenece a un único autor
(no existe relación muchos-a-muchos).

## Arquitectura

```
domain/                     -> entidades y reglas de negocio puras (sin frameworks)
  model/                    -> Author, Book
  service/                  -> BookService (filtrado por año)

application/                -> casos de uso, independientes de infraestructura
  ports/input/              -> IAuthorUseCase, IBookUseCase (lo que expone el core)
  ports/output/              -> AuthorPort, BookPort (lo que el core necesita del exterior)
  usecases/                 -> AuthorUseCaseImpl, BookUseCaseImpl (orquestación + validaciones)

infrastructure/
  adapters/input/           -> AuthorController, BookController (REST), DTOs y mappers
  adapters/output/          -> entidades JPA, repositorios Spring Data y adapters que implementan los ports
```

La dependencia siempre apunta hacia adentro: `infrastructure` conoce `application`, `application` conoce
`domain`, pero `domain` no conoce nada de Spring, JPA ni HTTP.

## Endpoints (prefijo `/v1`)

| Método | Ruta                                   | Descripción                                   |
|--------|-----------------------------------------|------------------------------------------------|
| POST   | `/v1/authors`                          | Crea un autor                                   |
| GET    | `/v1/authors`                          | Lista todos los autores                         |
| POST   | `/v1/books`                            | Crea un libro (valida que el autor exista)      |
| GET    | `/v1/books?authorId={id}&year={year}`  | Filtra los libros de un autor en un año dado (`@RequestParam`) |

Ejemplo:

```bash
curl -X POST localhost:8080/v1/authors -H "Content-Type: application/json" \
  -d '{"firstName":"Gabriel","lastName":"García Márquez","nationality":"Colombiana"}'

curl -X POST localhost:8080/v1/books -H "Content-Type: application/json" \
  -d '{"title":"Cien años de soledad","publicationYear":1967,"authorId":"<id-del-autor>"}'

curl "localhost:8080/v1/books?authorId=<id-del-autor>&year=1967"
```

Errores de validación (campos requeridos, autor inexistente, parámetros faltantes) responden `400`.

## Cómo correr el proyecto

```bash
./gradlew bootRun
```

- API disponible en `http://localhost:8080`
- Consola H2 en `http://localhost:8080/h2-console` (JDBC URL `jdbc:h2:mem:librarydb`, usuario `sa`, sin contraseña)
- Base de datos en memoria: se reinicia en cada arranque

## Tests

```bash
./gradlew test
```

Incluye tests de dominio (`BookServiceTest`), de casos de uso con fakes de los ports
(`AuthorUseCaseImplTest`, `BookUseCaseImplTest`) y de los controllers con `@WebMvcTest`
(`AuthorControllerTest`, `BookControllerTest`).

## Notas de diseño

- **Toolchain de Java 26**: se usó la JDK realmente instalada en la máquina de desarrollo. El proyecto de
  IntelliJ (`.idea`) quedó apuntando a `temurin-25`; si esa JDK no está instalada localmente, cambia el
  Project SDK en IntelliJ o ajusta `languageVersion` en `build.gradle.kts` a la JDK que tengas disponible.
- El `BookEntity` guarda `authorId` como columna simple (sin relación JPA `@ManyToOne`) para que el dominio
  y los adapters de salida no dependan de detalles de mapeo objeto-relacional; el core solo conoce el id
  del autor, no el grafo de objetos.
- `GET /v1/authors` no estaba en el enunciado, pero se agregó como endpoint mínimo de conveniencia para
  poder obtener el `id` de un autor y probar el resto del flujo manualmente.

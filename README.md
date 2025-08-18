# 📚 Foro Hub API REST

Este proyecto es una **API REST** construida con **Spring Boot**, que
gestiona tópicos de un foro.\
La aplicación implementa **autenticación** con Spring Security:\
- Los **endpoints de lectura** (`GET`) son públicos.\
- Los **endpoints de escritura** (`POST`, `PUT`, `DELETE`) requieren
autenticación.

------------------------------------------------------------------------

## 🚀 Tecnologías utilizadas

-   Java 21
-   Spring Boot 3
-   Spring Security
-   JPA / Hibernate
-   Base de datos relacional MySQL
-   Maven

------------------------------------------------------------------------

## ⚙️ Configuración del proyecto

1.  Clonar el repositorio:

    ``` bash
    git clone https://github.com/juandresh/Challenge-Api-Rest-ForoHub.git
    ```

2.  Configurar en `application.properties`:

    ``` properties
    spring.datasource.url=jdbc:mysql://localhost:3306/foro_api
    spring.datasource.username=tu_usuario
    spring.datasource.password=tu_password
    spring.jpa.hibernate.ddl-auto=update
    server.port=8080
    ```

3.  Ejecutar la aplicación:

    ``` bash
    mvn spring-boot:run
    ```

    La API estará disponible en:\
    👉 `http://localhost:8080/topicos`

------------------------------------------------------------------------

## 🔐 Autenticación

-   El sistema utiliza **Spring Security**.
-   Para crear, editar o eliminar tópicos debes **estar autenticado**
    (con un usuario válido).
-   Los endpoints de login y creación de usuario deben estar definidos
    en tu proyecto (por ejemplo: `/login`).

------------------------------------------------------------------------

## 📌 Endpoints

### 🔹 1. Listar todos los tópicos

``` http
GET /topicos
```

-   **Descripción**: Devuelve una lista paginada de todos los tópicos.\
-   **Parámetros opcionales**:
    -   `page` → número de página (default: 0)\
    -   `size` → tamaño de la página (default: 10)

✅ **Respuesta exitosa** `200 OK`

``` json
{
  "content": [
    { 
      "idAutor": 3,
      "id": 1,
      "titulo": "Error en Spring",
      "curso": "Java Backend",
      "mensaje": "Tengo un error en mi proyecto...",
      "fechaCreacion": "2025-08-15T12:45:23"
    }
  ],
  "pageable": { ... }
}
```

------------------------------------------------------------------------

### 🔹 2. Consultar un tópico por ID

``` http
GET /topicos/{id}
```

-   **Descripción**: Devuelve la información de un tópico específico.\
-   **Parámetro**: `id` (Long) → ID del tópico.

✅ **Respuesta exitosa** `200 OK`\
❌ **Error** → `404 Not Found` si no existe.

------------------------------------------------------------------------

### 🔹 3. Crear un nuevo tópico

``` http
POST /topicos
```

-   🔒 **Requiere autenticación**.\
-   **Body (JSON)**:

``` json
{
  "titulo": "Nuevo error en JPA",
  "curso": "Spring Boot",
  "mensaje": "¿Cómo configuro correctamente las entidades?"
}
```

✅ **Respuesta exitosa** `201 Created`

``` json
{
  "idAutor": 2,
  "id": 10,
  "titulo": "Nuevo error en JPA",
  "curso": "Spring Boot",
  "mensaje": "¿Cómo configuro correctamente las entidades?",
  "fechaCreacion": "2025-08-16T22:30:00"
}
```

❌ **Error** → `409 Conflict` si ya existe un tópico con el mismo título
y mensaje.

------------------------------------------------------------------------

### 🔹 4. Editar un tópico

``` http
PUT /topicos/{id}
```

-   🔒 **Requiere autenticación**.\
-   **Body (JSON)**:

``` json
{
  "titulo": "Título actualizado",
  "curso": "Java Avanzado",
  "mensaje": "He corregido el error, pero tengo otra duda..."
}
```

✅ **Respuesta exitosa** `200 OK`\
❌ **Error** → `404 Not Found` si el tópico no existe.

------------------------------------------------------------------------

### 🔹 5. Eliminar un tópico

``` http
DELETE /topicos/{id}
```

-   🔒 **Requiere autenticación**.\
-   **Parámetro**: `id` (Long).

✅ **Respuesta exitosa** `204 No Content`\
❌ **Error** → `404 Not Found` si el tópico no existe.

------------------------------------------------------------------------

## 📖 Notas

-   Los DTOs (`DatosRegistroTopico`, `DatosListarTopico`,
    `DatosActualizarTopico`) garantizan que la API solo exponga los
    datos necesarios.\
-   Se recomienda usar **Postman** o **Insomnia** para probar los
    endpoints.\

------------------------------------------------------------------------


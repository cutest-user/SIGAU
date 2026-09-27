# API REST — SIGAU

Esta documentación define de manera preliminar los endpoints REST previstos para la etapa de implementación de SIGAU.

Los endpoints se encuentran organizados según los módulos funcionales definidos en `docs/modulos.md`. En esta etapa constituyen una especificación inicial y podrán ajustarse durante la implementación.

## Autenticación

| Método | Endpoint          | Descripción                                   |
| ------ | ----------------- | --------------------------------------------- |
| POST   | `/api/auth/login` | Autenticar un usuario y obtener un token JWT. |

## Usuarios

| Método | Endpoint                    | Descripción                      |
| ------ | --------------------------- | -------------------------------- |
| GET    | `/api/usuarios`             | Consultar usuarios.              |
| GET    | `/api/usuarios/{id}`        | Consultar un usuario específico. |
| POST   | `/api/usuarios`             | Registrar un usuario.            |
| PUT    | `/api/usuarios/{id}`        | Actualizar un usuario.           |
| PATCH  | `/api/usuarios/{id}/estado` | Activar o desactivar un usuario. |

## Ejemplares

| Método | Endpoint                         | Descripción                            |
| ------ | -------------------------------- | -------------------------------------- |
| GET    | `/api/ejemplares`                | Consultar ejemplares.                  |
| GET    | `/api/ejemplares/{id}`           | Consultar el detalle de un ejemplar.   |
| POST   | `/api/ejemplares`                | Registrar un ejemplar.                 |
| PUT    | `/api/ejemplares/{id}`           | Actualizar información de un ejemplar. |
| GET    | `/api/ejemplares/{id}/historial` | Consultar el historial de un ejemplar. |

## Demandas

| Método | Endpoint                        | Descripción                          |
| ------ | ------------------------------- | ------------------------------------ |
| GET    | `/api/demandas`                 | Consultar demandas.                  |
| GET    | `/api/demandas/{id}`            | Consultar el detalle de una demanda. |
| POST   | `/api/demandas`                 | Registrar una demanda.               |
| PUT    | `/api/demandas/{id}`            | Actualizar una demanda.              |
| POST   | `/api/demandas/{id}/ejemplares` | Asociar ejemplares a una demanda.    |

## Inspecciones

| Método | Endpoint                            | Descripción                             |
| ------ | ----------------------------------- | --------------------------------------- |
| GET    | `/api/inspecciones`                 | Consultar inspecciones.                 |
| GET    | `/api/inspecciones/{id}`            | Consultar el detalle de una inspección. |
| POST   | `/api/inspecciones`                 | Registrar una inspección.               |
| POST   | `/api/inspecciones/{id}/evidencias` | Registrar una evidencia de inspección.  |

## Intervenciones

| Método | Endpoint                              | Descripción                               |
| ------ | ------------------------------------- | ----------------------------------------- |
| GET    | `/api/intervenciones`                 | Consultar intervenciones.                 |
| GET    | `/api/intervenciones/{id}`            | Consultar el detalle de una intervención. |
| POST   | `/api/intervenciones`                 | Registrar una intervención.               |
| PUT    | `/api/intervenciones/{id}`            | Actualizar una intervención.              |
| POST   | `/api/intervenciones/{id}/evidencias` | Registrar una evidencia de intervención.  |

## Verificaciones

| Método | Endpoint                   | Descripción                 |
| ------ | -------------------------- | --------------------------- |
| GET    | `/api/verificaciones`      | Consultar verificaciones.   |
| GET    | `/api/verificaciones/{id}` | Consultar una verificación. |
| POST   | `/api/verificaciones`      | Registrar una verificación. |

## Certificaciones

| Método | Endpoint                    | Descripción                  |
| ------ | --------------------------- | ---------------------------- |
| GET    | `/api/certificaciones`      | Consultar certificaciones.   |
| GET    | `/api/certificaciones/{id}` | Consultar una certificación. |
| POST   | `/api/certificaciones`      | Registrar una certificación. |

## Derivaciones

| Método | Endpoint                 | Descripción                                        |
| ------ | ------------------------ | -------------------------------------------------- |
| GET    | `/api/derivaciones`      | Consultar derivaciones.                            |
| GET    | `/api/derivaciones/{id}` | Consultar una derivación.                          |
| POST   | `/api/derivaciones`      | Registrar una derivación hacia un sistema externo. |
| PUT    | `/api/derivaciones/{id}` | Actualizar el estado de una derivación.            |

## Seguimiento y reportes

| Método | Endpoint         | Descripción                                        |
| ------ | ---------------- | -------------------------------------------------- |
| GET    | `/api/historial` | Consultar registros de trazabilidad.               |
| GET    | `/api/reportes`  | Consultar información para reportes e indicadores. |

## Convenciones generales

* La API utilizará el prefijo `/api`.
* Las operaciones utilizarán métodos HTTP acordes a su propósito: `GET`, `POST`, `PUT` y `PATCH`.
* Los recursos se identificarán mediante sus respectivos `id`.
* Los endpoints protegidos requerirán autenticación mediante JWT.
* La autorización se determinará según el rol del usuario.
* Las respuestas utilizarán formato JSON.

## Estado de la documentación

Los endpoints definidos en este documento son **previstos** y corresponden a la etapa de diseño. La definición final de rutas, parámetros, cuerpos de solicitud, respuestas y códigos HTTP podrá ajustarse durante la implementación del backend.

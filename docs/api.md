# API REST — SIGAU

Los endpoints están organizados según los módulos definidos en `docs/modulos.md`. Todas las rutas, salvo el login, requieren el encabezado `Authorization: Bearer <token>`.

Cuando una operación falla, la API responde con un cuerpo `{ "error": "...", "mensaje": "..." }`. Los errores de validación agregan una lista `detalles` con un mensaje por campo.

| Código | Cuándo se devuelve                                                        |
| ------ | ------------------------------------------------------------------------- |
| 400    | Datos inválidos o regla de negocio incumplida.                            |
| 401    | Falta el token, venció o las credenciales son incorrectas.                |
| 403    | El rol del usuario no tiene permiso para la operación.                    |
| 404    | El recurso no existe.                                                     |
| 409    | Conflicto con un dato existente (duplicado o restricción de integridad).  |

## Permisos por rol

| Rol            | Puede                                                                                                                         |
| -------------- | ----------------------------------------------------------------------------------------------------------------------------- |
| INSPECTOR      | Registrar inspecciones y evidencias, crear y cambiar de estado intervenciones, registrar verificaciones y certificaciones.    |
| ADMINISTRATIVO | Gestionar usuarios.                                                                                                           |
| Ambos          | Consultar todo, registrar y actualizar demandas, ubicaciones, ejemplares y derivaciones.                                      |

El usuario que figura en cada registro (inspector, responsable de una verificación, certificación o derivación, quien ingresa una demanda) se toma del token y no del cuerpo de la petición.

## Autenticación

| Método | Endpoint          | Descripción                                   |
| ------ | ----------------- | --------------------------------------------- |
| POST   | `/api/auth/login` | Autenticar un usuario y obtener un token JWT. |

## Usuarios (ADMINISTRATIVO)

| Método | Endpoint             | Descripción                                                              |
| ------ | -------------------- | ------------------------------------------------------------------------ |
| GET    | `/api/usuarios`      | Consultar usuarios.                                                      |
| GET    | `/api/usuarios/{id}` | Consultar un usuario específico.                                         |
| POST   | `/api/usuarios`      | Registrar un usuario (contraseña de 8 a 72 caracteres, rol 1 o 2).        |
| PUT    | `/api/usuarios/{id}` | Actualizar un usuario. El campo `activo` lo activa o desactiva.          |

## Ubicaciones

| Método | Endpoint               | Descripción                                                                                              |
| ------ | ---------------------- | -------------------------------------------------------------------------------------------------------- |
| GET    | `/api/ubicaciones`     | Consultar ubicaciones.                                                                                   |
| GET    | `/api/ubicaciones/{id}`| Consultar una ubicación.                                                                                 |
| POST   | `/api/ubicaciones`     | Registrar una ubicación. Se limpian los espacios y, si ya existe la misma calle, altura y barrio, se devuelve la existente. |

## Ejemplares

| Método | Endpoint                             | Descripción                                                    |
| ------ | ------------------------------------ | -------------------------------------------------------------- |
| GET    | `/api/ejemplares`                    | Consultar ejemplares.                                          |
| GET    | `/api/ejemplares?ubicacionId={id}`   | Consultar los ejemplares de una ubicación (control de duplicados). |
| GET    | `/api/ejemplares/{id}`               | Consultar el detalle de un ejemplar.                           |
| POST   | `/api/ejemplares`                    | Registrar un ejemplar. Siempre se crea en estado `ACTIVO`.     |

## Demandas

| Método | Endpoint                                      | Descripción                                                                                      |
| ------ | --------------------------------------------- | ------------------------------------------------------------------------------------------------ |
| GET    | `/api/demandas`                               | Consultar demandas.                                                                              |
| GET    | `/api/demandas/{id}`                          | Consultar el detalle de una demanda.                                                             |
| GET    | `/api/demandas/posibles-duplicadas?ubicacionId={id}` | Demandas abiertas (ingresadas o en gestión) en la misma ubicación.                        |
| POST   | `/api/demandas`                               | Registrar una demanda. Queda `INGRESADA`.                                                        |
| PUT    | `/api/demandas/{id}`                          | Actualizar una demanda abierta.                                                                  |
| PATCH  | `/api/demandas/{id}/cancelacion`              | Cancelar una demanda. Exige `observacion` y que no tenga intervenciones activas.                 |
| GET    | `/api/origenes-demanda`                       | Consultar los orígenes de demanda.                                                               |
| POST   | `/api/demandas-ejemplares`                    | Asociar un ejemplar a una demanda abierta.                                                       |

Estados de una demanda: `INGRESADA`, `EN_GESTION` (al registrarse la primera intervención), `CERTIFICADA` y `CANCELADA`.

## Inspecciones

| Método | Endpoint                                      | Descripción                                   |
| ------ | --------------------------------------------- | --------------------------------------------- |
| GET    | `/api/inspecciones`                           | Consultar inspecciones.                       |
| GET    | `/api/inspecciones/{id}`                      | Consultar una inspección.                     |
| POST   | `/api/inspecciones`                           | Registrar una inspección (INSPECTOR).         |
| GET    | `/api/evidencias-inspeccion/inspeccion/{id}`  | Consultar las evidencias de una inspección.   |
| POST   | `/api/evidencias-inspeccion`                  | Registrar una evidencia (INSPECTOR).          |

## Intervenciones

| Método | Endpoint                             | Descripción                                                                          |
| ------ | ------------------------------------ | ------------------------------------------------------------------------------------ |
| GET    | `/api/intervenciones`                | Consultar intervenciones. Admite `?demandaId={id}`.                                  |
| GET    | `/api/intervenciones/{id}`           | Consultar una intervención.                                                          |
| POST   | `/api/intervenciones`                | Registrar una intervención (INSPECTOR). Queda `PENDIENTE`.                           |
| PATCH  | `/api/intervenciones/{id}/estado`    | Cambiar el estado (INSPECTOR).                                                       |
| GET    | `/api/tipos-intervencion`            | Consultar los tipos de intervención.                                                 |

Transiciones permitidas: `PENDIENTE` → `PLANIFICADA` o `CANCELADA`; `PLANIFICADA` → `EN_EJECUCION`, `REALIZADA` o `CANCELADA`; `EN_EJECUCION` → `REALIZADA` o `CANCELADA`.

Reglas:

* El ejemplar debe estar asociado a la demanda.
* Una intervención previa debe ser de la misma demanda y del mismo ejemplar. No se puede iniciar ni realizar una intervención hasta que su previa esté realizada.
* Al pasar a `REALIZADA` una `EXTRACCION`, el ejemplar pasa a `EXTRAIDO`.
* Cada cambio queda registrado en el historial.

## Verificaciones, certificaciones y derivaciones

| Método | Endpoint                          | Descripción                                                                                      |
| ------ | --------------------------------- | ------------------------------------------------------------------------------------------------ |
| GET    | `/api/verificaciones`             | Consultar verificaciones.                                                                        |
| GET    | `/api/verificaciones/{id}`        | Consultar una verificación.                                                                      |
| POST   | `/api/verificaciones`             | Registrar una verificación (INSPECTOR) sobre una intervención realizada. Resultado `APROBADA` u `OBSERVADA`. |
| GET    | `/api/certificaciones`            | Consultar certificaciones.                                                                       |
| GET    | `/api/certificaciones/{id}`       | Consultar una certificación por número de orden.                                                 |
| POST   | `/api/certificaciones`            | Certificar una demanda (INSPECTOR). Ver reglas abajo.                                            |
| GET    | `/api/derivaciones`               | Consultar derivaciones.                                                                          |
| GET    | `/api/derivaciones/{id}`          | Consultar una derivación.                                                                        |
| POST   | `/api/derivaciones`               | Registrar una derivación. Sistema de destino: `147`, `SAP` o `ARBOPEDIA`.                        |
| PATCH  | `/api/derivaciones/{id}/estado`   | Actualizar el estado y la referencia externa de una derivación.                                  |

Para certificar, la demanda debe estar `EN_GESTION`, todas sus intervenciones no canceladas deben estar realizadas y cada una debe tener una verificación aprobada. Al certificar, la demanda pasa a `CERTIFICADA` y se completa su fecha de cierre.

## Seguimiento, historial y reportes

| Método | Endpoint                                      | Descripción                                                                         |
| ------ | --------------------------------------------- | ----------------------------------------------------------------------------------- |
| GET    | `/api/seguimiento/demanda/{id}`               | Demanda con sus ejemplares, intervenciones, verificaciones, certificación y cambios. |
| GET    | `/api/seguimiento/ejemplar/{id}/demandas`     | Demandas asociadas a un ejemplar.                                                   |
| GET    | `/api/seguimiento/ejemplar/{id}/historial`    | Historial completo de un ejemplar, con los cambios registrados.                     |
| GET    | `/api/seguimiento/intervenciones/pendientes`  | Intervenciones pendientes, planificadas o en ejecución.                             |
| GET    | `/api/seguimiento/demandas/estados`           | Demandas con su estado actual.                                                      |
| GET    | `/api/seguimiento/ejemplares/estados`         | Ejemplares con su estado actual.                                                    |
| GET    | `/api/historial?entidad={tipo}&id={id}`       | Cambios de estado de una `DEMANDA`, `EJEMPLAR` o `INTERVENCION`.                    |
| GET    | `/api/reportes`                               | Totales y distribución de demandas, intervenciones, inspecciones y ejemplares.      |

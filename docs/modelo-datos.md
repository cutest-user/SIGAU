# Modelo de datos — SIGAU

SIGAU utiliza un modelo de datos relacional implementado en MySQL. El modelo permite gestionar usuarios, ubicaciones, ejemplares arbóreos, demandas, inspecciones, intervenciones y su trazabilidad.

## Diagrama Entidad-Relación

El modelo conceptual se representa en `DER_SIGAU.png` e incluye las entidades principales, sus relaciones y cardinalidades.

El esquema relacional detallado se representa en `ESQUEMA_RELACIONAL_SIGAU.png`.

## Entidades y relaciones principales

Las principales entidades del modelo son:

* `rol` y `usuario`: gestión de usuarios y roles.
* `ubicacion` y `ejemplar`: asociación de ejemplares con su ubicación.
* `origen_demanda` y `demanda`: registro y clasificación de demandas.
* `demanda_ejemplar`: relación N:M entre demandas y ejemplares.
* `inspeccion` e `inspeccion_demanda`: registro de inspecciones y su relación con demandas.
* `evidencia_inspeccion`: evidencias asociadas a inspecciones.
* `tipo_intervencion` e `intervencion`: clasificación y gestión de intervenciones.
* `evidencia_intervencion`: evidencias asociadas a intervenciones.
* `verificacion`: controles realizados sobre las intervenciones.
* `certificacion`: certificación asociada a una demanda.
* `derivacion`: derivaciones hacia sistemas externos.
* `historial`: trazabilidad de estados y acciones del sistema.

Las relaciones N:M se resuelven mediante las tablas intermedias `demanda_ejemplar` e `inspeccion_demanda`.

La tabla `intervencion` incorpora una relación recursiva mediante `intervencion_previa_id` para representar la secuencia de intervenciones.

Una `demanda` puede tener cero o una `certificacion`. Esta condición se implementa mediante `UNIQUE` sobre `certificacion.demanda_id`.

## Diccionario de datos

| Tabla                    | Propósito                                 | Clave principal               | Relaciones principales                                                        |
| ------------------------ | ----------------------------------------- | ----------------------------- | ----------------------------------------------------------------------------- |
| `rol`                    | Roles del sistema.                        | `id`                          | —                                                                             |
| `usuario`                | Usuarios, credenciales y roles.           | `id`                          | `rol_id` → `rol`                                                              |
| `ubicacion`              | Direcciones y ubicación administrativa.   | `id`                          | —                                                                             |
| `ejemplar`               | Registro de ejemplares arbóreos.          | `id`                          | `ubicacion_id` → `ubicacion`                                                  |
| `origen_demanda`         | Orígenes posibles de una demanda.         | `id`                          | —                                                                             |
| `demanda`                | Solicitudes y demandas ingresadas.        | `id`                          | `origen_id`, `usuario_id`, `ubicacion_id`                                     |
| `demanda_ejemplar`       | Asociación entre demandas y ejemplares.   | `(demanda_id, ejemplar_id)`   | `demanda_id`, `ejemplar_id`                                                   |
| `inspeccion`             | Inspecciones realizadas sobre ejemplares. | `id`                          | `ejemplar_id`, `inspector_id`                                                 |
| `inspeccion_demanda`     | Asociación entre inspecciones y demandas. | `(inspeccion_id, demanda_id)` | `inspeccion_id`, `demanda_id`                                                 |
| `evidencia_inspeccion`   | Evidencias de inspecciones.               | `id`                          | `inspeccion_id`                                                               |
| `tipo_intervencion`      | Tipos de intervención disponibles.        | `id`                          | —                                                                             |
| `intervencion`           | Intervenciones sobre ejemplares.          | `id`                          | `demanda_id`, `ejemplar_id`, `tipo_intervencion_id`, `intervencion_previa_id` |
| `evidencia_intervencion` | Evidencias de intervenciones.             | `id`                          | `intervencion_id`                                                             |
| `verificacion`           | Verificaciones de intervenciones.         | `id`                          | `intervencion_id`, `usuario_id`                                               |
| `certificacion`          | Certificaciones de demandas.              | `id`                          | `demanda_id`, `usuario_id`                                                    |
| `derivacion`             | Derivaciones a sistemas externos.         | `id`                          | `demanda_id`, `usuario_id`                                                    |
| `historial`              | Registro de trazabilidad.                 | `id`                          | `intervencion_id`, `usuario_id`                                               |

Los tipos de datos y la definición completa de cada campo se encuentran en `database/schema.sql`.

## Normalización

El modelo separa entidades y responsabilidades para evitar la duplicación de información y facilitar su mantenimiento.

Las relaciones de muchos a muchos se implementan mediante tablas intermedias. Los datos de ubicación, origen y tipo de intervención se mantienen en entidades independientes y son referenciados mediante claves foráneas.

La información de evidencias también se separa de las entidades de inspección e intervención, permitiendo registrar múltiples evidencias para cada una.

## Claves e índices principales

Las tablas utilizan claves primarias para identificar de forma única sus registros.

Se utilizan claves foráneas para mantener la integridad referencial entre las entidades relacionadas.

También se definieron restricciones `UNIQUE` en:

* `rol.nombre`
* `usuario.email`
* `usuario.apellido`
* `origen_demanda.nombre`
* `tipo_intervencion.nombre`
* `certificacion.demanda_id`

Las claves primarias y restricciones `UNIQUE` generan los índices correspondientes en MySQL. No se definieron índices adicionales específicos en esta etapa.

La definición completa de claves, restricciones y tipos de datos se encuentra en `database/schema.sql`.

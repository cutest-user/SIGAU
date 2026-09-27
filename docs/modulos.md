# Módulos — SIGAU

## Módulos Backend

| Módulo        | Responsabilidad                                                                            | Requerimientos asociados | Prioridad |
| ------------- | ------------------------------------------------------------------------------------------ | ------------------------ | --------- |
| CONFIG        | Seguridad, JWT, CORS y configuración general del sistema.                                  | RNF02, RNF05             | Alta      |
| USUARIO       | Gestión de usuarios, roles y permisos.                                                     | RF11                     | Alta      |
| EJEMPLAR      | Registro y consulta de ejemplares arbóreos.                                                | RF03, RF09               | Alta      |
| DEMANDA       | Registro, clasificación, actualización y gestión de demandas y su relación con ejemplares. | RF01, RF02               | Alta      |
| INSPECCION    | Registro de inspecciones, observaciones, evidencias y diagnóstico.                         | RF04, RF05               | Alta      |
| INTERVENCION  | Gestión de intervenciones, estados y secuencia de tareas.                                  | RF06, RF07               | Alta      |
| VERIFICACION  | Registro y consulta de verificaciones realizadas sobre las intervenciones.                 | RF08                     | Media     |
| CERTIFICACION | Gestión de certificaciones asociadas a las órdenes de trabajo SAP y cierre de demandas.    | RF08                     | Media     |
| SEGUIMIENTO   | Consulta de historial, trazabilidad y estados de demandas, ejemplares e intervenciones.    | RF09, RF10               | Media     |
| DERIVACION    | Registro y seguimiento de las derivaciones hacia 147, SAP y Arbopedia.                     | —                        | Media     |
| REPORTE       | Generación de indicadores y reportes para planificación y consulta.                        | RF12                     | Media     |
| COMMON        | Manejo de excepciones, utilidades y respuestas comunes de la API.                          | RNF03, RNF06             | Alta      |

## Módulos Frontend

| Módulo          | Responsabilidad                                                                | Prioridad |
| --------------- | ------------------------------------------------------------------------------ | --------- |
| AUTH            | Login, manejo de sesión y protección de rutas según rol.                       | Alta      |
| DASHBOARD       | Panel general de demandas, intervenciones y tareas pendientes.                 | Media     |
| DEMANDAS        | Alta, listado, detalle, actualización y relación con ejemplares.               | Alta      |
| EJEMPLARES      | Alta, consulta e historial de ejemplares.                                      | Alta      |
| INSPECCIONES    | Registro de inspecciones, evidencias, observaciones y diagnóstico.             | Alta      |
| INTERVENCIONES  | Planificación, secuencias, estados y evidencias.                               | Alta      |
| VERIFICACIONES  | Registro y consulta de verificaciones.                                         | Media     |
| CERTIFICACIONES | Consulta y gestión de certificaciones y órdenes de trabajo asociadas.          | Media     |
| SEGUIMIENTO     | Consulta de trazabilidad e historial de demandas, ejemplares e intervenciones. | Media     |
| REPORTES        | Visualización de indicadores y reportes.                                       | Media     |
| ADMIN           | Gestión de usuarios y roles.                                                   | Alta      |

## Relación entre requerimientos funcionales y módulos

| Requerimiento                                     | Módulo responsable           |
| ------------------------------------------------- | ---------------------------- |
| RF01 – Registrar y actualizar demandas            | DEMANDA                      |
| RF02 – Asociar demandas con ejemplares            | DEMANDA                      |
| RF03 – Consultar demandas por ejemplar            | SEGUIMIENTO                  |
| RF04 – Registrar inspecciones                     | INSPECCION                   |
| RF05 – Registrar fotografías y observaciones      | INSPECCION                   |
| RF06 – Registrar intervenciones                   | INTERVENCION                 |
| RF07 – Establecer secuencia de intervenciones     | INTERVENCION                 |
| RF08 – Registrar verificaciones y certificaciones | VERIFICACION / CERTIFICACION |
| RF09 – Consultar historial de un ejemplar         | SEGUIMIENTO                  |
| RF10 – Consultar estados y tareas pendientes      | SEGUIMIENTO / REPORTE        |
| RF11 – Gestionar usuarios y permisos              | USUARIO                      |
| RF12 – Generar información e indicadores          | REPORTE                      |

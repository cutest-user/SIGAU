# Arquitectura — SIGAU

SIGAU adopta una **arquitectura modular en capas**, con separación entre frontend y backend y comunicación mediante una API REST.

## Capas

* **Presentación:** frontend desarrollado con React.
* **Aplicación:** backend encargado de coordinar los casos de uso y exponer la API REST.
* **Dominio:** entidades y reglas de negocio.
* **Infraestructura:** persistencia en MySQL y comunicación con sistemas externos.

La representación gráfica se encuentra en `ARQUITECTURA_SIGAU.png`.

## Stack tecnológico

| Componente           | Tecnologías                                                                |
| -------------------- | -------------------------------------------------------------------------- |
| Backend              | Java 21, Spring Boot, Spring Web / MVC, Spring Data JPA / Hibernate, Maven |
| Frontend             | React, HTML5, CSS3, JavaScript, Bootstrap                                  |
| Base de datos        | MySQL                                                                      |
| Seguridad            | Spring Security, JWT                                                       |
| Control de versiones | Git, GitHub, GitHub Projects                                               |
| Despliegue previsto  | Render, Vercel y servicio compatible con MySQL                             |

## Persistencia e integración

La información se almacenará en MySQL mediante el modelo relacional definido en `database/schema.sql`.

La capa de infraestructura contempla además la integración con los sistemas externos **147, SAP y Arbopedia**.

## Seguridad

La autenticación se realizará mediante JWT y la autorización mediante roles y permisos. Los roles iniciales definidos son `INSPECTOR` y `ADMINISTRATIVO`.

## Justificación

La separación en capas permite diferenciar presentación, aplicación, dominio e infraestructura, favoreciendo el mantenimiento y la evolución del sistema.

La separación frontend/backend mediante API REST permite mantener independientes la interfaz y la lógica de aplicación. El stack seleccionado se mantiene alineado con el definido en la primera entrega y con las tecnologías conocidas por el equipo.

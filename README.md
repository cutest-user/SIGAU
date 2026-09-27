# SIGAU

## Sistema de Gestión Integral de Arbolado Urbano

**Trabajo Final Integrador — Tecnicatura Universitaria en Programación**

**Segunda Entrega — Diseño y Módulos**
**Fecha de entrega:** 27/09/2026

### Integrantes- grupo 150

* Agustín Lepka
* Rosa Lourdes Paco

### Tutor

Ing. Sergio Andrés Antonini

## Nombre y descripción general del proyecto

**SIGAU — Sistema de Gestión Integral de Arbolado Urbano** es una aplicación web orientada a la gestión y trazabilidad de las demandas, inspecciones e intervenciones relacionadas con el arbolado urbano.

El sistema busca centralizar la información proveniente de diferentes fuentes, relacionando las demandas con los ejemplares arbóreos, las inspecciones y las intervenciones realizadas. También permite registrar verificaciones, certificaciones y el historial de las acciones realizadas sobre cada ejemplar.

SIGAU está pensado principalmente para inspectores de arbolado y personal administrativo, con el objetivo de facilitar el seguimiento de las tareas y disponer de información organizada y trazable durante todo el proceso de gestión.

## Objetivo

El objetivo de SIGAU es desarrollar un MVP web que permita centralizar, organizar y realizar el seguimiento de la información relacionada con las demandas y el ciclo de gestión de los ejemplares arbóreos.

El sistema busca facilitar la relación entre demandas, ejemplares, inspecciones e intervenciones, manteniendo la trazabilidad de las acciones realizadas y permitiendo consultar el estado e historial de cada proceso.

La solución estará orientada principalmente a inspectores de arbolado y personal administrativo, contemplando tanto el trabajo en territorio como las tareas de gestión y seguimiento.

## Alcance del MVP

### Incluye

* Registro y consulta de ejemplares arbóreos.
* Registro de demandas provenientes de diferentes fuentes.
* Asociación entre demandas y uno o varios ejemplares.
* Registro de inspecciones, observaciones y evidencias.
* Gestión y seguimiento de intervenciones.
* Definición de secuencias entre intervenciones cuando una tarea dependa de otra.
* Registro de verificaciones y certificaciones.
* Seguimiento de estados de demandas e intervenciones.
* Consulta del historial y trazabilidad de los ejemplares.
* Seguimiento de las demandas y sus intervenciones.
* Organización de la información para su posterior derivación a los sistemas correspondientes.

### No incluye

SIGAU no busca reemplazar los sistemas institucionales existentes, entre ellos:

* Sistema 147 de atención de reclamos.
* SAP utilizado para la gestión de órdenes de trabajo.
* Arbopedia u otros mecanismos institucionales de publicación.

Tampoco se contempla dentro del MVP la integración automática con dichos sistemas ni el desarrollo de un GIS municipal completo.

## Tecnologías seleccionadas

### Backend

* Java 21
* Spring Boot
* Spring Web / Spring MVC
* Spring Data JPA
* Hibernate
* Maven

El backend será responsable de la lógica de negocio y de exponer una API REST para la comunicación con el frontend.

### Frontend

* React
* JavaScript
* HTML5
* CSS3
* Bootstrap

El frontend estará orientado a las tareas administrativas y al trabajo de inspección en territorio.

### Base de datos

* MySQL
* MySQL Workbench

Se utilizará una base de datos relacional para mantener la integridad y las relaciones entre los datos del sistema.

### Seguridad

* Spring Security
* JWT (JSON Web Token)

Se utilizará autenticación basada en roles para diferenciar los permisos de los usuarios.

### Control de versiones y gestión

* Git
* GitHub
* GitHub Projects

### Despliegue previsto

* Render — Backend
* Vercel — Frontend
* Servicio compatible con MySQL — Base de datos

El despliegue corresponde a una etapa posterior de implementación.

## Estructura general del repositorio

El repositorio se organiza separando la aplicación, la base de datos y la documentación del proyecto. En esta segunda entrega, los directorios `backend` y `frontend` contienen la estructura inicial prevista para la futura implementación.

```text
sigau/
├── README.md
├── frontend/
│   └── estructura inicial
├── backend/
│   └── estructura inicial
├── database/
│   ├── schema.sql
│   └── seed.sql
└── docs/
    ├── segunda_entrega_SIGAU.pdf
    ├── DER_SIGAU.png
    ├── ESQUEMA_RELACIONAL_SIGAU.png
    ├── ARQUITECTURA_SIGAU.png
    ├── FLUJO_PRINCIPAL_SIGAU.png
    ├── modelo-datos.md
    ├── modulos.md
    ├── arquitectura.md
    └── api.md
```

La carpeta `database` contiene los scripts correspondientes al esquema y los datos iniciales de la base de datos. La carpeta `docs` reúne la documentación, los diagramas y los archivos de apoyo correspondientes al diseño del sistema.

La implementación de la aplicación se realizará en una etapa posterior, una vez aprobado el diseño.
## Documentación del proyecto

La documentación del proyecto se encuentra en el directorio `docs/` del repositorio.

* **`segunda_entrega_SIGAU.pdf`** — Documento principal de la segunda entrega, con el diseño de la base de datos, arquitectura, módulos y decisiones de diseño.
* **`modelo-datos.md`** — Modelo de datos, entidades, relaciones, cardinalidades, restricciones e índices.
* **`modulos.md`** — Descripción de los módulos del backend y frontend y su relación con los requisitos funcionales.
* **`arquitectura.md`** — Arquitectura del sistema, capas, tecnologías seleccionadas y justificación técnica.
* **`api.md`** — Documentación preliminar de la API REST prevista para la etapa de implementación.
* **`DER_SIGAU.png`** — Diagrama Entidad-Relación conceptual.
* **`ESQUEMA_RELACIONAL_SIGAU.png`** — Representación gráfica del esquema relacional.
* **`ARQUITECTURA_SIGAU.png`** — Diagrama de la arquitectura propuesta.
* **`FLUJO_PRINCIPAL_SIGAU.png`** — Diagrama del flujo general del sistema.

Los documentos `.md` complementan el documento principal y permiten consultar de manera independiente los distintos aspectos del diseño.

## Estado del proyecto

Esta segunda entrega corresponde a la etapa de **diseño y planificación del sistema**.

En esta instancia se definieron:

* El modelo de datos y el esquema relacional.
* Las reglas y flujos principales del sistema.
* La arquitectura propuesta.
* Los módulos del backend y frontend.
* Las tecnologías seleccionadas.
* La estructura inicial del repositorio.
* La documentación y los diagramas correspondientes.

La implementación de la lógica de negocio, la API REST y la interfaz frontend queda prevista para una etapa posterior del proyecto.



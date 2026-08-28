SIGAU

Sistema de Gestión Integral de Arbolado Urbano

SIGAU es una aplicación web orientada a la gestión y trazabilidad de las demandas, inspecciones e intervenciones relacionadas con el arbolado urbano, independientemente de su origen.

El sistema propone centralizar y relacionar las demandas vinculadas al arbolado urbano, permitiendo asociarlas a ejemplares, gestionar las inspecciones e intervenciones correspondientes y mantener la trazabilidad de todo el proceso.

Objetivo

Desarrollar un MVP web que permita centralizar, organizar y realizar el seguimiento de la información relacionada con las demandas y el ciclo de gestión de cada ejemplar arbóreo.

El sistema estará orientado principalmente a inspectores de arbolado y personal administrativo.

Problemática

La gestión del arbolado urbano genera información proveniente de múltiples fuentes, como reclamos ciudadanos, emergencias, solicitudes de gerencia, intervenciones de oficio e inspecciones planificadas.

La distribución de esta información entre diferentes sistemas, planillas y registros puede dificultar la relación entre demandas, ejemplares, inspecciones e intervenciones, generando duplicación de tareas, inconsistencias en los datos y dificultades para reconstruir el historial de un ejemplar.

SIGAU busca abordar esta problemática mediante una herramienta especializada que permita centralizar la información y mantener la trazabilidad del proceso.

Flujo general

El funcionamiento general de SIGAU se plantea mediante el siguiente flujo:
Demanda → Identificación del ejemplar → Inspección → Intervención/es → Verificación → Certificación → Derivación

Una demanda podrá relacionarse con uno o varios ejemplares y diferentes demandas podrán estar relacionadas con un mismo ejemplar.

Las intervenciones podrán comprender una o varias tareas y contemplar dependencias entre ellas cuando la resolución de una demanda requiera una secuencia de trabajos.

Alcance del MVP

Incluye

- Registro y consulta de ejemplares arbóreos.
- Registro de demandas provenientes de diferentes fuentes.
- Asociación de una o varias demandas con un mismo ejemplar.
- Registro de inspecciones, observaciones y fotografías.
- Gestión y seguimiento de intervenciones.
- Gestión de intervenciones que requieran una secuencia de tareas.
-  Registro de verificaciones y certificaciones.
- Seguimiento de estados.
- Consulta del historial de cada ejemplar.
- Seguimiento de las demandas.
- Organización de información para su posterior derivación a los circuitos correspondientes.

No incluye

SIGAU no busca reemplazar los sistemas institucionales existentes, como:

- Sistema 147 de atención de reclamos.
- SAP utilizado para la gestión de órdenes de trabajo.
- Arbopedia u otros mecanismos institucionales de publicación.
- Tampoco se contempla dentro del MVP la integración automática con dichos sistemas ni el desarrollo de un GIS municipal completo.

Stack tecnológico

Backend

- Java 21
- Spring Boot
- Spring Web / Spring MVC
- Spring Data JPA
- Hibernate
- Maven

El backend será responsable de la lógica de negocio y de exponer una API REST para la comunicación con el frontend.

Frontend

- React
- JavaScript
- HTML5
- CSS3
- Bootstrap
El frontend estará orientado al trabajo administrativo y de inspección, contemplando su utilización desde dispositivos móviles durante el trabajo en territorio.

Base de datos

- MySQL
- MySQL Workbench

Se utilizará una base de datos relacional para mantener las relaciones e integridad de la información.

Seguridad

- Spring Security
- JWT (JSON Web Token)

Se implementará autenticación basada en roles para diferenciar los permisos de los distintos usuarios del sistema.

Control de versiones y gestión

Git
GitHub
GitHub Projects

Despliegue

Render - Backend
Vercel - Frontend
Servicio compatible con MySQL - Base de datos

Arquitectura prevista

La solución estará organizada en tres componentes principales:
 
Estructura del repositorio

SIGAU/
│
├── backend/       # API REST y lógica de negocio
├── frontend/      # Interfaz web
├── database/      # Scripts y recursos de la base de datos
├── docs/          # Documentación y entregas
│
├── .gitignore
└── README.md

Documentación

La documentación, propuestas y avances del proyecto se incorporarán progresivamente dentro del directorio docs/.

Integrantes

Agustin Lepka.
Rosa Lourdes Paco Laura.

Tutor

Sergio Andres Antonini

Trabajo Final Integrador — Tecnicatura Universitaria en Programación


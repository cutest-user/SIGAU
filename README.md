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
* Gradle

El backend será responsable de la lógica de negocio y de exponer una API REST para la comunicación con el frontend.

El backend se configura y ejecuta como se explica en la sección *Configuración y ejecución local*.

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

## Configuración y ejecución local

### Requisitos

- Java 21.
- MySQL 8.
- Node.js 20 o superior (para el frontend).

### Base de datos

Con MySQL en funcionamiento, ejecutar en este orden los scripts de la carpeta `database`:

```bash
mysql -u root -p < database/schema.sql
mysql -u root -p < database/seed.sql
```

`schema.sql` crea la base `sigau` con todas las tablas y `seed.sql` carga los roles, los orígenes de demanda y los tipos de intervención. La aplicación no modifica la estructura de la base (`SIGAU_DDL_AUTO=none` por defecto).

### Variables de entorno del backend

| Variable | Descripción |
|---|---|
| `DB_PASSWORD` | Contraseña del usuario de MySQL. Obligatoria. |
| `SIGAU_JWT_SECRET` | Clave secreta para firmar y validar los tokens JWT. Debe tener al menos 32 bytes. Obligatoria. |
| `SIGAU_ADMIN_PASSWORD` | Contraseña inicial del usuario administrativo. Solo se usa si ese usuario todavía no existe. |
| `SIGAU_DB_URL` | URL de conexión. Por defecto `jdbc:mysql://localhost:3306/sigau`. |
| `SIGAU_DB_USER` | Usuario de MySQL. Por defecto `root`. |
| `SIGAU_CORS_ORIGENES` | Orígenes del frontend permitidos, separados por coma. Por defecto `http://localhost:5173`. |
| `SIGAU_DDL_AUTO` | Modo de `spring.jpa.hibernate.ddl-auto`. Por defecto `none`. |
| `SIGAU_SHOW_SQL` | Muestra las consultas SQL en consola (`true` o `false`). Por defecto `false`. |

Estas variables no deben guardarse en el repositorio. En Windows PowerShell, desde la carpeta `backend`:

```powershell
$env:DB_PASSWORD = "contraseña-de-mysql"
$env:SIGAU_JWT_SECRET = "ReemplazarPorUnaClavePropiaDeAlMenos32Caracteres"
$env:SIGAU_ADMIN_PASSWORD = "DefinirUnaContraseñaSegura"
```

El usuario administrativo inicial es `admin@sigau.com`. La contraseña solo se utiliza durante su creación. Las variables hay que volver a definirlas en cada sesión nueva de PowerShell.

### Backend

Desde la carpeta `backend`:

```powershell
.\gradlew.bat build
.\gradlew.bat bootRun
```

La API queda disponible en `http://localhost:8080`. Para ejecutar solo las pruebas unitarias: `.\gradlew.bat test`. La prueba de arranque del contexto se ejecuta únicamente si `DB_PASSWORD` está definida.

### Frontend

Desde la carpeta `frontend`:

```bash
npm install
cp .env.example .env
npm run dev
```

La aplicación queda disponible en `http://localhost:5173`. Más detalles en `frontend/README.md`.

### Primer uso

1. Ingresar con `admin@sigau.com` y la contraseña definida en `SIGAU_ADMIN_PASSWORD`.
2. En **Usuarios**, crear al menos un usuario con rol *Inspector*, que es quien registra inspecciones, intervenciones, verificaciones y certificaciones.
3. Registrar una demanda, un ejemplar y asociarlo desde el detalle de la demanda.

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

El repositorio organiza el proyecto separando la aplicación, la base de datos y la documentación. El directorio `backend` contiene la implementación de la API REST, mientras que `frontend` contiene la aplicación cliente. La carpeta `database` reúne los scripts del esquema y los datos iniciales, y `docs` contiene la documentación técnica y los diagramas del sistema.
```text
sigau/
├── README.md
├── frontend/
│   └── src/
├── backend/
│   └── src/
├── database/
│   ├── schema.sql
│   └── seed.sql
└── docs/
    ├── TFI SIGAU.pdf
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

## Documentación del proyecto

La documentación del proyecto se encuentra en el directorio `docs/` del repositorio.

* **`TFI SIGAU.pdf`** — Informe del despliegue final del sistema y con el link del video.
* **`segunda_entrega_SIGAU.pdf`** — Documento principal de la segunda entrega, con el diseño de la base de datos, arquitectura, módulos y decisiones de diseño.
* **`modelo-datos.md`** — Modelo de datos, entidades, relaciones, cardinalidades, restricciones e índices.
* **`modulos.md`** — Descripción de los módulos del backend y frontend y su relación con los requisitos funcionales.
* **`arquitectura.md`** — Arquitectura del sistema, capas, tecnologías seleccionadas y justificación técnica.
* **`api.md`** — Documentación de la API REST: endpoints, permisos por rol y reglas de negocio.
* **`DER_SIGAU.png`** — Diagrama Entidad-Relación conceptual.
* **`ESQUEMA_RELACIONAL_SIGAU.png`** — Representación gráfica del esquema relacional.
* **`ARQUITECTURA_SIGAU.png`** — Diagrama de la arquitectura propuesta.
* **`FLUJO_PRINCIPAL_SIGAU.png`** — Diagrama del flujo general del sistema.

Los documentos `.md` complementan el documento principal y permiten consultar de manera independiente los distintos aspectos del diseño.


## Estado del proyecto

El proyecto se encuentra en la etapa de integración y pruebas del MVP. El funcionamiento completo del sistema se muestra en el video.

Está implementado:

- API REST con Java y Spring Boot: ejemplares, demandas, ubicaciones, inspecciones, evidencias, intervenciones, verificaciones, certificaciones, derivaciones, seguimiento, historial, reportes y usuarios.
- Autenticación con JWT, permisos por rol y CORS configurable.
- Reglas de negocio: estados y transiciones de demandas e intervenciones, secuencia entre intervenciones, extracción de ejemplares, condiciones para certificar y registro de cambios en el historial.
- Calidad de datos: validación de los datos de entrada, normalización de ubicaciones, reutilización de direcciones repetidas y aviso ante demandas o ejemplares posiblemente duplicados.
- Frontend en React con inicio de sesión y pantallas para todos los módulos.
- Pruebas unitarias de las reglas de negocio.

## Sistema desplegado

El sistema se encuentra completamente desplegado y funcional en la nube:

Frontend: https://sigau.vercel.app — desplegado en Vercel.

Backend: API REST desplegada en Render.

Base de datos: MySQL gestionado en Aiven.

La comunicación entre el frontend y el backend se realiza a través de la API REST, con CORS configurado para permitir el origen del frontend en Vercel. La base de datos en Aiven requiere conexión SSL (sslMode=REQUIRED) y las credenciales se gestionan mediante variables de entorno en Render, sin exponerse en el repositorio.

## Notas sobre el despliegue

El backend se construye con Gradle y se ejecuta como un JAR de Spring Boot en Render.

La base de datos en Aiven fue inicializada con los scripts schema.sql y seed.sql de la carpeta database.

El frontend en Vercel consume la API del backend y permite el inicio de sesión con el usuario administrativo inicial.

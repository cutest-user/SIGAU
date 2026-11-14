# SIGAU - Frontend

**Trabajo Final Integrador — Tecnicatura Universitaria en Programación**

**Grupo:** 150

**Integrantes:** Rosa Lourdes Paco Laura - Agustin Mario Nicolas Lepka

Aplicación web en React (Vite) con Bootstrap para el Sistema de Gestión Integral de Arbolado Urbano.

## Requisitos

-Node.js 20 o superior

-Backend de SIGAU en ejecución (por defecto en `http://localhost:8080`)

## Puesta en marcha

```bash
npm install
cp .env.example .env
npm run dev
```

La aplicación queda disponible en `http://localhost:5173`. Si el backend corre en otra dirección, se cambia `VITE_API_URL` en el archivo `.env`.

Para que el navegador pueda consultar el backend, el origen del frontend tiene que estar en la variable `SIGAU_CORS_ORIGENES` del backend (ver el README principal).

## Scripts

- `npm run dev`: servidor de desarrollo
- `npm run build`: compilación para producción en `dist/`
- `npm run lint`: revisión del código con ESLint

## Estructura

```
src/
  api/          cliente HTTP, formatos y utilidades de ubicación
  auth/         sesión del usuario (JWT)
  components/   piezas compartidas (cabecera, menú, alertas, etc.)
  hooks/        carga de datos y ejecución de acciones
  pages/        una carpeta por módulo (demandas, ejemplares, inspecciones, ...)
```

## Permisos en pantalla

-**Inspector**: registra inspecciones, evidencias, intervenciones, verificaciones y certificaciones.

-**Administrativo**: administra usuarios, registra demandas, ejemplares y derivaciones.

Ambos roles pueden consultar toda la información. Los permisos se validan también en el backend.

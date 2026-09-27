CREATE DATABASE sigau;
USE sigau;

-- Roles de usuario
CREATE TABLE rol (
id INT AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR(50) NOT NULL UNIQUE,
descripcion VARCHAR(255)
);

-- Usuarios del sistema
CREATE TABLE usuario (
id INT AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR(100) NOT NULL,
apellido VARCHAR(150) NOT NULL UNIQUE,
email VARCHAR(150) NOT NULL UNIQUE,
password_hash VARCHAR(255) NOT NULL,
rol_id INT NOT NULL,
activo BOOLEAN NOT NULL DEFAULT TRUE,
CONSTRAINT fk_usuario_rol
FOREIGN KEY (rol_id) REFERENCES rol(id)
);
-- Ubicaciones normalizadas

CREATE TABLE ubicacion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    calle VARCHAR(150),
    numero VARCHAR(20),
    barrio VARCHAR(100),
    comuna VARCHAR(50),
    referencia VARCHAR(255)
);
-- Ejemplares arbóreos

CREATE TABLE ejemplar (
    id INT AUTO_INCREMENT PRIMARY KEY,
    especie VARCHAR(100),
    altura DECIMAL(5,2),
    dap DECIMAL(6,2),
    ubicacion_id INT,
    estado VARCHAR(50) NOT NULL DEFAULT 'ACTIVO',
    fecha_alta DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    observaciones TEXT,
    CONSTRAINT fk_ejemplar_ubicacion
        FOREIGN KEY (ubicacion_id) REFERENCES ubicacion(id)
);
-- Origen de la demanda
CREATE TABLE origen_demanda (
id INT AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR(50) NOT NULL UNIQUE,
descripcion VARCHAR(255)
);

-- Demandas
CREATE TABLE demanda (
    id INT AUTO_INCREMENT PRIMARY KEY,
    origen_id INT NOT NULL,
    usuario_id INT NOT NULL,
    ubicacion_id INT,
    fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    motivo VARCHAR(255) NOT NULL,
    descripcion TEXT,
    estado VARCHAR(50) NOT NULL DEFAULT 'INGRESADA',
    prioridad VARCHAR(20) DEFAULT 'MEDIA',
    fecha_cierre DATETIME,
    observaciones TEXT,

    CONSTRAINT fk_demanda_origen
        FOREIGN KEY (origen_id) REFERENCES origen_demanda(id),

    CONSTRAINT fk_demanda_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuario(id),

    CONSTRAINT fk_demanda_ubicacion
        FOREIGN KEY (ubicacion_id) REFERENCES ubicacion(id)
);



-- Relación N:M entre demandas y ejemplares
CREATE TABLE demanda_ejemplar (
demanda_id INT NOT NULL,
ejemplar_id INT NOT NULL,
fecha_asociacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
observacion VARCHAR(255),
PRIMARY KEY (demanda_id, ejemplar_id),
CONSTRAINT fk_de_demanda
FOREIGN KEY (demanda_id) REFERENCES demanda(id),
CONSTRAINT fk_de_ejemplar
FOREIGN KEY (ejemplar_id) REFERENCES ejemplar(id)
);
-- Inspecciones
CREATE TABLE inspeccion (
id INT AUTO_INCREMENT PRIMARY KEY,
ejemplar_id INT NOT NULL,
inspector_id INT NOT NULL,
fecha DATE NOT NULL DEFAULT CURRENT_TIMESTAMP,
especie_identificada VARCHAR(100),
altura DECIMAL (5,2),
dap DECIMAL (6,2),
diagnostico TEXT,
observaciones TEXT,
estado VARCHAR(50) NOT NULL DEFAULT 'REALIZADA',
CONSTRAINT fk_inspeccion_ejemplar
FOREIGN KEY (ejemplar_id) REFERENCES ejemplar(id),
CONSTRAINT fk_inspeccion_usuario
FOREIGN KEY (inspector_id) REFERENCES usuario(id)
);

-- Relación N:M entre inspecciones y demandas
CREATE TABLE inspeccion_demanda (
inspeccion_id INT NOT NULL,
demanda_id INT NOT NULL,
PRIMARY KEY (inspeccion_id, demanda_id),
CONSTRAINT fk_id_inspeccion
FOREIGN KEY (inspeccion_id) REFERENCES inspeccion(id),
CONSTRAINT fk_id_demanda
FOREIGN KEY (demanda_id) REFERENCES demanda(id)
);

-- Evidencias de inspección
CREATE TABLE evidencia_inspeccion (
id INT AUTO_INCREMENT PRIMARY KEY,
inspeccion_id INT NOT NULL,
url VARCHAR(500) NOT NULL,
descripcion VARCHAR(255),
fecha DATE NOT NULL DEFAULT CURRENT_TIMESTAMP,
CONSTRAINT fk_evidencia_inspeccion
FOREIGN KEY (inspeccion_id) REFERENCES inspeccion(id)
);

-- Tipos de intervención
CREATE TABLE tipo_intervencion (
id INT AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR(100) NOT NULL UNIQUE,
descripcion VARCHAR(255)
);

-- Intervenciones
CREATE TABLE intervencion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    demanda_id INT NOT NULL,
    ejemplar_id INT NOT NULL,
    tipo_intervencion_id INT NOT NULL,
    estado VARCHAR(50) NOT NULL DEFAULT 'PENDIENTE',
    fecha_planificada DATE,
    fecha_ejecucion DATE,
    intervencion_previa_id INT,
    observaciones TEXT,

    CONSTRAINT fk_intervencion_demanda
        FOREIGN KEY (demanda_id) REFERENCES demanda(id),

    CONSTRAINT fk_intervencion_ejemplar
        FOREIGN KEY (ejemplar_id) REFERENCES ejemplar(id),

    CONSTRAINT fk_intervencion_tipo
        FOREIGN KEY (tipo_intervencion_id) REFERENCES tipo_intervencion(id),

    CONSTRAINT fk_intervencion_previa
        FOREIGN KEY (intervencion_previa_id) REFERENCES intervencion(id)
);

-- Evidencias de intervención
CREATE TABLE evidencia_intervencion (
id INT AUTO_INCREMENT PRIMARY KEY,
intervencion_id INT NOT NULL,
url VARCHAR(500) NOT NULL,
descripcion VARCHAR(255),
fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
CONSTRAINT fk_evidencia_intervencion
FOREIGN KEY (intervencion_id) REFERENCES intervencion(id)
);

-- Verificaciones
CREATE TABLE verificacion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    intervencion_id INT NOT NULL,
    usuario_id INT NOT NULL,
    fecha DATE NOT NULL,
    resultado VARCHAR(50) NOT NULL,
    evidencia VARCHAR(500),
    observaciones TEXT,

    CONSTRAINT fk_verificacion_intervencion
        FOREIGN KEY (intervencion_id) REFERENCES intervencion(id),

    CONSTRAINT fk_verificacion_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

-- Certificaciones
CREATE TABLE certificacion (
    id VARCHAR(100) PRIMARY KEY,
    demanda_id INT NOT NULL UNIQUE,
    usuario_id INT NOT NULL,
    fecha DATE NOT NULL,
    estado VARCHAR(50) NOT NULL,
    observaciones TEXT,

    CONSTRAINT fk_certificacion_demanda
        FOREIGN KEY (demanda_id) REFERENCES demanda(id),

    CONSTRAINT fk_certificacion_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);
-- Derivaciones a sistemas existentes
CREATE TABLE derivacion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    demanda_id INT NOT NULL,
    usuario_id INT NOT NULL,
    sistema_destino VARCHAR(50) NOT NULL,
    fecha DATE NOT NULL DEFAULT (CURRENT_DATE),
    estado VARCHAR(50) NOT NULL DEFAULT 'PENDIENTE',
    referencia_externa VARCHAR(100),
    observaciones TEXT,

    CONSTRAINT fk_derivacion_demanda
        FOREIGN KEY (demanda_id) REFERENCES demanda(id),

    CONSTRAINT fk_derivacion_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

-- Historial y trazabilidad
CREATE TABLE historial (
    id INT AUTO_INCREMENT PRIMARY KEY,
    entidad VARCHAR(50) NOT NULL,
    entidad_id INT NOT NULL,
    intervencion_id INT,
    estado_anterior VARCHAR(50),
    estado_nuevo VARCHAR(50),
    usuario_id INT NOT NULL,
    fecha DATE NOT NULL,
    observacion TEXT,

    CONSTRAINT fk_historial_intervencion
        FOREIGN KEY (intervencion_id) REFERENCES intervencion(id),

    CONSTRAINT fk_historial_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

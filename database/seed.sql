USE sigau;

-- Roles
INSERT INTO rol (nombre, descripcion) VALUES
('INSPECTOR', 'Inspector de arbolado'),
('ADMINISTRATIVO', 'Personal administrativo');


-- Orígenes de demanda
INSERT INTO origen_demanda (nombre, descripcion) VALUES
('RECLAMO_147', 'Reclamo ciudadano recibido por canal institucional'),
('EMERGENCIA', 'Emergencia'),
('GERENCIA', 'Solicitud de gerencia'),
('OFICIO', 'Intervención de oficio detectada por inspector'),
('PLANIFICACION', 'Planificación de mantenimiento');


-- Tipos de intervención
INSERT INTO tipo_intervencion (nombre, descripcion) VALUES
('PODA', 'Poda aérea'),
('EXTRACCION', 'Extracción'),
('PLANTACION', 'Plantación'),
('APERTURA_PLANTERA', 'Apertura de plantera'),
('CORTE_RAIZ', 'Corte de raíz'),
('EXTRACCION_CEPA', 'Extracción de cepa'),
('REPARACION_VEREDA', 'Reparación de vereda');

show tables;
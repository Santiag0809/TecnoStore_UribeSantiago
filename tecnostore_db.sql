-- =====================================
-- TecnoStore Database Script
-- =====================================

DROP DATABASE IF EXISTS tecnostore;
CREATE DATABASE tecnostore;
USE tecnostore;

-- =====================================
-- Tabla: marca
-- =====================================

CREATE TABLE marca (
    id INT NOT NULL AUTO_INCREMENT,
    marca VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

-- =====================================
-- Tabla: cliente
-- =====================================

CREATE TABLE cliente (
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    identificacion VARCHAR(50) NOT NULL UNIQUE,
    correo VARCHAR(50) NOT NULL,
    telefono VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

-- =====================================
-- Tabla: celular
-- =====================================

CREATE TABLE celular (
    id INT NOT NULL AUTO_INCREMENT,
    modelo VARCHAR(50) NOT NULL,
    id_marca INT NOT NULL,
    precio DOUBLE NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    sistema_operativo VARCHAR(50) NOT NULL,
    gama ENUM('Alta', 'Media', 'Baja') NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_marca) REFERENCES marca(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

-- =====================================
-- Tabla: venta
-- =====================================

CREATE TABLE venta (
    id INT NOT NULL AUTO_INCREMENT,
    id_cliente INT NOT NULL,
    fecha DATE NOT NULL,
    total DOUBLE NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_cliente) REFERENCES cliente(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

-- =====================================
-- Tabla: detalle_venta
-- =====================================

CREATE TABLE detalle_venta (
    id INT NOT NULL AUTO_INCREMENT,
    id_venta INT NOT NULL,
    id_celular INT NOT NULL,
    cantidad INT NOT NULL DEFAULT 0,
    subtotal DOUBLE NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_venta) REFERENCES venta(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (id_celular) REFERENCES celular(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);
-- =====================================
-- Datos de ejemplo (opcional)
-- =====================================

INSERT INTO marca (marca) VALUES
('Samsung'),
('Apple'),
('Xiaomi');


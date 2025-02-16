-- Tabla Persona
CREATE TABLE persona (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         nombre VARCHAR(100) NOT NULL,
                         genero VARCHAR(50) NOT NULL,
                         edad INT NOT NULL,
                         identificacion VARCHAR(50) NOT NULL UNIQUE,
                         direccion VARCHAR(255),
                         telefono VARCHAR(50)
);

-- Tabla Cliente (Hereda de Persona)
CREATE TABLE cliente (
                         id BIGINT NOT NULL PRIMARY KEY,
                         cliente_id VARCHAR(50) NOT NULL UNIQUE,
                         contrasena VARCHAR(100) NOT NULL,
                         estado BOOLEAN NOT NULL,
                         CONSTRAINT fk_persona FOREIGN KEY (id) REFERENCES persona(id) ON DELETE CASCADE
);


-- Datos de prueba (Opcional)
INSERT INTO persona (nombre, genero, edad, identificacion, direccion, telefono) VALUES
                                                                                    ('Jose Lema', 'Masculino', 30, '1234567890', 'Otavalo sn y principal', '098254785'),
                                                                                    ('Marianela Montalvo ', 'Femenino', 25, '9876543210', 'Amazonas y NNUU ', '097548965');

-- Insertar en la tabla Cliente, asegurando la relación con la tabla Persona
INSERT INTO cliente (id, cliente_id, contrasena, estado) VALUES
                                                             (1, '1', '123', TRUE),
                                                             (2, '2', '5678', TRUE);
-- Datos de prueba (Opcional)
INSERT INTO cuenta (numero_cuenta, tipo_cuenta, saldo_inicial, estado) VALUES
                                                                   ('1234567890', 'AHORRO', 1000.00, TRUE),
                                                                   ('0987654321', 'CORRIENTE', 2500.00, TRUE);

INSERT INTO movimiento (tipo_movimiento, valor, saldo, cuenta_id) VALUES
                                                                      ('DEPOSITO', 500.00, 1500.00, 1),
                                                                      ('RETIRO', 200.00, 1300.00, 1),
                                                                      ('DEPOSITO', 1000.00, 3500.00, 2);

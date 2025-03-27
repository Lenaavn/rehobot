INSERT INTO usuario (nombre, email, contrasena, rol, telefono) VALUES
('Leandro', 'leandro@example.com', '1234', 'ADMIN', '123456789'),
('Maria', 'maria@example.com', '1234', 'USER', '987654321');

INSERT INTO vehiculo (id_usuario, marca, modelo, matricula) VALUES
(1, 'Toyota', 'Corolla', 'ABC1234'),
(2, 'Honda', 'Civic', 'XYZ5678');

INSERT INTO servicio (nombre, descripcion, precio) VALUES
('Lavado_1', 'Incluye lavado exterior e interior.', 15.00),
('Lavado_3', 'Incluye lavado completo y encerado.', 30.00);

INSERT INTO pago (id_cita, monto, metodo_pago) VALUES
(1, 35.24, 'EFECTIVO'), 
(2, 30.00, 'TARJETA');

INSERT INTO cita (id_vehiculo, id_servicio, id_pago, fecha, hora, estado) VALUES
(1, 1, 1, '2025-03-26', '09:00:00', 'PAGADA'),
(2, 2, 2, '2025-03-27', '11:00:00', 'PAGADA');

INSERT INTO servicita (id_servicio, id_cita) VALUES
(1, 1),
(2, 2);


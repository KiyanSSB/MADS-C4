/* Populate tables */
INSERT INTO usuarios (id, email, nombre, password, fecha_nacimiento) VALUES('1', 'domingo@ua', 'Domingo Gallardo', '123', '2001-02-10');
INSERT INTO usuarios (id, email, nombre, password, fecha_nacimiento) VALUES('2', 'a@a.com', 'a', 'a', '2001-02-10');

INSERT INTO tareas (id, titulo, usuario_id) VALUES('1', 'Lavar coche', '1');
INSERT INTO tareas (id, titulo, usuario_id) VALUES('2', 'Renovar DNI', '1');

INSERT INTO recetas (id , nombre , ingredientes, usuario_id) VALUES ('1' , 'Receta 1' , 'Ingredientes 1' ,  '1')
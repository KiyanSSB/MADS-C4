/* Populate tables */
INSERT INTO usuarios (id, email, nombre, password, fecha_nacimiento,bloqueado, admin) VALUES('1', 'domingo@ua', 'Domingo Gallardo', '123', '2001-02-10', false, false );
INSERT INTO usuarios (id, email, nombre, password, fecha_nacimiento,bloqueado,  admin ) VALUES('2', 'a@a.com', 'a', 'a', '2001-02-10', false, false );
INSERT INTO usuarios (id, email, nombre, password, fecha_nacimiento, admin) VALUES('3', 'admin@admin.com', 'admin', 'admin', '2001-02-10' , true);


INSERT INTO recetas (id , nombre , ingredientes, favorita, usuario_id, compartida , likes) VALUES ('1' , 'Receta 1 favorita no compartida'    , 'Ingredientes 1' , true ,   '1' , false , 0);
INSERT INTO recetas (id , nombre , ingredientes, favorita, usuario_id, compartida , likes) VALUES ('2' , 'Receta 1 favorita compartida'       , 'Ingredientes 1' , true ,   '1' , true , 0 );



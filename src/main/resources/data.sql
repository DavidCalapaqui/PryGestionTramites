<<<<<<< HEAD
insert into direcciones (pk_direccion, nombre) VALUES(1,'Archivo');
insert into direcciones (pk_direccion, nombre)VALUES(2,'Dirección Financiera');
insert into direcciones (pk_direccion, nombre)VALUES(3,'Dirección Administrativa');
insert into direcciones (pk_direccion, nombre)VALUES(4,'Dirección de Desarrollo Social');
insert into direcciones (pk_direccion, nombre)VALUES(5,'Dirección de Gestión Ambiental');
insert into direcciones (pk_direccion, nombre)VALUES(6,'Registro de la Propiedad');
insert into direcciones (pk_direccion, nombre) VALUES(7,'Dirección Financiera');
insert into direcciones (pk_direccion, nombre)VALUES(8,'Dirección de Planificación');
insert into direcciones (pk_direccion, nombre)VALUES(9,'Dirección de Obras Públicas');
insert into direcciones (pk_direccion, nombre)VALUES(10,'Secretaria General');
insert into direcciones (pk_direccion, nombre)VALUES(11,'Alcaldia');
insert into direcciones (pk_direccion, nombre)VALUES(12,'Archivo');
insert into usuarios (habilitado, nombre, password, fk_direccion) values(1, 'dirArchivo', '$2a$10$/q6FGfP50cILXrSuN7DiLOdTuaPcOhBdlFC4yc/iRlNJyVQCXW.Iy',1);
insert into roles (nombre, fk_usuario) values('ROLE_ARCH',1);
=======
insert into usuarios (habilitado, nombre, password) values(1, 'dirArchivo', '$2a$10$/q6FGfP50cILXrSuN7DiLOdTuaPcOhBdlFC4yc/iRlNJyVQCXW.Iy')

insert into roles (nombre) values('ROLE_ARCH')


insert into direcciones values('Dirección Financiera')
insert into direcciones values('Dirección Administrativa')
insert into direcciones values('Dirección de Desarrollo Social')
insert into direcciones values('Dirección de Gestión Ambiental')
insert into direcciones values('Registro de la Propiedad')
insert into direcciones values('Dirección Financiera')
insert into direcciones values('Dirección de Planificación')
insert into direcciones values('Dirección de Obras Públicas')
insert into direcciones values('Secretaria General')
insert into direcciones values('Alcaldia')
insert into direcciones values('Archivo')
>>>>>>> 5aeda7345dda6f2ff1b92daa2ec7a01fc6c3b1b0

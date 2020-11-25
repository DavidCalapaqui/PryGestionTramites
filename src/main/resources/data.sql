



insert into direcciones values('Archivo')
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
insert into usuarios (habilitado, nombre, password, fk_direccion) values(1, 'dirArchivo', '$2a$10$/q6FGfP50cILXrSuN7DiLOdTuaPcOhBdlFC4yc/iRlNJyVQCXW.Iy',1)
insert into roles (nombre, fk_usuario) values('ROLE_ARCH',1)
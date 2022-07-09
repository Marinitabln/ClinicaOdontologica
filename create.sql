create table if not exists pacientes (
    id bigint auto_increment primary key,
    apellido varchar(255),
    nombre varchar(255),
    dni varchar (255),
    fecha_ingreso varchar(255),
    idDomicilio bigint);

create table if not exists domicilios (
    id bigint auto_increment primary key,
    calle varchar(255),
    numero long,
    localidad varchar (255),
    provincia varchar(255));

create table if not exists odontologos (
    id bigint auto_increment primary key,
    apellido varchar(255),
    nombre varchar (255),
    matricula varchar(255));

create table if not exists turnos (
        id bigint auto_increment primary key,
        idPaciente bigint,
        idOdontologo bigint);


-- Pueden agregar acá sentencias Insert para precargar datos.
INSERT INTO domicilios (calle, numero, localidad, provincia) VALUES('Sarmiento 95', 1, 'Lujan de Cuyo ', 'Mendoza');

INSERT INTO pacientes (apellido, nombre, dni, fecha_ingreso, idDomicilio) VALUES('Blanco', 'Marina', '33822150', '2022-05-31', 1);

INSERT INTO odontologos (apellido, nombre, matricula) VALUES('Torrez', 'Juan Manuel', '3474');


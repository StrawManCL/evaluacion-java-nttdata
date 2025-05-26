create sequence telefono_seq start with 1 increment by 50;
create table telefono
(
    id            bigint not null,
    codigo_ciudad varchar(255),
    codigo_pais   varchar(255),
    numero        varchar(255),
    primary key (id)
);
create table usuario
(
    activo       boolean not null,
    creado       timestamp(6) with time zone,
    modificado
                 timestamp(6) with time zone,
    ultimo_login timestamp(6) with time zone,
    id           uuid    not null,
    clave        varchar(255),
    correo       varchar(255) unique,
    nombre       varchar(255),
    token        varchar(255),
    primary key (id)
);
create table usuario_telefono
(
    telefono_id bigint not null unique,
    usuario_id  uuid   not null
);
alter table if exists usuario_telefono
    add constraint FK29w8uwgxdc2xijhih46jbst4c foreign key
        (telefono_id) references telefono;
alter table if exists usuario_telefono
    add constraint FK3vskcg4v9q2i6y3qsdugyc0ib foreign key
        (usuario_id) references usuario;
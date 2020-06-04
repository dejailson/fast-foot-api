create table cidade (id bigint not null auto_increment, nome varchar(20) not null, uf char(2) not null, primary key (id)) engine=InnoDB
create table restaurante (id bigint not null auto_increment, descricao varchar(150) not null, bairro varchar(50), cep varchar(10) not null, complemento varchar(50), logradouro varchar(150) not null, numero varchar(10) not null, nome varchar(30) not null, cidade bigint not null, primary key (id)) engine=InnoDB
create table restaurante_servico_entrega (restaurante_id bigint not null, servico_entrega_id bigint not null, primary key (restaurante_id, servico_entrega_id)) engine=InnoDB
create table servico_entrega (id bigint not null auto_increment, nome varchar(20) not null, primary key (id)) engine=InnoDB
alter table restaurante add constraint UK_jd0r4hoyk81uwt7jb88va5noa unique (nome)
alter table servico_entrega add constraint UK_cqo23epljjonj1p58ultehymo unique (nome)
alter table restaurante add constraint FK_CIDADE foreign key (cidade) references cidade (id)
alter table restaurante_servico_entrega add constraint SERVICO_ENTREGA_ID_FK foreign key (servico_entrega_id) references servico_entrega (id)
alter table restaurante_servico_entrega add constraint RESTAURANTE_ID_FK foreign key (restaurante_id) references restaurante (id)

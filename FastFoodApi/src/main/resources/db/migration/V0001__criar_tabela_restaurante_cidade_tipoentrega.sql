create table cidade (
	id bigint not null auto_increment,
	nome varchar(20) not null,
	uf char(2) not null, 
	primary key (id)) engine=InnoDB;
	
create table restaurante (
	id bigint not null auto_increment,
	descricao varchar(150) not null,
	bairro varchar(50),
	cep varchar(10) not null,
	complemento varchar(50),
	logradouro varchar(150) not null,
	numero varchar(10) not null,
	nome varchar(30) not null,
	cidade bigint not null,
	tipo_entrega bigint not null,
	primary key (id)) engine=InnoDB;
	
create table tipo_entrega (
	id bigint not null auto_increment,
	nome varchar(20) not null,
	primary key (id)) engine=InnoDB;
	
alter table restaurante add constraint UK_NOME_RESTAURANTE unique (nome);
alter table tipo_entrega add constraint UK_NOME_ENTREGA unique (nome);
alter table restaurante add constraint FK_CIDADE foreign key (cidade) references cidade (id);
alter table restaurante add constraint FK_TIPO_ENTREGA foreign key (tipo_entrega) references tipo_entrega (id);

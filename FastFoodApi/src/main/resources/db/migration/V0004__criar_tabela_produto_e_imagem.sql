create table imagem (id bigint not null auto_increment, 
	descricao varchar(150) not null,
	nome varchar(255) not null,
	tipo_conteudo varchar(255) not null,
	produto_id bigint,
	primary key (id)) engine=InnoDB;
create table produto (id bigint not null auto_increment,
	descricao varchar(150) not null,
	disponivel boolean default true not null,
	nome varchar(100) not null,
	peso bigint not null,
	preco_unitario decimal(19,2) not null,
	sku varchar(255) not null,
	tipo varchar(255) not null,
	restaurante_id bigint,
	primary key (id)) engine=InnoDB;
	
alter table produto add constraint UK_SKU_PRODUTO unique (sku);
alter table imagem add constraint PRODUTO_ID_FK foreign key (produto_id) references produto (id);
alter table produto add constraint FK_RESTAURANTE_ID foreign key (restaurante_id) references restaurante (id)
#remover a coluna tipo_entrega da tabela restaurante
alter table restaurante drop foreign key FK_TIPO_ENTREGA;
alter table restaurante drop column tipo_entrega;

#renomear a tabela tipo_entrega para servico_entrega
rename table tipo_entrega to servico_entrega;

#adicionar tabela relacional
create table restaurante_servico_entrega (restaurante_id bigint not null, servico_entrega_id bigint not null, primary key (restaurante_id, servico_entrega_id)) engine=InnoDB;

alter table restaurante_servico_entrega add constraint SERVICO_ENTREGA_ID_FK foreign key (servico_entrega_id) references servico_entrega (id);
alter table restaurante_servico_entrega add constraint RESTAURANTE_ID_FK foreign key (restaurante_id) references restaurante (id);

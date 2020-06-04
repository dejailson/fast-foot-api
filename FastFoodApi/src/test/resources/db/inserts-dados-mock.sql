insert into cidade(nome,uf) values("Fortaleza","CE");
insert into cidade(nome,uf) values("Imperatriz","MA");

insert into servico_entrega(nome) values("Delivery");
insert into servico_entrega(nome) values("Local");

insert into restaurante (nome, descricao, logradouro, numero, bairro, cep, complemento, cidade,aberto) values ("Cabana do Sol","Comida Caseira", "Rua João Pinheiro", "1000", "Centro", "38400-999", "Próximo a praia", (select c.id from cidade c where c.nome="Fortaleza"),true);

insert into restaurante (nome, descricao, logradouro, numero, bairro, cep, complemento, cidade,aberto) values ("Cheiro Verde","Comida Caseira", "Rua João Pinheiro", "1000", "Centro", "38400-999", "Próximo a praia", (select c.id from cidade c where c.nome="Fortaleza"),true);

insert into restaurante_servico_entrega (restaurante_id,servico_entrega_id) values((select r.id from restaurante r where r.nome="Cabana do Sol"),(select se.id from servico_entrega se where se.nome="Delivery"));
insert into restaurante_servico_entrega (restaurante_id,servico_entrega_id) values((select r.id from restaurante r where r.nome="Cabana do Sol"),(select se.id from servico_entrega se where se.nome="Local"));
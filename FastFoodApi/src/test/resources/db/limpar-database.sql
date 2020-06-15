set foreign_key_checks = 0;

DELETE FROM restaurante;
DELETE FROM cidade;
DELETE FROM servico_entrega;
DELETE FROM restaurante_servico_entrega;
DELETE FROM imagem;
DELETE FROM produto;

set foreign_key_checks = 1;

alter table restaurante auto_increment = 1;
alter table cidade auto_increment = 1;
alter table servico_entrega auto_increment = 1;
alter table produto auto_increment = 1;
alter table imagem auto_increment = 1;
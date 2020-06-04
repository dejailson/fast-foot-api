set foreign_key_checks = 0;

DELETE FROM restaurante;
DELETE FROM cidade;
DELETE FROM servico_entrega;
DELETE FROM restaurante_servico_entrega;

set foreign_key_checks = 1;

alter table restaurante auto_increment = 1;
alter table cidade auto_increment = 1;
alter table servico_entrega auto_increment = 1;
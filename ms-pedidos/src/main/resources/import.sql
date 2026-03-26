insert into tb_pedido(nome, cpf, data, status, valor_total) values('Jon Snow', '12345678901', '2025-11-25', 'CRIADO',  540.0);
insert into tb_pedido(nome, cpf,data, status, valor_total) values('Jayra Stark', '23456789012', '2026-01-25', 'CRIADO', 3599.0);

insert into tb_item_do_pedido(quantidade, descricao, preco_unitario, pedido_id) values(2, 'mouse sem fio microsoft', 250.0, 1);
insert into tb_item_do_pedido(quantidade, descricao, preco_unitario, pedido_id) values(1, 'teclado sem fio microsoft', 250.0, 1);
insert into tb_item_do_pedido(quantidade, descricao, preco_unitario, pedido_id) values(1, 'smart tv', 1599.0, 1);

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `gerar_pedidos`(IN qtd_pedidos INT)
BEGIN
    DECLARE i INT DEFAULT 0;
    DECLARE usuario_id CHAR(36);
    DECLARE pedido_id CHAR(36);
    DECLARE produto_id CHAR(36);  -- variável para receber o ID do produto
    DECLARE preco_unitario DECIMAL(10,2);
    DECLARE quantidade INT;
    DECLARE preco_total DECIMAL(10,2);
    DECLARE valor_total_pedido DECIMAL(10,2);
    DECLARE status_pedido ENUM('PENDENTE', 'PAGO', 'CANCELADO');
    DECLARE produto_existe INT DEFAULT 0;
    DECLARE usuario_existe INT DEFAULT 0;
    DECLARE total_produtos_disponiveis INT DEFAULT 0;
    DECLARE data_criacao_pedido DATETIME;
    DECLARE data_atualizacao_pedido DATETIME;
    DECLARE data_pagamento DATETIME;
    DECLARE num_itens INT;

    -- Verifica se há usuários do perfil USER
    SELECT COUNT(*) INTO usuario_existe FROM usuarios WHERE perfil = 'USER';
    IF usuario_existe = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Nenhum usuário USER disponível para criar pedidos.';
    END IF;

    -- Verifica se há produtos
    SELECT COUNT(*) INTO produto_existe FROM produtos;
    IF produto_existe = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Nenhum produto disponível para criar pedidos.';
    END IF;

    WHILE i < qtd_pedidos DO
        -- Seleciona usuário do perfil USER
        SET usuario_id = (
            SELECT id 
            FROM usuarios 
            WHERE perfil = 'USER' 
            ORDER BY RAND() 
            LIMIT 1
        );

        -- Status aleatório
        SET status_pedido = (
            CASE FLOOR(RAND() * 3)
                WHEN 0 THEN 'PENDENTE'
                WHEN 1 THEN 'PAGO'
                ELSE 'CANCELADO'
            END
        );

        -- Datas aleatórias
        SET data_criacao_pedido = DATE_ADD(NOW(), INTERVAL -FLOOR(RAND() * 60) DAY);
        SET data_atualizacao_pedido = DATE_ADD(data_criacao_pedido, INTERVAL FLOOR(RAND() * 5) DAY);

        -- Gera UUID para pedido
        SET pedido_id = UUID();

        -- Insere pedido com valor_total = 0
        INSERT INTO pedidos (id, usuario_id, status, preco_total_registrado, data_criacao, data_atualizacao)
        VALUES (pedido_id, usuario_id, status_pedido, 0, data_criacao_pedido, data_atualizacao_pedido);

        -- Cria tabela temporária com TODOS os produtos, mas renomeando a coluna para temp_id
        DROP TEMPORARY TABLE IF EXISTS temp_produtos_disponiveis;
        CREATE TEMPORARY TABLE temp_produtos_disponiveis AS
        SELECT id AS temp_id, preco
        FROM produtos;

        -- Calcula quantos itens serão adicionados
        SELECT COUNT(*) INTO total_produtos_disponiveis FROM temp_produtos_disponiveis;
        SET num_itens = LEAST(FLOOR(RAND() * 3) + 1, total_produtos_disponiveis);

        SET valor_total_pedido = 0;

        pedido_loop: WHILE num_itens > 0 DO
            -- Sorteia um produto da tabela temporária
            SELECT temp_id, preco INTO produto_id, preco_unitario
            FROM temp_produtos_disponiveis
            ORDER BY RAND()
            LIMIT 1;

            -- Se não achou produto, sai do loop
            IF produto_id IS NULL THEN
                LEAVE pedido_loop;
            END IF;

            -- Remove o produto sorteado
            DELETE FROM temp_produtos_disponiveis
            WHERE temp_id = produto_id
            LIMIT 1;

            -- Define quantidade do item
            SET quantidade = FLOOR(RAND() * 5) + 1;
            SET preco_total = preco_unitario * quantidade;

            -- Insere item do pedido
            INSERT INTO pedido_itens (id, pedido_id, produto_id, quantidade, preco_unitario_registrado , preco_total_registrado)
            VALUES (UUID(), pedido_id, produto_id, quantidade, preco_unitario, preco_total);

            -- Atualiza valor total
            SET valor_total_pedido = valor_total_pedido + preco_total;

            SET num_itens = num_itens - 1;
        END WHILE pedido_loop;

        -- Se o valor for 0, exclui o pedido
        IF valor_total_pedido > 0 THEN
            UPDATE pedidos
            SET preco_total_registrado = valor_total_pedido
            WHERE id = pedido_id;
		ELSE
            DELETE FROM pedidos
            WHERE id = pedido_id
            LIMIT 1;
        END IF;

        DROP TEMPORARY TABLE IF EXISTS temp_produtos_disponiveis;

        SET i = i + 1;
    END WHILE;
END$$
DELIMITER ;

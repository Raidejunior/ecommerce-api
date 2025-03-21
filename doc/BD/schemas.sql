-- Criação do banco de dados
CREATE DATABASE ecommerce;
USE ecommerce;

-- Tabela de usuários
CREATE TABLE usuarios (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    nome_usuario VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    perfil ENUM('ADMIN', 'USER') NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de produtos
CREATE TABLE produtos (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    preco DECIMAL(10,2) NOT NULL,
    categoria ENUM('ELETRONICOS', 'ROUPAS', 'LIVROS', 'ALIMENTOS', 'MOVEIS', 'ESPORTES') NOT NULL,
    quantidade_estoque INT NOT NULL CHECK (quantidade_estoque >= 0),
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Índices para a tabela de produtos
CREATE INDEX idx_produtos_categoria ON produtos(categoria);
CREATE INDEX idx_produtos_preco ON produtos(preco);
CREATE INDEX idx_produtos_nome ON produtos(nome); -- Índice para nome do produto

-- Tabela de pedidos
CREATE TABLE pedidos (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    usuario_id CHAR(36) NOT NULL,
    status ENUM('PENDENTE', 'PAGO', 'CANCELADO') DEFAULT 'PENDENTE',
    preco_total_registrado DECIMAL(10,2) NOT NULL DEFAULT 0,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- Índices para a tabela de pedidos
CREATE INDEX idx_pedidos_usuario_id ON pedidos(usuario_id);
CREATE INDEX idx_pedidos_data_criacao ON pedidos(data_criacao);

-- Tabela de itens do pedido
CREATE TABLE pedido_itens (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    pedido_id CHAR(36) NOT NULL,
    produto_id CHAR(36) NOT NULL,
    quantidade INT NOT NULL CHECK (quantidade > 0),
    preco_total_registrado DECIMAL(10,2) NOT NULL,
    preco_unitario_registrado DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (pedido_id) REFERENCES pedidos(id) ON DELETE CASCADE,
    FOREIGN KEY (produto_id) REFERENCES produtos(id)
);

-- Índices para a tabela de itens do pedido
CREATE INDEX idx_pedido_itens_pedido_id ON pedido_itens(pedido_id);
CREATE INDEX idx_pedido_itens_produto_id ON pedido_itens(produto_id);

INSERT INTO usuarios (nome_usuario, senha, perfil, data_criacao) VALUES
('admin', '$2a$10$J4R8xpauQph86yLcFx943uZqHbVmT1tN56kBj5ELpVrQmWQRgDvCG', 'ADMIN', NOW()),
('user1', '$2a$10$J4R8xpauQph86yLcFx943uZqHbVmT1tN56kBj5ELpVrQmWQRgDvCG', 'USER', NOW()),
('user2', '$2a$10$J4R8xpauQph86yLcFx943uZqHbVmT1tN56kBj5ELpVrQmWQRgDvCG', 'USER', NOW()),
('user3', '$2a$10$J4R8xpauQph86yLcFx943uZqHbVmT1tN56kBj5ELpVrQmWQRgDvCG', 'USER', NOW()),
('user4', '$2a$10$J4R8xpauQph86yLcFx943uZqHbVmT1tN56kBj5ELpVrQmWQRgDvCG', 'USER', NOW()),
('user5', '$2a$10$J4R8xpauQph86yLcFx943uZqHbVmT1tN56kBj5ELpVrQmWQRgDvCG', 'USER', NOW()),
('user6', '$2a$10$J4R8xpauQph86yLcFx943uZqHbVmT1tN56kBj5ELpVrQmWQRgDvCG', 'USER', NOW()),
('user7', '$2a$10$J4R8xpauQph86yLcFx943uZqHbVmT1tN56kBj5ELpVrQmWQRgDvCG', 'USER', NOW()),
('user8', '$2a$10$J4R8xpauQph86yLcFx943uZqHbVmT1tN56kBj5ELpVrQmWQRgDvCG', 'USER', NOW()),
('user9', '$2a$10$J4R8xpauQph86yLcFx943uZqHbVmT1tN56kBj5ELpVrQmWQRgDvCG', 'USER', NOW()),
('user10', '$2a$10$J4R8xpauQph86yLcFx943uZqHbVmT1tN56kBj5ELpVrQmWQRgDvCG', 'USER', NOW());


INSERT INTO produtos (nome, descricao, preco, categoria, quantidade_estoque, data_criacao, data_atualizacao) VALUES
('Smartphone X', 'Smartphone de última geração com câmera avançada.', 2999.99, 'ELETRONICOS', 50, NOW(), NOW()),
('Camiseta Polo', 'Camiseta polo de algodão, disponível em várias cores.', 89.90, 'ROUPAS', 100, NOW(), NOW()),
('Livro: Código Limpo', 'Livro sobre boas práticas de desenvolvimento de software.', 149.99, 'LIVROS', 30, NOW(), NOW()),
('Chocolate Artesanal', 'Chocolate 70% cacau feito artesanalmente.', 24.90, 'ALIMENTOS', 200, NOW(), NOW()),
('Sofá Retrátil', 'Sofá retrátil e reclinável para sala de estar.', 2499.00, 'MOVEIS', 10, NOW(), NOW()),
('Bola de Futebol', 'Bola oficial para prática de futebol de campo.', 129.90, 'ESPORTES', 75, NOW(), NOW());
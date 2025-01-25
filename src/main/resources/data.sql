-- Tabela de Cliente
INSERT INTO cliente (cpf, nome) VALUES ('12345678901', 'João Silva');
INSERT INTO cliente (cpf, nome) VALUES ('98765432100', 'Maria Oliveira');

-- Tabela de Endereço
INSERT INTO endereco (logradouro, numero, complemento, bairro, cidade, estado, cliente_cpf)
VALUES ('Rua das Flores', '123', 'Apto 101', 'Jardim', 'São Paulo', 'SP', '12345678901');
INSERT INTO endereco (logradouro, numero, complemento, bairro, cidade, estado, cliente_cpf)
VALUES ('Avenida Brasil', '456', '', 'Centro', 'Rio de Janeiro', 'RJ', '98765432100');

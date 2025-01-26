-- Tabela de Cliente
INSERT INTO cliente (cpf, nome) VALUES ('12345678901', 'João Silva');
INSERT INTO cliente (cpf, nome) VALUES ('98765432100', 'Maria Oliveira');

-- Tabela de Endereço
INSERT INTO endereco (cep, logradouro, numero, complemento, bairro, cidade, estado, cliente_cpf)
VALUES ('08676320','Rua das Flores', '123', 'Apto 101', 'Jardim', 'São Paulo', 'SP', '12345678901');
INSERT INTO endereco (cep, logradouro, numero, complemento, bairro, cidade, estado, cliente_cpf)
VALUES ('08765400','Avenida Brasil', '456', '', 'Centro', 'Rio de Janeiro', 'RJ', '98765432100');

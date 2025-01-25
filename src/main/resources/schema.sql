CREATE TABLE cliente (
                         cpf VARCHAR(11) PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL
);

CREATE TABLE endereco (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          logradouro VARCHAR(255),
                          cep VARCHAR(10),
                          numero INTEGER,
                          complemento VARCHAR(255),
                          bairro VARCHAR(100),
                          cidade VARCHAR(100),
                          estado VARCHAR(2),
                          cliente_cpf VARCHAR(11),
                          CONSTRAINT fk_cliente FOREIGN KEY (cliente_cpf) REFERENCES cliente(cpf)
);
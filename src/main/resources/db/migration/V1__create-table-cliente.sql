CREATE TABLE cliente(
    codigo BIGSERIAL NOT NULL,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    idade INTEGER NOT NULL CHECK (idade >= 1 AND idade <= 99), -- aceita somente valores entre 1 e 100
    ativo BOOLEAN NOT NULL,
    PRIMARY KEY(codigo)
)
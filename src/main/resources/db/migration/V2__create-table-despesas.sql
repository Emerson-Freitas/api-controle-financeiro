CREATE TABLE despesas(
id bigint NOT NULL AUTO_INCREMENT,
descricao varchar(255) NOT NULL,
valor DECIMAL(19,2) NOT NULL,
data DATE NOT NULL,

PRIMARY KEY(id)
);
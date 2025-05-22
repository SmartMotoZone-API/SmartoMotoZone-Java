-- ZONAS
INSERT INTO ZONA (codigo, descricao) VALUES ('Z01', 'Zona Central');
INSERT INTO ZONA (codigo, descricao) VALUES ('Z02', 'Zona Norte');
INSERT INTO ZONA (codigo, descricao) VALUES ('Z03', 'Zona Sul');

-- MOTOS
INSERT INTO MOTO (zona_id, modelo, placa, status) VALUES (1, 'Honda CG 160', 'ABC1234', 'DISPONIVEL');
INSERT INTO MOTO (zona_id, modelo, placa, status) VALUES (2, 'Yamaha Fazer 250', 'XYZ5678', 'EM_USO');
INSERT INTO MOTO (zona_id, modelo, placa, status) VALUES (3, 'Suzuki Intruder', 'DEF9876', 'MANUTENCAO');

-- FUNCIONÁRIOS
INSERT INTO FUNCIONARIO (cargo, nome) VALUES ('Gestor', 'Carlos Silva');

-- USUÁRIOS
INSERT INTO USUARIO (email, login, nome, perfil, senha)
VALUES ('admin@smartmoto.com', 'admin', 'Administrador', 'ADMIN', '123456');

-- MOVIMENTAÇÕES
INSERT INTO MOVIMENTACAO (data_hora, moto_id, zona_destino_id, zona_origem_id, descricao)
VALUES (CURRENT_TIMESTAMP(), 1, 2, 1, 'Transferencia para manutencao');

INSERT INTO MOVIMENTACAO (data_hora, moto_id, zona_destino_id, zona_origem_id, descricao)
VALUES (CURRENT_TIMESTAMP(), 2, 3, 2, 'Recolocacao operacional');

INSERT INTO MOVIMENTACAO (data_hora, moto_id, zona_destino_id, zona_origem_id, descricao)
VALUES (CURRENT_TIMESTAMP(), 3, 1, 3, 'Retorno pos manutencao');

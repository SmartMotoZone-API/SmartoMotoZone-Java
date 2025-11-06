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
-- IMPORTANTE: A senha precisa ser "hasheada" para o Spring Security
-- A senha '123456' hasheada (BCrypt) é: $2a$10$T/DL.1qMssS6yv8m/c.S.Oq0/D.E.E.q2sJz.f.m9g.3.1.Zp.k3e
INSERT INTO USUARIO (email, login, nome, perfil, senha)
VALUES ('admin@smartmoto.com', 'admin', 'Administrador', 'ADMIN', '$2a$10$T/DL.1qMssS6yv8m/c.S.Oq0/D.E.E.q2sJz.f.m9g.3.1.Zp.k3e');

-- MOVIMENTAÇÕES
INSERT INTO MOVIMENTACAO (data_hora, moto_id, zona_destino_id, zona_origem_id, descricao)
VALUES (CURRENT_TIMESTAMP(), 1, 2, 1, 'Transferencia para manutencao');

INSERT INTO MOVIMENTACAO (data_hora, moto_id, zona_destino_id, zona_origem_id, descricao)
VALUES (CURRENT_TIMESTAMP(), 2, 3, 2, 'Recolocacao operacional');

INSERT INTO MOVIMENTACAO (data_hora, moto_id, zona_destino_id, zona_origem_id, descricao)
VALUES (CURRENT_TIMESTAMP(), 3, 1, 3, 'Retorno pos manutencao');
-- Comunicado 1: Aluno Específico
INSERT INTO comunicado (id, conteudo, data_envio, titulo, remetente_coordenador_id, remetente_professor_id) 
VALUES (1, 'Lembrete sobre entrega do trabalho final.', NOW(), 'Entrega de Trabalho', NULL, '56789012345');
INSERT INTO comunicado_receptor_alunos (comunicado_id, aluno_id) 
VALUES (1, 15);

-- Comunicado 2: Aluno Específico
INSERT INTO comunicado (id, conteudo, data_envio, titulo, remetente_coordenador_id, remetente_professor_id) 
VALUES (2, 'Atualização sobre notas do último simulado.', NOW(), 'Notas do Simulado', NULL, '89012345678');
INSERT INTO comunicado_receptor_alunos (comunicado_id, aluno_id) 
VALUES (2, 42);

-- Comunicado 3: Grupo de Alunos
INSERT INTO comunicado (id, conteudo, data_envio, titulo, remetente_coordenador_id, remetente_professor_id) 
VALUES (3, 'Parabéns pelo desempenho na Olimpíada de Matemática.', NOW(), 'Desempenho Excelente', '23456789012', NULL);
INSERT INTO comunicado_receptor_alunos (comunicado_id, aluno_id) 
VALUES (3, 4), (3, 8), (3, 12), (3, 16);

-- Comunicado 4: Turma Inteira (Turma 1)
INSERT INTO comunicado (id, conteudo, data_envio, titulo, remetente_coordenador_id, remetente_professor_id) 
VALUES (4, 'Prova final agendada para o dia 15/12.', NOW(), 'Prova Final', NULL, '67890123456');
INSERT INTO comunicado_receptor_alunos (comunicado_id, aluno_id) 
VALUES 
(4, 1), (4, 2), (4, 4), (4, 5), (4, 8), 
(4, 9), (4, 10), (4, 12), (4, 13), (4, 15), 
(4, 16), (4, 21), (4, 22), (4, 23), (4, 24), 
(4, 33), (4, 34), (4, 35), (4, 36), (4, 37), 
(4, 47), (4, 48), (4, 56), (4, 57), (4, 58);

-- Comunicado 5: Turma Inteira (Turma 2)
INSERT INTO comunicado (id, conteudo, data_envio, titulo, remetente_coordenador_id, remetente_professor_id) 
VALUES (5, 'Entrega de atividades pendentes até o final do mês.', NOW(), 'Atividades Pendentes', '12345678901', NULL);
INSERT INTO comunicado_receptor_alunos (comunicado_id, aluno_id) 
VALUES 
(5, 3), (5, 6), (5, 7), (5, 11), (5, 14), 
(5, 17), (5, 18), (5, 19), (5, 20), (5, 25), 
(5, 26), (5, 27), (5, 28), (5, 29), (5, 30), 
(5, 31), (5, 32), (5, 38), (5, 39), (5, 42);

-- Comunicado 6: Turma Inteira (Turma 3)
INSERT INTO comunicado (id, conteudo, data_envio, titulo, remetente_coordenador_id, remetente_professor_id) 
VALUES (6, 'Simulado geral marcado para o próximo sábado.', NOW(), 'Simulado Geral', NULL, '78901234567');
INSERT INTO comunicado_receptor_alunos (comunicado_id, aluno_id) 
VALUES 
(6, 40), (6, 41), (6, 43), (6, 44), (6, 51), 
(6, 52), (6, 59), (6, 66), (6, 67), (6, 69), 
(6, 85), (6, 89), (6, 97);

-- Comunicado 7: Grupo de Alunos (Diversificado)
INSERT INTO comunicado (id, conteudo, data_envio, titulo, remetente_coordenador_id, remetente_professor_id) 
VALUES (7, 'Participação na Feira de Ciências.', NOW(), 'Feira de Ciências', '34567890123', NULL);
INSERT INTO comunicado_receptor_alunos (comunicado_id, aluno_id) 
VALUES (7, 3), (7, 15), (7, 42), (7, 60), (7, 89);

-- Comunicado 8: Aluno Específico
INSERT INTO comunicado (id, conteudo, data_envio, titulo, remetente_coordenador_id, remetente_professor_id) 
VALUES (8, 'Confirmação de presença no evento cultural.', NOW(), 'Evento Cultural', NULL, '98765432101');
INSERT INTO comunicado_receptor_alunos (comunicado_id, aluno_id) 
VALUES (8, 98);

-- Comunicado 9: Múltiplas Turmas
INSERT INTO comunicado (id, conteudo, data_envio, titulo, remetente_coordenador_id, remetente_professor_id) 
VALUES (9, 'Aviso: mudanças no calendário acadêmico.', NOW(), 'Mudanças no Calendário', '12345678901', NULL);
INSERT INTO comunicado_receptor_alunos (comunicado_id, aluno_id) 
VALUES 
(9, 1), (9, 2), (9, 3), (9, 6), (9, 8), 
(9, 40), (9, 41), (9, 43), (9, 44), (9, 51);

-- Comunicado 10: Aluno Específico
INSERT INTO comunicado (id, conteudo, data_envio, titulo, remetente_coordenador_id, remetente_professor_id) 
VALUES (10, 'Agendamento de reforço escolar.', NOW(), 'Reforço Escolar', NULL, '56789012345');
INSERT INTO comunicado_receptor_alunos (comunicado_id, aluno_id) 
VALUES (10, 25);

-- Comunicado 11: Grupo de Alunos
INSERT INTO comunicado (id, conteudo, data_envio, titulo, remetente_coordenador_id, remetente_professor_id) 
VALUES (11, 'Preparação para o torneio de debates.', NOW(), 'Torneio de Debates', '23456789012', NULL);
INSERT INTO comunicado_receptor_alunos (comunicado_id, aluno_id) 
VALUES (11, 16), (11, 22), (11, 35), (11, 41);

-- Comunicado 12: Turma Inteira (Turma 1)
INSERT INTO comunicado (id, conteudo, data_envio, titulo, remetente_coordenador_id, remetente_professor_id) 
VALUES (12, 'Entrega do relatório de estágio.', NOW(), 'Relatório de Estágio', NULL, '67890123456');
INSERT INTO comunicado_receptor_alunos (comunicado_id, aluno_id) 
VALUES 
(12, 1), (12, 2), (12, 4), (12, 5), (12, 8), 
(12, 9), (12, 10), (12, 12), (12, 13), (12, 15), 
(12, 16), (12, 21), (12, 22), (12, 23), (12, 24), 
(12, 33), (12, 34), (12, 35), (12, 36), (12, 37), 
(12, 47), (12, 48), (12, 56), (12, 57), (12, 58);

-- Comunicado 13: Turma Inteira (Turma 2)
INSERT INTO comunicado (id, conteudo, data_envio, titulo, remetente_coordenador_id, remetente_professor_id) 
VALUES (13, 'Aviso de reposição de aulas.', NOW(), 'Reposição de Aulas', NULL, '78901234567');
INSERT INTO comunicado_receptor_alunos (comunicado_id, aluno_id) 
VALUES 
(13, 3), (13, 6), (13, 7), (13, 11), (13, 14), 
(13, 17), (13, 18), (13, 19), (13, 20), (13, 25), 
(13, 26), (13, 27), (13, 28), (13, 29), (13, 30), 
(13, 31), (13, 32), (13, 38), (13, 39), (13, 42);

-- Comunicado 14: Turma Inteira (Turma 3)
INSERT INTO comunicado (id, conteudo, data_envio, titulo, remetente_coordenador_id, remetente_professor_id) 
VALUES (14, 'Resultados do simulado geral.', NOW(), 'Resultados do Simulado', NULL, '90123456789');
INSERT INTO comunicado_receptor_alunos (comunicado_id, aluno_id) 
VALUES 
(14, 40), (14, 41), (14, 43), (14, 44), (14, 51), 
(14, 52), (14, 59), (14, 66), (14, 67), (14, 69), 
(14, 85), (14, 89), (14, 97);

-- Comunicado 15: Múltiplas Turmas
INSERT INTO comunicado (id, conteudo, data_envio, titulo, remetente_coordenador_id, remetente_professor_id) 
VALUES (15, 'Mudanças no cronograma de provas.', NOW(), 'Cronograma de Provas', '34567890123', NULL);
INSERT INTO comunicado_receptor_alunos (comunicado_id, aluno_id) 
VALUES 
(15, 1), (15, 3), (15, 8), (15, 40), (15, 44), 
(15, 25), (15, 67), (15, 85), (15, 41), (15, 11);

-- Comunicado 16: Aluno Específico
INSERT INTO comunicado (id, conteudo, data_envio, titulo, remetente_coordenador_id, remetente_professor_id) 
VALUES (16, 'Notificação de atrasos na entrega de atividades.', NOW(), 'Atrasos em Atividades', NULL, '98765432101');
INSERT INTO comunicado_receptor_alunos (comunicado_id, aluno_id) 
VALUES (16, 97);

-- Comunicado 17: Grupo de Alunos
INSERT INTO comunicado (id, conteudo, data_envio, titulo, remetente_coordenador_id, remetente_professor_id) 
VALUES (17, 'Convocação para ensaio da apresentação cultural.', NOW(), 'Apresentação Cultural', NULL, '56789012345');
INSERT INTO comunicado_receptor_alunos (comunicado_id, aluno_id) 
VALUES (17, 52), (17, 59), (17, 66), (17, 89);

-- Comunicado 18: Turma Inteira (Turma 2)
INSERT INTO comunicado (id, conteudo, data_envio, titulo, remetente_coordenador_id, remetente_professor_id) 
VALUES (18, 'Lembrete: inscrições para a maratona de programação.', NOW(), 'Maratona de Programação', '12345678901', NULL);
INSERT INTO comunicado_receptor_alunos (comunicado_id, aluno_id) 
VALUES 
(18, 3), (18, 6), (18, 7), (18, 11), (18, 14), 
(18, 17), (18, 18), (18, 19), (18, 20), (18, 25);

-- Comunicado 19: Múltiplas Turmas
INSERT INTO comunicado (id, conteudo, data_envio, titulo, remetente_coordenador_id, remetente_professor_id) 
VALUES (19, 'Divulgação dos horários das monitorias.', NOW(), 'Horários das Monitorias', NULL, '67890123456');
INSERT INTO comunicado_receptor_alunos (comunicado_id, aluno_id) 
VALUES 
(19, 1), (19, 3), (19, 8), (19, 40), (19, 44), 
(19, 25), (19, 67), (19, 85), (19, 41), (19, 11);

-- Comunicado 20: Grupo de Alunos
INSERT INTO comunicado (id, conteudo, data_envio, titulo, remetente_coordenador_id, remetente_professor_id) 
VALUES (20, 'Agradecimento pela participação no evento esportivo.', NOW(), 'Evento Esportivo', NULL, '89012345678');
INSERT INTO comunicado_receptor_alunos (comunicado_id, aluno_id) 
VALUES (20, 12), (20, 24), (20, 36), (20, 48);

-- Comunicado 21: Aluno Específico
INSERT INTO comunicado (id, conteudo, data_envio, titulo, remetente_coordenador_id, remetente_professor_id) 
VALUES (21, 'Notificação de ausência em atividades avaliativas.', NOW(), 'Ausência em Atividades', NULL, '56789012345');
INSERT INTO comunicado_receptor_alunos (comunicado_id, aluno_id) 
VALUES (21, 45);

-- Comunicado 22: Aluno Específico
INSERT INTO comunicado (id, conteudo, data_envio, titulo, remetente_coordenador_id, remetente_professor_id) 
VALUES (22, 'Convite para participar do grupo de estudo.', NOW(), 'Convite para Grupo de Estudo', NULL, '89012345678');
INSERT INTO comunicado_receptor_alunos (comunicado_id, aluno_id) 
VALUES (22, 33);

-- Comunicado 23: Grupo de Alunos
INSERT INTO comunicado (id, conteudo, data_envio, titulo, remetente_coordenador_id, remetente_professor_id) 
VALUES (23, 'Lembrete: entrega do projeto de pesquisa.', NOW(), 'Entrega de Projeto', '23456789012', NULL);
INSERT INTO comunicado_receptor_alunos (comunicado_id, aluno_id) 
VALUES (23, 11), (23, 13), (23, 17), (23, 21);

-- Comunicado 24: Turma Inteira (Turma 1)
INSERT INTO comunicado (id, conteudo, data_envio, titulo, remetente_coordenador_id, remetente_professor_id) 
VALUES (24, 'Horário alterado para as aulas de Matemática.', NOW(), 'Alteração de Horário', '34567890123', NULL);
INSERT INTO comunicado_receptor_alunos (comunicado_id, aluno_id) 
VALUES 
(24, 1), (24, 2), (24, 4), (24, 5), (24, 8), 
(24, 9), (24, 10), (24, 12), (24, 13), (24, 15), 
(24, 16), (24, 21), (24, 22), (24, 23), (24, 24), 
(24, 33), (24, 34), (24, 35), (24, 36), (24, 37);

-- Comunicado 25: Turma Inteira (Turma 2)
INSERT INTO comunicado (id, conteudo, data_envio, titulo, remetente_coordenador_id, remetente_professor_id) 
VALUES (25, 'Entrega de relatório da feira de ciências.', NOW(), 'Relatório da Feira', NULL, '67890123456');
INSERT INTO comunicado_receptor_alunos (comunicado_id, aluno_id) 
VALUES 
(25, 3), (25, 6), (25, 7), (25, 11), (25, 14), 
(25, 17), (25, 18), (25, 19), (25, 20), (25, 25), 
(25, 26), (25, 27), (25, 28), (25, 29), (25, 30);

-- Comunicado 26: Turma Inteira (Turma 3)
INSERT INTO comunicado (id, conteudo, data_envio, titulo, remetente_coordenador_id, remetente_professor_id) 
VALUES (26, 'Preparação para a semana de artes.', NOW(), 'Semana de Artes', NULL, '78901234567');
INSERT INTO comunicado_receptor_alunos (comunicado_id, aluno_id) 
VALUES 
(26, 40), (26, 41), (26, 43), (26, 44), (26, 51), 
(26, 52), (26, 59), (26, 66), (26, 67), (26, 69), 
(26, 85), (26, 89), (26, 97);

-- Comunicado 27: Múltiplas Turmas
INSERT INTO comunicado (id, conteudo, data_envio, titulo, remetente_coordenador_id, remetente_professor_id) 
VALUES (27, 'Aviso: inscrições abertas para o campeonato de xadrez.', NOW(), 'Campeonato de Xadrez', '12345678901', NULL);
INSERT INTO comunicado_receptor_alunos (comunicado_id, aluno_id) 
VALUES 
(27, 1), (27, 3), (27, 40), (27, 43), (27, 52), 
(27, 66), (27, 67), (27, 11), (27, 19), (27, 85);

-- Comunicado 28: Grupo de Alunos
INSERT INTO comunicado (id, conteudo, data_envio, titulo, remetente_coordenador_id, remetente_professor_id) 
VALUES (28, 'Parabéns pelo desempenho na prova de Física.', NOW(), 'Desempenho na Prova', NULL, '56789012345');
INSERT INTO comunicado_receptor_alunos (comunicado_id, aluno_id) 
VALUES (28, 8), (28, 22), (28, 35), (28, 41);

-- Comunicado 29: Aluno Específico
INSERT INTO comunicado (id, conteudo, data_envio, titulo, remetente_coordenador_id, remetente_professor_id) 
VALUES (29, 'Lembrete de inscrição para o vestibular.', NOW(), 'Inscrição no Vestibular', NULL, '89012345678');
INSERT INTO comunicado_receptor_alunos (comunicado_id, aluno_id) 
VALUES (29, 98);

-- Comunicado 30: Grupo de Alunos
INSERT INTO comunicado (id, conteudo, data_envio, titulo, remetente_coordenador_id, remetente_professor_id) 
VALUES (30, 'Revisão de conteúdos para o simulado.', NOW(), 'Revisão de Simulado', '34567890123', NULL);
INSERT INTO comunicado_receptor_alunos (comunicado_id, aluno_id) 
VALUES (30, 9), (30, 12), (30, 18), (30, 21);

-- Comunicado 31: Turma Inteira (Turma 1)
INSERT INTO comunicado (id, conteudo, data_envio, titulo, remetente_coordenador_id, remetente_professor_id) 
VALUES (31, 'Convocação para reunião de pais e mestres.', NOW(), 'Reunião de Pais', NULL, '67890123456');
INSERT INTO comunicado_receptor_alunos (comunicado_id, aluno_id) 
VALUES 
(31, 1), (31, 2), (31, 4), (31, 5), (31, 8), 
(31, 9), (31, 10), (31, 12), (31, 13), (31, 15);

-- Comunicado 32: Múltiplas Turmas
INSERT INTO comunicado (id, conteudo, data_envio, titulo, remetente_coordenador_id, remetente_professor_id) 
VALUES (32, 'Horário especial para o evento cultural.', NOW(), 'Evento Cultural', '12345678901', NULL);
INSERT INTO comunicado_receptor_alunos (comunicado_id, aluno_id) 
VALUES 
(32, 40), (32, 41), (32, 44), (32, 51), (32, 11), 
(32, 19), (32, 29), (32, 35), (32, 36), (32, 89);

-- Comunicado 33: Grupo de Alunos
INSERT INTO comunicado (id, conteudo, data_envio, titulo, remetente_coordenador_id, remetente_professor_id) 
VALUES (33, 'Entrega de notas do simulado de História.', NOW(), 'Notas do Simulado', NULL, '90123456789');
INSERT INTO comunicado_receptor_alunos (comunicado_id, aluno_id) 
VALUES (33, 3), (33, 7), (33, 17), (33, 25);

-- Comunicado 34: Aluno Específico
INSERT INTO comunicado (id, conteudo, data_envio, titulo, remetente_coordenador_id, remetente_professor_id) 
VALUES (34, 'Notificação de atraso na entrega de trabalho.', NOW(), 'Atraso em Trabalho', NULL, '56789012345');
INSERT INTO comunicado_receptor_alunos (comunicado_id, aluno_id) 
VALUES (34, 42);

-- Comunicado 35: Grupo de Alunos
INSERT INTO comunicado (id, conteudo, data_envio, titulo, remetente_coordenador_id, remetente_professor_id) 
VALUES (35, 'Parabéns pelo desempenho no campeonato esportivo.', NOW(), 'Desempenho Esportivo', NULL, '67890123456');
INSERT INTO comunicado_receptor_alunos (comunicado_id, aluno_id) 
VALUES (35, 59), (35, 66), (35, 67), (35, 85);

-- Comunicado 36: Turma Inteira (Turma 2)
INSERT INTO comunicado (id, conteudo, data_envio, titulo, remetente_coordenador_id, remetente_professor_id) 
VALUES (36, 'Prova de recuperação agendada.', NOW(), 'Prova de Recuperação', '23456789012', NULL);
INSERT INTO comunicado_receptor_alunos (comunicado_id, aluno_id) 
VALUES 
(36, 3), (36, 6), (36, 7), (36, 11), (36, 14);


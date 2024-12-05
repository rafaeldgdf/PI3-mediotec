-- ============================
-- INSERÇÕES DE COORDENAÇÕES
-- ============================
INSERT INTO coordenacao (id_coordenacao, nome, descricao) VALUES
(1, 'Coordenação de Ciências Humanas', 'Gerencia disciplinas das Ciências Humanas e Sociais'),
(2, 'Coordenação de Exatas e Tecnológicas', 'Gerencia disciplinas das Exatas e Tecnológicas');

-- ============================
-- INSERÇÕES DE ENDEREÇOS PARA COORDENAÇÕES
-- ============================
INSERT INTO endereco (rua, numero, bairro, cidade, estado, cep, aluno_id, coordenador_id, professor_id, coordenacao_id) VALUES
('Rua Conselheiro Rosa e Silva', '1000', 'Graças', 'Recife', 'PE', '52020000', NULL, NULL, NULL, 1), -- Coordenação de Ciências Humanas
('Avenida Caxangá', '2000', 'Cordeiro', 'Recife', 'PE', '50711000', NULL, NULL, NULL, 2); -- Coordenação de Exatas e Tecnológicas

-- ============================
-- INSERÇÕES DE TELEFONES PARA COORDENAÇÕES
-- ============================
INSERT INTO telefone (ddd, numero, aluno_id, coordenador_id, professor_id, coordenacao_id, responsavel_id) VALUES
('81', '988110001', NULL, NULL, NULL, 1, NULL), -- Telefone da Coordenação de Ciências Humanas
('81', '988220002', NULL, NULL, NULL, 2, NULL); -- Telefone da Coordenação de Exatas e Tecnológicas


-- ============================
-- INSERÇÕES DE COORDENADORES
-- ============================
INSERT INTO coordenador (cpf, nome, ultimo_nome, genero, data_nascimento, email, senha, status, id_coordenacao) VALUES
('12345678901', 'Morrissey', 'Smith', 'Masculino', '1965-05-22', 'morrissey.smith@coordenacao.com', 'morrissey.smith@coordenacao.com', 1, 1),
('23456789012', 'Eddie', 'Vedder', 'Masculino', '1964-12-23', 'eddie.vedder@coordenacao.com', 'eddie.vedder@coordenacao.com', 1, 2),
('34567890123', 'Thom', 'Yorke', 'Masculino', '1968-10-07', 'thom.yorke@coordenacao.com', 'thom.yorke@coordenacao.com', 1, 1),
('1122', 'Rafael', 'Oliveira', 'Masculino', '1998-10-07', 'r@r.com', '123', 1, 2);

-- ============================
-- INSERÇÕES DE PROFESSORES
-- ============================
INSERT INTO professor (cpf, nome, ultimo_nome, genero, data_nascimento, email, senha, status, id_coordenacao) VALUES
('45678901234', 'Liam', 'Gallagher', 'Masculino', '1972-09-21', 'liam.gallagher@prof.com', 'liam.gallagher@prof.com', 1, 1),
('56789012345', 'Noel', 'Gallagher', 'Masculino', '1967-05-29', 'noel.gallagher@prof.com', 'noel.gallagher@prof.com', 1, 1),
('67890123456', 'Brandon', 'Flowers', 'Masculino', '1981-06-21', 'brandon.flowers@prof.com', 'brandon.flowers@prof.com', 1, 1),
('78901234567', 'Alex', 'Turner', 'Masculino', '1986-01-06', 'alex.turner@prof.com', 'alex.turner@prof.com', 1, 2),
('89012345678', 'Matt', 'Bellamy', 'Masculino', '1978-06-09', 'matt.bellamy@prof.com', 'matt.bellamy@prof.com', 1, 2),
('90123456789', 'Chris', 'Martin', 'Masculino', '1977-03-02', 'chris.martin@prof.com', 'chris.martin@prof.com', 1, 2),
('01234567890', 'Robert', 'Smith', 'Masculino', '1959-04-21', 'robert.smith@prof.com', 'robert.smith@prof.com', 1, 1),
('12345098765', 'Billy', 'Corgan', 'Masculino', '1967-03-17', 'billy.corgan@prof.com', 'billy.corgan@prof.com', 1, 2),
('98765432101', 'Jack', 'White', 'Masculino', '1975-07-09', 'jack.white@prof.com', 'jack.white@prof.com', 1, 2),
('19283746501', 'Kurt', 'Cobain', 'Masculino', '1967-02-20', 'kurt.cobain@prof.com', 'kurt.cobain@prof.com', 1, 1);

-- ============================
-- INSERÇÕES DE DISCIPLINAS
-- ============================
INSERT INTO disciplina (id_disciplina, nome, carga_horaria) VALUES
(1, 'Matemática', 60),
(2, 'Física', 60),
(3, 'Química', 60),
(4, 'Biologia', 60),
(5, 'Geografia', 60),
(6, 'História', 60),
(7, 'Filosofia', 60),
(8, 'Sociologia', 60),
(9, 'Inglês', 60),
(10, 'Português', 60),
(11, 'Arte', 60),
(12, 'Educação Física', 60),
(13, 'Música', 60),
(14, 'Cinema', 60),
(15, 'Tecnologia da Informação', 60),
(16, 'Robótica', 60),
(17, 'Astronomia', 60),
(18, 'Geometria', 60),
(19, 'Lógica de Programação', 60),
(20, 'Cálculo Diferencial', 60);

-- ============================
-- INSERÇÕES DE TURMAS
-- ============================
INSERT INTO turma (id_turma, nome, ano_escolar, ano_letivo, turno, id_coordenacao, status) VALUES
(1, 'Turma 1', '1º Ano', 2024, 'Manhã', 1, 1),
(2, 'Turma 2', '2º Ano', 2024, 'Tarde', 2, 1),
(3, 'Turma 3', '3º Ano', 2024, 'Noite', 1, 1);


-- ============================
-- INSERÇÕES DE ALUNOS
-- ============================
INSERT INTO aluno (matricula_aluno, nome, ultimo_nome, genero, data_nascimento, email, senha, cpf, status, coordenacao_id_coordenacao) VALUES
(1, 'Johnny', 'Marr', 'Masculino', '2006-02-13', 'johnny.marr@aluno.com', 'johnny.marr@aluno.com', '11122233344', 1, 1),
(2, 'Mary', 'Smith', 'Feminino', '2005-11-05', 'mary.smith@aluno.com', 'mary.smith@aluno.com', '11122233345', 1, 1),
(3, 'Michael', 'Gallagher', 'Masculino', '2006-06-09', 'michael.gallagher@aluno.com', 'michael.gallagher@aluno.com', '11122233346', 1, 2),
(4, 'Luana', 'Silva', 'Feminino', '2005-09-21', 'luana.silva@aluno.com', 'luana.silva@aluno.com', '11122233347', 1, 1),
(5, 'Tom', 'York', 'Masculino', '2004-10-07', 'tom.york@aluno.com', 'tom.york@aluno.com', '11122233348', 1, 1),
(6, 'Chris', 'Martins', 'Masculino', '2005-03-02', 'chris.martins@aluno.com', 'chris.martins@aluno.com', '11122233349', 1, 2),
(7, 'Fernanda', 'Vaz', 'Feminino', '2006-08-04', 'fernanda.vaz@aluno.com', 'fernanda.vaz@aluno.com', '11122233350', 1, 2),
(8, 'Janaina', 'Turner', 'Feminino', '2006-01-06', 'janaina.turner@aluno.com', 'janaina.turner@aluno.com', '11122233351', 1, 1),
(9, 'Matheus', 'Beltrame', 'Masculino', '2005-06-09', 'matheus.beltrame@aluno.com', 'matheus.beltrame@aluno.com', '11122233352', 1, 1),
(10, 'Brandon', 'Flores', 'Masculino', '2006-06-21', 'brandon.flores@aluno.com', 'brandon.flores@aluno.com', '11122233353', 1, 1),
(11, 'Joana', 'White', 'Feminino', '2004-07-09', 'joana.white@aluno.com', 'joana.white@aluno.com', '11122233354', 1, 2),
(12, 'Kurt', 'Brown', 'Masculino', '2005-02-20', 'kurt.brown@aluno.com', 'kurt.brown@aluno.com', '11122233355', 1, 1),
(13, 'Roberta', 'Smith', 'Feminino', '2004-04-21', 'roberta.smith2@aluno.com', 'roberta.smith2@aluno.com', '11122233356', 1, 1),
(14, 'Billy', 'Corgan', 'Masculino', '2006-03-17', 'billy.corgan@aluno.com', 'billy.corgan@aluno.com', '11122233357', 1, 2),
(15, 'Nick', 'Valensi', 'Masculino', '2004-01-16', 'nick.valensi@aluno.com', 'nick.valensi@aluno.com', '11122233358', 1, 1),
(16, 'Albert', 'Hammond', 'Masculino', '2006-04-09', 'albert.hammond@aluno.com', 'albert.hammond@aluno.com', '11122233359', 1, 1),
(17, 'Juliana', 'Casablancas', 'Feminino', '2005-06-23', 'juliana.casablancas@aluno.com', 'juliana.casablancas@aluno.com', '11122233360', 1, 2),
(18, 'George', 'Harrison', 'Masculino', '2005-07-20', 'george.harrison@aluno.com', 'george.harrison@aluno.com', '11122233361', 1, 2),
(19, 'Paulo', 'McCarthy', 'Masculino', '2006-01-18', 'paulo.mccarthy@aluno.com', 'paulo.mccarthy@aluno.com', '11122233362', 1, 2),
(20, 'John', 'Lemos', 'Masculino', '2006-10-09', 'john.lemos@aluno.com', 'john.lemos@aluno.com', '11122233363', 1, 2),
(21, 'Rita', 'Starr', 'Feminino', '2005-07-07', 'rita.starr@aluno.com', 'rita.starr@aluno.com', '11122233364', 1, 1),
(22, 'Fred', 'Mercurio', 'Masculino', '2006-09-05', 'fred.mercurio@aluno.com', 'fred.mercurio@aluno.com', '11122233365', 1, 1),
(23, 'Briana', 'May', 'Feminino', '2005-07-19', 'briana.may@aluno.com', 'briana.may@aluno.com', '11122233366', 1, 1),
(24, 'Roger', 'Taylor', 'Masculino', '2006-04-26', 'roger.taylor@aluno.com', 'roger.taylor@aluno.com', '11122233367', 1, 1),
(25, 'Davi', 'Bowie', 'Masculino', '2005-01-08', 'davi.bowie@aluno.com', 'davi.bowie@aluno.com', '11122233368', 1, 2),
(26, 'Mick', 'Jagger', 'Masculino', '2006-07-26', 'mick.jagger@aluno.com', 'mick.jagger@aluno.com', '11122233369', 1, 2),
(27, 'Keith', 'Richards', 'Masculino', '2005-12-18', 'keith.richards@aluno.com', 'keith.richards@aluno.com', '11122233370', 1, 2),
(28, 'Ronnie', 'Wood', 'Masculino', '2005-06-01', 'ronnie.wood@aluno.com', 'ronnie.wood@aluno.com', '11122233371', 1, 2),
(29, 'Charlene', 'Watts', 'Feminino', '2006-06-02', 'charlene.watts@aluno.com', 'charlene.watts@aluno.com', '11122233372', 1, 2),
(30, 'Philipe', 'Collins', 'Masculino', '2005-01-30', 'philipe.collins@aluno.com', 'philipe.collins@aluno.com', '11122233373', 1, 2),
(31, 'Peterson', 'Gabriel', 'Masculino', '2005-02-13', 'peterson.gabriel@aluno.com', 'peterson.gabriel@aluno.com', '11122233374', 1, 2),
(32, 'Estevão', 'Hackett', 'Masculino', '2006-12-02', 'estevao.hackett@aluno.com', 'estevao.hackett@aluno.com', '11122233375', 1, 2),
(33, 'Tony', 'Banks', 'Masculino', '2005-03-27', 'tony.banks@aluno.com', 'tony.banks@aluno.com', '11122233376', 1, 1),
(34, 'Michael', 'Rutherford', 'Masculino', '2005-10-02', 'michael.rutherford@aluno.com', 'michael.rutherford@aluno.com', '11122233377', 1, 1),
(35, 'Elliot', 'Smith', 'Masculino', '2005-08-06', 'elliot.smith@aluno.com', 'elliot.smith@aluno.com', '11122233378', 1, 1),
(36, 'Jefferson', 'Buckley', 'Masculino', '2006-11-17', 'jefferson.buckley@aluno.com', 'jefferson.buckley@aluno.com', '11122233379', 1, 1),
(37, 'Nickolas', 'Drake', 'Masculino', '2004-06-19', 'nickolas.drake@aluno.com', 'nickolas.drake@aluno.com', '11122233380', 1, 1),
(38, 'Thomas', 'Petty', 'Masculino', '2005-10-20', 'thomas.petty@aluno.com', 'thomas.petty@aluno.com', '11122233381', 1, 2),
(39, 'Estefânia', 'Nicks', 'Feminino', '2005-05-26', 'estefania.nicks@aluno.com', 'estefania.nicks@aluno.com', '11122233382', 1, 2),
(40, 'Joaquina', 'Jett', 'Feminino', '2005-06-29', 'joaquina.jett@aluno.com', 'joaquina.jett@aluno.com', '11122233383', 1, 1),
(41, 'Courtney', 'Love', 'Feminino', '2006-07-09', 'courtney.love@aluno.com', 'courtney.love@aluno.com', '11122233384', 1, 1),
(42, 'Patti', 'Smith', 'Feminino', '2005-08-18', 'patti.smith1@aluno.com', 'patti.smith@aluno.com', '11122233385', 1, 2),
(43, 'Karen', 'O', 'Feminino', '2006-05-17', 'karen.o@aluno.com', 'karen.o@aluno.com', '11122233386', 1, 1),
(44, 'Kim', 'Gordon', 'Feminino', '2005-03-15', 'kim.gordon1@aluno.com', 'kim.gordon@aluno.com', '11122233387', 1, 1),
(45, 'Shirley', 'Manson', 'Feminino', '2006-09-12', 'shirley.manson@aluno.com', 'shirley.manson@aluno.com', '11122233388', 1, 2),
(46, 'Debbie', 'Harry', 'Feminino', '2005-11-05', 'debbie.harry1@aluno.com', 'debbie.harry@aluno.com', '11122233389', 1, 2),
(47, 'Chrissie', 'Hynde', 'Feminino', '2006-06-01', 'chrissie.hynde1@aluno.com', 'chrissie.hynde@aluno.com', '11122233390', 1, 1),
(48, 'Florence', 'Welch', 'Feminino', '2005-07-25', 'florence.welch1@aluno.com', 'florence.welch@aluno.com', '11122233391', 1, 1),
(49, 'Bjork', 'Gudmundsdottir', 'Feminino', '2005-11-21', 'bjork.gudmundsdottir1@aluno.com', 'bjork.gudmundsdottir@aluno.com', '11122233392', 1, 2),
(50, 'Sinead', 'O’Connor', 'Feminino', '2004-12-08', 'sinead.oconnor1@aluno.com', 'sinead.oconnor@aluno.com', '11122233393', 1, 1),
(51, 'Annie', 'Lennox', 'Feminino', '2004-06-02', 'annie.lennox1@aluno.com', 'annie.lennox@aluno.com', '11122233394', 1, 1),
(52, 'Janis', 'Joplin', 'Feminino', '2005-01-19', 'janis.joplin1@aluno.com', 'janis.joplin@aluno.com', '11122233395', 1, 2),
(53, 'Diana', 'Ross', 'Feminino', '2006-04-26', 'diana.ross1@aluno.com', 'diana.ross@aluno.com', '11122233396', 1, 1),
(54, 'Aretha', 'Franklin', 'Feminino', '2005-03-25', 'aretha.franklin1@aluno.com', 'aretha.franklin@aluno.com', '11122233397', 1, 2),
(55, 'Whitney', 'Houston', 'Feminino', '2005-08-09', 'whitney.houston1@aluno.com', 'whitney.houston@aluno.com', '11122233398', 1, 2),
(56, 'Amy', 'Winehouse', 'Feminino', '2007-09-14', 'amy.winehouse1@aluno.com', 'amy.winehouse@aluno.com', '11122233399', 1, 1),
(57, 'Nina', 'Simone', 'Feminino', '2004-02-21', 'nina.simone1@aluno.com', 'nina.simone@aluno.com', '11122233400', 1, 1),
(58, 'Joni', 'Mitchell', 'Feminino', '2006-09-16', 'joni.mitchell1@aluno.com', 'joni.mitchell@aluno.com', '11122233401', 1, 1),
(59, 'Tina', 'Turner', 'Feminino', '2005-11-26', 'tina.turner1@aluno.com', 'tina.turner@aluno.com', '11122233402', 1, 1),
(60, 'Stevie', 'Ray Vaughan', 'Masculino', '2004-10-03', 'stevie.vaughan1@aluno.com', 'stevie.vaughan@aluno.com', '11122233403', 1, 2),
(61, 'Bruce', 'Springsteen', 'Masculino', '2005-09-23', 'bruce.springsteen1@aluno.com', 'bruce.springsteen@aluno.com', '11122233404', 1, 2),
(62, 'David', 'Gilmour', 'Masculino', '2006-03-06', 'david.gilmour1@aluno.com', 'david.gilmour@aluno.com', '11122233405', 1, 1),
(63, 'Roger', 'Waters', 'Masculino', '2005-02-16', 'roger.waters1@aluno.com', 'roger.waters@aluno.com', '11122233406', 1, 2),
(64, 'Nick', 'Mason', 'Masculino', '2006-08-27', 'nick.mason1@aluno.com', 'nick.mason@aluno.com', '11122233407', 1, 1),
(65, 'Rick', 'Wright', 'Masculino', '2005-12-04', 'rick.wright1@aluno.com', 'rick.wright@aluno.com', '11122233408', 1, 1),
(66, 'John', 'Deacon', 'Masculino', '2005-07-19', 'john.deacon1@aluno.com', 'john.deacon@aluno.com', '11122233409', 1, 1),
(67, 'Kerry', 'King', 'Masculino', '2005-12-05', 'kerry.king1@aluno.com', 'kerry.king@aluno.com', '11122233410', 1, 1),
(68, 'Tom', 'Araya', 'Masculino', '2005-09-13', 'tom.araya1@aluno.com', 'tom.araya@aluno.com', '11122233411', 1, 2),
(69, 'Jeff', 'Hanneman', 'Masculino', '2005-12-07', 'jeff.hanneman1@aluno.com', 'jeff.hanneman@aluno.com', '11122233412', 1, 1),
(70, 'Dave', 'Lombardo', 'Masculino', '2005-10-11', 'dave.lombardo1@aluno.com', 'dave.lombardo@aluno.com', '11122233413', 1, 2),
(71, 'Ozzy', 'Osbourne', 'Masculino', '2005-12-03', 'ozzy.osbourne1@aluno.com', 'ozzy.osbourne@aluno.com', '11122233414', 1, 2),
(72, 'Tony', 'Iommi', 'Masculino', '2005-09-06', 'tony.iommi1@aluno.com', 'tony.iommi@aluno.com', '11122233415', 1, 1),
(73, 'Geezer', 'Butler', 'Masculino', '2005-10-20', 'geezer.butler1@aluno.com', 'geezer.butler@aluno.com', '11122233416', 1, 1),
(74, 'Bill', 'Ward', 'Masculino', '2005-11-25', 'bill.ward1@aluno.com', 'bill.ward@aluno.com', '11122233417', 1, 2),
(75, 'Robert', 'Plant', 'Masculino', '2005-08-10', 'robert.plant1@aluno.com', 'robert.plant@aluno.com', '11122233418', 1, 1),
(76, 'Jimmy', 'Page', 'Masculino', '2005-09-15', 'jimmy.page1@aluno.com', 'jimmy.page@aluno.com', '11122233419', 1, 1),
(77, 'John', 'Bonham', 'Masculino', '2005-11-01', 'john.bonham1@aluno.com', 'john.bonham@aluno.com', '11122233420', 1, 1),
(78, 'John', 'Paul Jones', 'Masculino', '2005-07-07', 'john.jones1@aluno.com', 'john.jones@aluno.com', '11122233421', 1, 2),
(79, 'Freddie', 'Mercury', 'Masculino', '2005-09-05', 'freddie.mercury2@aluno.com', 'freddie.mercury@aluno.com', '11122233422', 1, 1),
(80, 'Brian', 'May', 'Masculino', '2005-12-09', 'brian.may2@aluno.com', 'brian.may@aluno.com', '11122233423', 1, 2),
(81, 'Roger', 'Taylor', 'Masculino', '2005-11-17', 'roger.taylor2@aluno.com', 'roger.taylor@aluno.com', '11122233424', 1, 1),
(82, 'David', 'Bowie', 'Masculino', '2005-09-19', 'david.bowie2@aluno.com', 'david.bowie@aluno.com', '11122233425', 1, 2),
(83, 'Elton', 'John', 'Masculino', '2005-12-01', 'elton.john1@aluno.com', 'elton.john@aluno.com', '11122233426', 1, 1),
(84, 'George', 'Michael', 'Masculino', '2005-10-15', 'george.michael1@aluno.com', 'george.michael@aluno.com', '11122233427', 1, 1),
(85, 'Phil', 'Collins', 'Masculino', '2005-12-11', 'phil.collins2@aluno.com', 'phil.collins@aluno.com', '11122233428', 1, 1),
(86, 'Peter', 'Gabriel', 'Masculino', '2005-10-14', 'peter.gabriel2@aluno.com', 'peter.gabriel@aluno.com', '11122233429', 1, 2),
(87, 'Sting', 'Summers', 'Masculino', '2005-12-12', 'sting.summers2@aluno.com', 'sting.summers@aluno.com', '11122233430', 1, 2),
(88, 'Bono', 'Vox', 'Masculino', '2005-12-13', 'bono.vox1@aluno.com', 'bono.vox@aluno.com', '11122233431', 1, 1),
(89, 'The', 'Edge', 'Masculino', '2005-11-14', 'the.edge1@aluno.com', 'the.edge@aluno.com', '11122233432', 1, 1),
(90, 'Adam', 'Clayton', 'Masculino', '2005-09-20', 'adam.clayton1@aluno.com', 'adam.clayton@aluno.com', '11122233433', 1, 2),
(91, 'Larry', 'Mullen Jr', 'Masculino', '2005-10-18', 'larry.mullen1@aluno.com', 'larry.mullen@aluno.com', '11122233434', 1, 1),
(92, 'Kurt', 'Cobain', 'Masculino', '2005-10-20', 'kurt.cobain2@aluno.com', 'kurt.cobain@aluno.com', '11122233435', 1, 1),
(93, 'Dave', 'Grohl', 'Masculino', '2005-09-23', 'dave.grohl1@aluno.com', 'dave.grohl@aluno.com', '11122233436', 1, 1),
(94, 'Krist', 'Novoselic', 'Masculino', '2005-10-21', 'krist.novoselic1@aluno.com', 'krist.novoselic@aluno.com', '11122233437', 1, 1),
(95, 'Billy', 'Corgan', 'Masculino', '2005-12-15', 'billy.corgan2@aluno.com', 'billy.corgan@aluno.com', '11122233438', 1, 2),
(96, 'James', 'Iha', 'Masculino', '2005-10-17', 'james.iha1@aluno.com', 'james.iha@aluno.com', '11122233439', 1, 1),
(97, 'Darcy', 'Wretzky', 'Feminino', '2005-10-19', 'darcy.wretzky1@aluno.com', 'darcy.wretzky@aluno.com', '11122233440', 1, 1),
(98, 'Thom', 'Yorke', 'Masculino', '2005-12-18', 'thom.yorke2@aluno.com', 'thom.yorke@aluno.com', '11122233441', 1, 2),
(99, 'Johnny', 'Greenwood', 'Masculino', '2005-10-22', 'johnny.greenwood1@aluno.com', 'johnny.greenwood@aluno.com', '11122233442', 1, 1),
(100, 'Colin', 'Greenwood', 'Masculino', '2005-10-25', 'colin.greenwood1@aluno.com', 'colin.greenwood@aluno.com', '11122233443', 1, 1);

-- ============================
-- INSERÇÕES DE RESPONSÁVEIS
-- ============================
INSERT INTO responsavel (id, nome, ultimo_nome, cpf, grau_parentesco, aluno_id) VALUES
(1, 'John', 'Gallagher', '12345678909', 'Pai', 1),
(2, 'Sarah', 'Gallagher', '12345678910', 'Mãe', 1),
(3, 'Robert', 'Smith', '12345678911', 'Pai', 2),
(4, 'Emily', 'Smith', '12345678912', 'Mãe', 2),
(5, 'Michael', 'Jones', '12345678913', 'Pai', 3),
(6, 'Laura', 'Jones', '12345678914', 'Mãe', 3),
(7, 'James', 'Brown', '12345678915', 'Pai', 4),
(8, 'Anna', 'Brown', '12345678916', 'Mãe', 4),
(9, 'Peter', 'Taylor', '12345678917', 'Pai', 5),
(10, 'Sophia', 'Taylor', '12345678918', 'Mãe', 5),
(11, 'Paul', 'Anderson', '12345678919', 'Pai', 6),
(12, 'Grace', 'Anderson', '12345678920', 'Mãe', 6),
(13, 'David', 'Clark', '12345678921', 'Pai', 7),
(14, 'Emma', 'Clark', '12345678922', 'Mãe', 7),
(15, 'William', 'Harris', '12345678923', 'Pai', 8),
(16, 'Olivia', 'Harris', '12345678924', 'Mãe', 8),
(17, 'George', 'Lewis', '12345678925', 'Pai', 9),
(18, 'Mia', 'Lewis', '12345678926', 'Mãe', 9),
(19, 'Charles', 'Young', '12345678927', 'Pai', 10),
(20, 'Isabella', 'Young', '12345678928', 'Mãe', 10),
(21, 'Henry', 'Walker', '12345678929', 'Pai', 11),
(22, 'Amelia', 'Walker', '12345678930', 'Mãe', 11),
(23, 'Thomas', 'Hall', '12345678931', 'Pai', 12),
(24, 'Chloe', 'Hall', '12345678932', 'Mãe', 12),
(25, 'Andrew', 'Allen', '12345678933', 'Pai', 13),
(26, 'Ellie', 'Allen', '12345678934', 'Mãe', 13),
(27, 'Christopher', 'Scott', '12345678935', 'Pai', 14),
(28, 'Lucy', 'Scott', '12345678936', 'Mãe', 14),
(29, 'Mark', 'Green', '12345678937', 'Pai', 15),
(30, 'Zoe', 'Green', '12345678938', 'Mãe', 15),
(31, 'Jack', 'Adams', '12345678939', 'Pai', 16),
(32, 'Lily', 'Adams', '12345678940', 'Mãe', 16),
(33, 'Adam', 'Baker', '12345678941', 'Pai', 17),
(34, 'Ella', 'Baker', '12345678942', 'Mãe', 17),
(35, 'Patrick', 'Mitchell', '12345678943', 'Pai', 18),
(36, 'Sofia', 'Mitchell', '12345678944', 'Mãe', 18),
(37, 'Harry', 'Carter', '12345678945', 'Pai', 19),
(38, 'Charlotte', 'Carter', '12345678946', 'Mãe', 19),
(39, 'Benjamin', 'Phillips', '12345678947', 'Pai', 20),
(40, 'Victoria', 'Phillips', '12345678948', 'Mãe', 20),
(41, 'Lucas', 'Turner', '12345678949', 'Pai', 21),
(42, 'Alice', 'Turner', '12345678950', 'Mãe', 21),
(43, 'Nathan', 'Evans', '12345678951', 'Pai', 22),
(44, 'Sophia', 'Evans', '12345678952', 'Mãe', 22),
(45, 'Ryan', 'Collins', '12345678953', 'Pai', 23),
(46, 'Hannah', 'Collins', '12345678954', 'Mãe', 23),
(47, 'Jordan', 'Stewart', '12345678955', 'Pai', 24),
(48, 'Ava', 'Stewart', '12345678956', 'Mãe', 24),
(49, 'Ethan', 'Foster', '12345678957', 'Pai', 25),
(50, 'Harper', 'Foster', '12345678958', 'Mãe', 25),
(51, 'Aaron', 'Morgan', '12345678959', 'Pai', 26),
(52, 'Layla', 'Morgan', '12345678960', 'Mãe', 26),
(53, 'Christian', 'White', '12345678961', 'Pai', 27),
(54, 'Abigail', 'White', '12345678962', 'Mãe', 27),
(55, 'Dylan', 'Reed', '12345678963', 'Pai', 28),
(56, 'Scarlett', 'Reed', '12345678964', 'Mãe', 28),
(57, 'Oliver', 'King', '12345678965', 'Pai', 29),
(58, 'Avery', 'King', '12345678966', 'Mãe', 29),
(59, 'Jason', 'Parker', '12345678967', 'Pai', 30),
(60, 'Audrey', 'Parker', '12345678968', 'Mãe', 30),
(61, 'Kevin', 'Wood', '12345678969', 'Pai', 31),
(62, 'Paisley', 'Wood', '12345678970', 'Mãe', 31),
(63, 'Samuel', 'Hughes', '12345678971', 'Pai', 32),
(64, 'Elliana', 'Hughes', '12345678972', 'Mãe', 32),
(65, 'Zachary', 'Bennett', '12345678973', 'Pai', 33),
(66, 'Aurora', 'Bennett', '12345678974', 'Mãe', 33),
(67, 'Jonathan', 'Gray', '12345678975', 'Pai', 34),
(68, 'Caroline', 'Gray', '12345678976', 'Mãe', 34),
(69, 'Eli', 'Wright', '12345678977', 'Pai', 35),
(70, 'Lydia', 'Wright', '12345678978', 'Mãe', 35),
(71, 'Isaac', 'Cole', '12345678979', 'Pai', 36),
(72, 'Vivian', 'Cole', '12345678980', 'Mãe', 36),
(73, 'Oscar', 'Ramirez', '12345678981', 'Pai', 37),
(74, 'Maria', 'Ramirez', '12345678982', 'Mãe', 37),
(75, 'Alexander', 'Diaz', '12345678983', 'Pai', 38),
(76, 'Gabriella', 'Diaz', '12345678984', 'Mãe', 38),
(77, 'Matthew', 'Sanders', '12345678985', 'Pai', 39),
(78, 'Penelope', 'Sanders', '12345678986', 'Mãe', 39),
(79, 'Edward', 'Rogers', '12345678987', 'Pai', 40),
(80, 'Jasmine', 'Rogers', '12345678988', 'Mãe', 40),
(81, 'Bryan', 'Martinez', '12345678989', 'Pai', 41),
(82, 'Stella', 'Martinez', '12345678990', 'Mãe', 41),
(83, 'Hunter', 'Perez', '12345678991', 'Pai', 42),
(84, 'Valentina', 'Perez', '12345678992', 'Mãe', 42),
(85, 'Logan', 'Roberts', '12345678993', 'Pai', 43),
(86, 'Eleanor', 'Roberts', '12345678994', 'Mãe', 43),
(87, 'Caleb', 'Watson', '12345678995', 'Pai', 44),
(88, 'Madeline', 'Watson', '12345678996', 'Mãe', 44),
(89, 'Christian', 'Torres', '12345678997', 'Pai', 45),
(90, 'Amara', 'Torres', '12345678998', 'Mãe', 45),
(91, 'Cameron', 'Price', '12345678999', 'Pai', 46),
(92, 'Eliza', 'Price', '12345678000', 'Mãe', 46),
(93, 'Maxwell', 'Diaz', '12345678001', 'Pai', 47),
(94, 'Bella', 'Diaz', '12345678002', 'Mãe', 47),
(95, 'Joseph', 'Reyes', '12345678003', 'Pai', 48),
(96, 'Savannah', 'Reyes', '12345678004', 'Mãe', 48),
(97, 'Jason', 'Gutierrez', '12345678005', 'Pai', 49),
(98, 'Julia', 'Gutierrez', '12345678006', 'Mãe', 49),
(99, 'Bradley', 'Garcia', '12345678007', 'Pai', 50),
(100, 'Adeline', 'Garcia', '12345678008', 'Mãe', 50),
(101, 'Diego', 'Mendoza', '12345678009', 'Pai', 51),
(102, 'Cecilia', 'Mendoza', '12345678010', 'Mãe', 51),
(103, 'Austin', 'Vega', '12345678011', 'Pai', 52),
(104, 'Leah', 'Vega', '12345678012', 'Mãe', 52),
(105, 'Riley', 'Castro', '12345678013', 'Pai', 53),
(106, 'Isabelle', 'Castro', '12345678014', 'Mãe', 53),
(107, 'Gavin', 'Hernandez', '12345678015', 'Pai', 54),
(108, 'Maya', 'Hernandez', '12345678016', 'Mãe', 54),
(109, 'Sean', 'Ortiz', '12345678017', 'Pai', 55),
(110, 'Ivy', 'Ortiz', '12345678018', 'Mãe', 55),
(111, 'Landon', 'Jenkins', '12345678019', 'Pai', 56),
(112, 'Delilah', 'Jenkins', '12345678020', 'Mãe', 56),
(113, 'Miles', 'Flores', '12345678021', 'Pai', 57),
(114, 'Serena', 'Flores', '12345678022', 'Mãe', 57),
(115, 'Easton', 'Morales', '12345678023', 'Pai', 58),
(116, 'Ariana', 'Morales', '12345678024', 'Mãe', 58),
(117, 'Bryce', 'Fisher', '12345678025', 'Pai', 59),
(118, 'Autumn', 'Fisher', '12345678026', 'Mãe', 59),
(119, 'Bentley', 'Gomez', '12345678027', 'Pai', 60),
(120, 'Brooklyn', 'Gomez', '12345678028', 'Mãe', 60);


-- ============================
-- INSERÇÕES DE ENDEREÇOS PARA ALUNOS
-- ============================
INSERT INTO endereco (rua, numero, bairro, cidade, estado, cep, aluno_id, coordenador_id, professor_id, coordenacao_id) VALUES
('Rua da Aurora', '123', 'Boa Vista', 'Recife', 'PE', '50050000', 1, NULL, NULL, NULL),
('Rua do Príncipe', '456', 'Boa Vista', 'Recife', 'PE', '50050001', 2, NULL, NULL, NULL),
('Rua Visconde de Suassuna', '789', 'Santo Amaro', 'Recife', 'PE', '50050002', 3, NULL, NULL, NULL),
('Rua da Harmonia', '111', 'Casa Amarela', 'Recife', 'PE', '52050010', 4, NULL, NULL, NULL),
('Avenida Norte', '222', 'Macaxeira', 'Recife', 'PE', '52090500', 5, NULL, NULL, NULL),
('Rua do Lima', '333', 'Santo Amaro', 'Recife', 'PE', '50050500', 6, NULL, NULL, NULL),
('Rua Barão de Souza Leão', '444', 'Boa Viagem', 'Recife', 'PE', '51030000', 7, NULL, NULL, NULL),
('Avenida Recife', '555', 'Ipsep', 'Recife', 'PE', '51250000', 8, NULL, NULL, NULL),
('Rua Real da Torre', '666', 'Torre', 'Recife', 'PE', '50710100', 9, NULL, NULL, NULL),
('Rua Benfica', '777', 'Madalena', 'Recife', 'PE', '50720000', 10, NULL, NULL, NULL),
('Rua Padre Lemos', '888', 'Casa Forte', 'Recife', 'PE', '52060100', 11, NULL, NULL, NULL),
('Rua das Creoulas', '999', 'Casa Forte', 'Recife', 'PE', '52070000', 12, NULL, NULL, NULL),
('Avenida Dezessete de Agosto', '101', 'Casa Forte', 'Recife', 'PE', '52061000', 13, NULL, NULL, NULL),
('Rua Guilherme Pinto', '102', 'Graças', 'Recife', 'PE', '52011100', 14, NULL, NULL, NULL),
('Avenida Rosa e Silva', '103', 'Graças', 'Recife', 'PE', '52020100', 15, NULL, NULL, NULL),
('Rua Conselheiro Portela', '104', 'Espinheiro', 'Recife', 'PE', '52030000', 16, NULL, NULL, NULL),
('Avenida Norte', '105', 'Rosarinho', 'Recife', 'PE', '52020110', 17, NULL, NULL, NULL),
('Rua Amaro Bezerra', '106', 'Derby', 'Recife', 'PE', '50030100', 18, NULL, NULL, NULL),
('Rua Dom Bosco', '107', 'Boa Vista', 'Recife', 'PE', '50040100', 19, NULL, NULL, NULL),
('Rua Joaquim Nabuco', '108', 'Derby', 'Recife', 'PE', '50030110', 20, NULL, NULL, NULL),
('Rua Amélia', '109', 'Graças', 'Recife', 'PE', '52011000', 21, NULL, NULL, NULL),
('Rua do Futuro', '110', 'Graças', 'Recife', 'PE', '52080002', 22, NULL, NULL, NULL),
('Avenida Beira Rio', '111', 'Pina', 'Recife', 'PE', '51170000', 23, NULL, NULL, NULL),
('Rua Capitão Rebelinho', '112', 'Pina', 'Recife', 'PE', '51110200', 24, NULL, NULL, NULL),
('Avenida Boa Viagem', '113', 'Boa Viagem', 'Recife', 'PE', '51021100', 25, NULL, NULL, NULL),
('Rua Setúbal', '114', 'Boa Viagem', 'Recife', 'PE', '51030100', 26, NULL, NULL, NULL),
('Avenida Domingos Ferreira', '115', 'Boa Viagem', 'Recife', 'PE', '51020100', 27, NULL, NULL, NULL),
('Rua dos Navegantes', '116', 'Boa Viagem', 'Recife', 'PE', '51011100', 28, NULL, NULL, NULL),
('Rua Carlos Pereira Falcão', '117', 'Boa Viagem', 'Recife', 'PE', '51030110', 29, NULL, NULL, NULL),
('Rua Dona Benvinda de Araújo', '118', 'Boa Viagem', 'Recife', 'PE', '51020400', 30, NULL, NULL, NULL),
('Rua Marechal Deodoro', '119', 'Santo Antônio', 'Recife', 'PE', '50020300', 31, NULL, NULL, NULL),
('Rua Nova', '120', 'São José', 'Recife', 'PE', '50030000', 32, NULL, NULL, NULL),
('Rua das Calçadas', '121', 'São José', 'Recife', 'PE', '50020310', 33, NULL, NULL, NULL),
('Rua da Imperatriz', '122', 'Boa Vista', 'Recife', 'PE', '50050030', 34, NULL, NULL, NULL),
('Rua do Sol', '123', 'Santo Antônio', 'Recife', 'PE', '50010200', 35, NULL, NULL, NULL),
('Rua do Imperador', '124', 'Santo Antônio', 'Recife', 'PE', '50010210', 36, NULL, NULL, NULL),
('Rua das Fronteiras', '125', 'Santo Amaro', 'Recife', 'PE', '50040010', 37, NULL, NULL, NULL),
('Rua Barão de Moreno', '126', 'Torreão', 'Recife', 'PE', '52030110', 38, NULL, NULL, NULL),
('Rua Desembargador Góis Cavalcante', '127', 'Jaqueira', 'Recife', 'PE', '52050300', 39, NULL, NULL, NULL),
('Rua do Espinheiro', '128', 'Espinheiro', 'Recife', 'PE', '52030120', 40, NULL, NULL, NULL),
('Rua Alfredo Lisboa', '129', 'Recife Antigo', 'Recife', 'PE', '50030020', 41, NULL, NULL, NULL),
('Rua Marquês de Olinda', '130', 'Recife Antigo', 'Recife', 'PE', '50030130', 42, NULL, NULL, NULL),
('Rua do Bom Jesus', '131', 'Recife Antigo', 'Recife', 'PE', '50030140', 43, NULL, NULL, NULL),
('Avenida Rio Branco', '132', 'Recife Antigo', 'Recife', 'PE', '50030150', 44, NULL, NULL, NULL),
('Rua Madre de Deus', '133', 'Recife Antigo', 'Recife', 'PE', '50030160', 45, NULL, NULL, NULL),
('Rua Aurora', '134', 'Boa Vista', 'Recife', 'PE', '50020170', 46, NULL, NULL, NULL),
('Rua Visconde de Goiana', '135', 'Santo Antônio', 'Recife', 'PE', '50020320', 47, NULL, NULL, NULL),
('Rua do Príncipe', '136', 'Boa Vista', 'Recife', 'PE', '50020220', 48, NULL, NULL, NULL),
('Rua do Riachuelo', '137', 'Boa Vista', 'Recife', 'PE', '50020030', 49, NULL, NULL, NULL),
('Rua Domingos José Martins', '138', 'Santo Antônio', 'Recife', 'PE', '50020330', 50, NULL, NULL, NULL),
('Rua da Concórdia', '139', 'São José', 'Recife', 'PE', '50020050', 51, NULL, NULL, NULL),
('Rua dos Judeus', '140', 'Recife Antigo', 'Recife', 'PE', '50030310', 52, NULL, NULL, NULL),
('Rua Frei Caneca', '141', 'Soledade', 'Recife', 'PE', '50020510', 53, NULL, NULL, NULL),
('Rua dos Palmares', '142', 'Soledade', 'Recife', 'PE', '50030090', 54, NULL, NULL, NULL),
('Rua Doutor José Mariano', '143', 'Boa Vista', 'Recife', 'PE', '50020190', 55, NULL, NULL, NULL),
('Rua do Hospício', '144', 'Boa Vista', 'Recife', 'PE', '50020210', 56, NULL, NULL, NULL),
('Rua da União', '145', 'Boa Vista', 'Recife', 'PE', '50020020', 57, NULL, NULL, NULL),
('Rua Gervásio Pires', '146', 'Boa Vista', 'Recife', 'PE', '50020370', 58, NULL, NULL, NULL),
('Rua Fernandes Vieira', '147', 'Boa Vista', 'Recife', 'PE', '50020380', 59, NULL, NULL, NULL),
('Rua do Sossego', '148', 'Boa Vista', 'Recife', 'PE', '50020390', 60, NULL, NULL, NULL),
('Rua Sete de Setembro', '149', 'Recife Antigo', 'Recife', 'PE', '50030410', 61, NULL, NULL, NULL),
('Rua da Guia', '150', 'Recife Antigo', 'Recife', 'PE', '50030420', 62, NULL, NULL, NULL),
('Rua Nova Descoberta', '151', 'Nova Descoberta', 'Recife', 'PE', '52080400', 63, NULL, NULL, NULL),
('Rua Dois Irmãos', '152', 'Dois Irmãos', 'Recife', 'PE', '52080210', 64, NULL, NULL, NULL),
('Rua Joaquim Cardoso', '153', 'Casa Amarela', 'Recife', 'PE', '52080300', 65, NULL, NULL, NULL),
('Avenida Caxangá', '154', 'Cordeiro', 'Recife', 'PE', '50721120', 66, NULL, NULL, NULL),
('Rua Barão de São Borja', '155', 'Derby', 'Recife', 'PE', '50020410', 67, NULL, NULL, NULL),
('Rua Almeida Cunha', '156', 'Boa Viagem', 'Recife', 'PE', '51030160', 68, NULL, NULL, NULL),
('Rua Ribeiro de Brito', '157', 'Boa Viagem', 'Recife', 'PE', '51030170', 69, NULL, NULL, NULL),
('Rua General Joaquim Inácio', '158', 'Ilha do Leite', 'Recife', 'PE', '50050010', 70, NULL, NULL, NULL),
('Rua Jornalista Aníbal Fernandes', '159', 'Boa Viagem', 'Recife', 'PE', '51030180', 71, NULL, NULL, NULL),
('Rua Almirante Tamandaré', '160', 'Pina', 'Recife', 'PE', '51021110', 72, NULL, NULL, NULL),
('Rua Professor José dos Anjos', '161', 'Jaqueira', 'Recife', 'PE', '52050120', 73, NULL, NULL, NULL),
('Rua Guilherme Pinto', '162', 'Graças', 'Recife', 'PE', '52020140', 74, NULL, NULL, NULL),
('Rua João Tude de Melo', '163', 'Espinheiro', 'Recife', 'PE', '52030050', 75, NULL, NULL, NULL),
('Rua Jacó Velosino', '164', 'Torre', 'Recife', 'PE', '50710060', 76, NULL, NULL, NULL),
('Rua da Saudade', '165', 'Soledade', 'Recife', 'PE', '50020530', 77, NULL, NULL, NULL),
('Rua Barão de Itamaracá', '166', 'Espinheiro', 'Recife', 'PE', '52030060', 78, NULL, NULL, NULL),
('Rua Amélia', '167', 'Graças', 'Recife', 'PE', '52020150', 79, NULL, NULL, NULL),
('Rua João Fernandes Vieira', '168', 'Boa Vista', 'Recife', 'PE', '50020360', 80, NULL, NULL, NULL),
('Rua dos Coelhos', '169', 'Coelhos', 'Recife', 'PE', '50040110', 81, NULL, NULL, NULL),
('Rua da Hora', '170', 'Espinheiro', 'Recife', 'PE', '52030070', 82, NULL, NULL, NULL),
('Rua Doutor Malaquias', '171', 'Espinheiro', 'Recife', 'PE', '52030080', 83, NULL, NULL, NULL),
('Rua do Sossego', '172', 'Boa Vista', 'Recife', 'PE', '50020350', 84, NULL, NULL, NULL),
('Rua Princesa Isabel', '173', 'Boa Vista', 'Recife', 'PE', '50020340', 85, NULL, NULL, NULL),
('Rua Carlos Gomes', '174', 'Boa Vista', 'Recife', 'PE', '50020320', 86, NULL, NULL, NULL),
('Rua Tupinambás', '175', 'Boa Vista', 'Recife', 'PE', '50020330', 87, NULL, NULL, NULL),
('Rua Alfredo Lisboa', '176', 'Recife Antigo', 'Recife', 'PE', '50030510', 88, NULL, NULL, NULL),
('Rua Madre de Deus', '177', 'Recife Antigo', 'Recife', 'PE', '50030520', 89, NULL, NULL, NULL),
('Rua do Apolo', '178', 'Recife Antigo', 'Recife', 'PE', '50030530', 90, NULL, NULL, NULL),
('Rua da Moeda', '179', 'Recife Antigo', 'Recife', 'PE', '50030540', 91, NULL, NULL, NULL),
('Rua Marquês de Olinda', '180', 'Recife Antigo', 'Recife', 'PE', '50030550', 92, NULL, NULL, NULL),
('Rua do Bom Jesus', '181', 'Recife Antigo', 'Recife', 'PE', '50030560', 93, NULL, NULL, NULL),
('Rua Vigário Tenório', '182', 'Recife Antigo', 'Recife', 'PE', '50030570', 94, NULL, NULL, NULL),
('Rua Imperador Pedro II', '183', 'Santo Antônio', 'Recife', 'PE', '50010240', 95, NULL, NULL, NULL),
('Rua do Sol', '184', 'Santo Antônio', 'Recife', 'PE', '50010250', 96, NULL, NULL, NULL),
('Rua São João', '185', 'São José', 'Recife', 'PE', '50020380', 97, NULL, NULL, NULL),
('Rua das Calçadas', '186', 'São José', 'Recife', 'PE', '50020490', 98, NULL, NULL, NULL),
('Rua Vidal de Negreiros', '187', 'Santo Antônio', 'Recife', 'PE', '50010330', 99, NULL, NULL, NULL),
('Rua do Imperador', '188', 'Santo Antônio', 'Recife', 'PE', '50020370', 100, NULL, NULL, NULL);

-- ============================
-- INSERÇÕES DE ENDEREÇO DE COORDENADOR 
-- ============================

INSERT INTO endereco (rua, numero, bairro, cidade, estado, cep, aluno_id, coordenador_id, professor_id, coordenacao_id) VALUES
('Rua da Aurora', '500', 'Boa Vista', 'Recife', 'PE', '50050000', NULL, '12345678901', NULL, NULL), -- Morrissey Smith
('Avenida Conde da Boa Vista', '1000', 'Boa Vista', 'Recife', 'PE', '50060000', NULL, '23456789012', NULL, NULL), -- Eddie Vedder
('Rua do Príncipe', '200', 'Santo Amaro', 'Recife', 'PE', '50070000', NULL, '34567890123', NULL, NULL); -- Thom Yorke


-- ============================
-- INSERÇÕES DE ENDEREÇOS PARA PROFESSORES
-- ============================
INSERT INTO endereco (rua, numero, bairro, cidade, estado, cep, aluno_id, coordenador_id, professor_id, coordenacao_id) VALUES
('Rua da Aurora', '101', 'Boa Vista', 'Recife', 'PE', '50050010', NULL, NULL, '45678901234', NULL), -- Liam Gallagher
('Avenida Conde da Boa Vista', '202', 'Boa Vista', 'Recife', 'PE', '50060020', NULL, NULL, '56789012345', NULL), -- Noel Gallagher
('Rua do Príncipe', '303', 'Santo Amaro', 'Recife', 'PE', '50070030', NULL, NULL, '67890123456', NULL), -- Brandon Flowers
('Rua da Harmonia', '404', 'Casa Amarela', 'Recife', 'PE', '52050040', NULL, NULL, '78901234567', NULL), -- Alex Turner
('Avenida Norte', '505', 'Macaxeira', 'Recife', 'PE', '52090550', NULL, NULL, '89012345678', NULL), -- Matt Bellamy
('Rua do Lima', '606', 'Santo Amaro', 'Recife', 'PE', '50050560', NULL, NULL, '90123456789', NULL), -- Chris Martin
('Rua Barão de Souza Leão', '707', 'Boa Viagem', 'Recife', 'PE', '51030070', NULL, NULL, '01234567890', NULL), -- Robert Smith
('Avenida Recife', '808', 'Ipsep', 'Recife', 'PE', '51250080', NULL, NULL, '12345098765', NULL), -- Billy Corgan
('Rua Real da Torre', '909', 'Torre', 'Recife', 'PE', '50710190', NULL, NULL, '98765432101', NULL), -- Jack White
('Rua Benfica', '1001', 'Madalena', 'Recife', 'PE', '50720010', NULL, NULL, '19283746501', NULL); -- Kurt Cobain

-- ============================
-- INSERÇÕES DE TELEFONES PARA ALUNOS
-- ============================
INSERT INTO telefone (ddd, numero, aluno_id, coordenador_id, professor_id, coordenacao_id, responsavel_id) VALUES
('81', '988887771', 1, NULL, NULL, NULL, NULL),
('81', '988887772', 2, NULL, NULL, NULL, NULL),
('81', '988887773', 3, NULL, NULL, NULL, NULL),
('81', '988887774', 4, NULL, NULL, NULL, NULL),
('81', '988887775', 5, NULL, NULL, NULL, NULL),
('81', '988887776', 6, NULL, NULL, NULL, NULL),
('81', '988887777', 7, NULL, NULL, NULL, NULL),
('81', '988887778', 8, NULL, NULL, NULL, NULL),
('81', '988887779', 9, NULL, NULL, NULL, NULL),
('81', '988887780', 10, NULL, NULL, NULL, NULL),
('81', '988887781', 11, NULL, NULL, NULL, NULL),
('81', '988887782', 12, NULL, NULL, NULL, NULL),
('81', '988887783', 13, NULL, NULL, NULL, NULL),
('81', '988887784', 14, NULL, NULL, NULL, NULL),
('81', '988887785', 15, NULL, NULL, NULL, NULL),
('81', '988887786', 16, NULL, NULL, NULL, NULL),
('81', '988887787', 17, NULL, NULL, NULL, NULL),
('81', '988887788', 18, NULL, NULL, NULL, NULL),
('81', '988887789', 19, NULL, NULL, NULL, NULL),
('81', '988887790', 20, NULL, NULL, NULL, NULL),
('81', '988887791', 21, NULL, NULL, NULL, NULL),
('81', '988887792', 22, NULL, NULL, NULL, NULL),
('81', '988887793', 23, NULL, NULL, NULL, NULL),
('81', '988887794', 24, NULL, NULL, NULL, NULL),
('81', '988887795', 25, NULL, NULL, NULL, NULL),
('81', '988887796', 26, NULL, NULL, NULL, NULL),
('81', '988887797', 27, NULL, NULL, NULL, NULL),
('81', '988887798', 28, NULL, NULL, NULL, NULL),
('81', '988887799', 29, NULL, NULL, NULL, NULL),
('81', '988887800', 30, NULL, NULL, NULL, NULL),
('81', '988887801', 31, NULL, NULL, NULL, NULL),
('81', '988887802', 32, NULL, NULL, NULL, NULL),
('81', '988887803', 33, NULL, NULL, NULL, NULL),
('81', '988887804', 34, NULL, NULL, NULL, NULL),
('81', '988887805', 35, NULL, NULL, NULL, NULL),
('81', '988887806', 36, NULL, NULL, NULL, NULL),
('81', '988887807', 37, NULL, NULL, NULL, NULL),
('81', '988887808', 38, NULL, NULL, NULL, NULL),
('81', '988887809', 39, NULL, NULL, NULL, NULL),
('81', '988887810', 40, NULL, NULL, NULL, NULL),
('81', '988887811', 41, NULL, NULL, NULL, NULL),
('81', '988887812', 42, NULL, NULL, NULL, NULL),
('81', '988887813', 43, NULL, NULL, NULL, NULL),
('81', '988887814', 44, NULL, NULL, NULL, NULL),
('81', '988887815', 45, NULL, NULL, NULL, NULL),
('81', '988887816', 46, NULL, NULL, NULL, NULL),
('81', '988887817', 47, NULL, NULL, NULL, NULL),
('81', '988887818', 48, NULL, NULL, NULL, NULL),
('81', '988887819', 49, NULL, NULL, NULL, NULL),
('81', '988887820', 50, NULL, NULL, NULL, NULL),
('81', '988887821', 51, NULL, NULL, NULL, NULL),
('81', '988887822', 52, NULL, NULL, NULL, NULL),
('81', '988887823', 53, NULL, NULL, NULL, NULL),
('81', '988887824', 54, NULL, NULL, NULL, NULL),
('81', '988887825', 55, NULL, NULL, NULL, NULL),
('81', '988887826', 56, NULL, NULL, NULL, NULL),
('81', '988887827', 57, NULL, NULL, NULL, NULL),
('81', '988887828', 58, NULL, NULL, NULL, NULL),
('81', '988887829', 59, NULL, NULL, NULL, NULL),
('81', '988887830', 60, NULL, NULL, NULL, NULL),
('81', '988887831', 61, NULL, NULL, NULL, NULL),
('81', '988887832', 62, NULL, NULL, NULL, NULL),
('81', '988887833', 63, NULL, NULL, NULL, NULL),
('81', '988887834', 64, NULL, NULL, NULL, NULL),
('81', '988887835', 65, NULL, NULL, NULL, NULL),
('81', '988887836', 66, NULL, NULL, NULL, NULL),
('81', '988887837', 67, NULL, NULL, NULL, NULL),
('81', '988887838', 68, NULL, NULL, NULL, NULL),
('81', '988887839', 69, NULL, NULL, NULL, NULL),
('81', '988887840', 70, NULL, NULL, NULL, NULL),
('81', '988887841', 71, NULL, NULL, NULL, NULL),
('81', '988887842', 72, NULL, NULL, NULL, NULL),
('81', '988887843', 73, NULL, NULL, NULL, NULL),
('81', '988887844', 74, NULL, NULL, NULL, NULL),
('81', '988887845', 75, NULL, NULL, NULL, NULL),
('81', '988887846', 76, NULL, NULL, NULL, NULL),
('81', '988887847', 77, NULL, NULL, NULL, NULL),
('81', '988887848', 78, NULL, NULL, NULL, NULL),
('81', '988887849', 79, NULL, NULL, NULL, NULL),
('81', '988887850', 80, NULL, NULL, NULL, NULL),
('81', '988887851', 81, NULL, NULL, NULL, NULL),
('81', '988887852', 82, NULL, NULL, NULL, NULL),
('81', '988887853', 83, NULL, NULL, NULL, NULL),
('81', '988887854', 84, NULL, NULL, NULL, NULL),
('81', '988887855', 85, NULL, NULL, NULL, NULL),
('81', '988887856', 86, NULL, NULL, NULL, NULL),
('81', '988887857', 87, NULL, NULL, NULL, NULL),
('81', '988887858', 88, NULL, NULL, NULL, NULL),
('81', '988887859', 89, NULL, NULL, NULL, NULL),
('81', '988887860', 90, NULL, NULL, NULL, NULL),
('81', '988887861', 91, NULL, NULL, NULL, NULL),
('81', '988887862', 92, NULL, NULL, NULL, NULL),
('81', '988887863', 93, NULL, NULL, NULL, NULL),
('81', '988887864', 94, NULL, NULL, NULL, NULL),
('81', '988887865', 95, NULL, NULL, NULL, NULL),
('81', '988887866', 96, NULL, NULL, NULL, NULL),
('81', '988887867', 97, NULL, NULL, NULL, NULL),
('81', '988887868', 98, NULL, NULL, NULL, NULL),
('81', '988887869', 99, NULL, NULL, NULL, NULL),
('81', '988887870', 100, NULL, NULL, NULL, NULL), 
('81', '988887871', 1, NULL, NULL, NULL, NULL),
('81', '988887872', 2, NULL, NULL, NULL, NULL),
('81', '988887873', 3, NULL, NULL, NULL, NULL),
('81', '988887874', 4, NULL, NULL, NULL, NULL),
('81', '988887875', 5, NULL, NULL, NULL, NULL),
('81', '988887876', 6, NULL, NULL, NULL, NULL),
('81', '988887877', 7, NULL, NULL, NULL, NULL),
('81', '988887878', 8, NULL, NULL, NULL, NULL),
('81', '988887879', 9, NULL, NULL, NULL, NULL),
('81', '988887880', 10, NULL, NULL, NULL, NULL),
('81', '988887881', 11, NULL, NULL, NULL, NULL),
('81', '988887882', 12, NULL, NULL, NULL, NULL),
('81', '988887883', 13, NULL, NULL, NULL, NULL),
('81', '988887884', 14, NULL, NULL, NULL, NULL),
('81', '988887885', 15, NULL, NULL, NULL, NULL),
('81', '988887886', 16, NULL, NULL, NULL, NULL),
('81', '988887887', 17, NULL, NULL, NULL, NULL),
('81', '988887888', 18, NULL, NULL, NULL, NULL),
('81', '988887889', 19, NULL, NULL, NULL, NULL),
('81', '988887890', 20, NULL, NULL, NULL, NULL),
('81', '988887891', 21, NULL, NULL, NULL, NULL),
('81', '988887892', 22, NULL, NULL, NULL, NULL),
('81', '988887893', 23, NULL, NULL, NULL, NULL),
('81', '988887894', 24, NULL, NULL, NULL, NULL),
('81', '988887895', 25, NULL, NULL, NULL, NULL),
('81', '988887896', 26, NULL, NULL, NULL, NULL),
('81', '988887897', 27, NULL, NULL, NULL, NULL),
('81', '988887898', 28, NULL, NULL, NULL, NULL),
('81', '988887899', 29, NULL, NULL, NULL, NULL),
('81', '988887900', 30, NULL, NULL, NULL, NULL);


-- ============================
-- INSERÇÕES DE TELEFONES PARA RESPONSÁVEIS
-- ============================
INSERT INTO telefone (ddd, numero, aluno_id, coordenador_id, professor_id, coordenacao_id, responsavel_id) VALUES
('81', '988888001', NULL, NULL, NULL, NULL, 1),
('81', '988888002', NULL, NULL, NULL, NULL, 2),
('81', '988888003', NULL, NULL, NULL, NULL, 3),
('81', '988888004', NULL, NULL, NULL, NULL, 4),
('81', '988888005', NULL, NULL, NULL, NULL, 5),
('81', '988888006', NULL, NULL, NULL, NULL, 6),
('81', '988888007', NULL, NULL, NULL, NULL, 7),
('81', '988888008', NULL, NULL, NULL, NULL, 8),
('81', '988888009', NULL, NULL, NULL, NULL, 9),
('81', '988888010', NULL, NULL, NULL, NULL, 10),
('81', '988888011', NULL, NULL, NULL, NULL, 11),
('81', '988888012', NULL, NULL, NULL, NULL, 12),
('81', '988888013', NULL, NULL, NULL, NULL, 13),
('81', '988888014', NULL, NULL, NULL, NULL, 14),
('81', '988888015', NULL, NULL, NULL, NULL, 15),
('81', '988888016', NULL, NULL, NULL, NULL, 16),
('81', '988888017', NULL, NULL, NULL, NULL, 17),
('81', '988888018', NULL, NULL, NULL, NULL, 18),
('81', '988888019', NULL, NULL, NULL, NULL, 19),
('81', '988888020', NULL, NULL, NULL, NULL, 20),
('81', '988888021', NULL, NULL, NULL, NULL, 21),
('81', '988888022', NULL, NULL, NULL, NULL, 22),
('81', '988888023', NULL, NULL, NULL, NULL, 23),
('81', '988888024', NULL, NULL, NULL, NULL, 24),
('81', '988888025', NULL, NULL, NULL, NULL, 25),
('81', '988888026', NULL, NULL, NULL, NULL, 26),
('81', '988888027', NULL, NULL, NULL, NULL, 27),
('81', '988888028', NULL, NULL, NULL, NULL, 28),
('81', '988888029', NULL, NULL, NULL, NULL, 29),
('81', '988888030', NULL, NULL, NULL, NULL, 30),
('81', '988888031', NULL, NULL, NULL, NULL, 31),
('81', '988888032', NULL, NULL, NULL, NULL, 32),
('81', '988888033', NULL, NULL, NULL, NULL, 33),
('81', '988888034', NULL, NULL, NULL, NULL, 34),
('81', '988888035', NULL, NULL, NULL, NULL, 35),
('81', '988888036', NULL, NULL, NULL, NULL, 36),
('81', '988888037', NULL, NULL, NULL, NULL, 37),
('81', '988888038', NULL, NULL, NULL, NULL, 38),
('81', '988888039', NULL, NULL, NULL, NULL, 39),
('81', '988888040', NULL, NULL, NULL, NULL, 40),
('81', '988888041', NULL, NULL, NULL, NULL, 41),
('81', '988888042', NULL, NULL, NULL, NULL, 42),
('81', '988888043', NULL, NULL, NULL, NULL, 43),
('81', '988888044', NULL, NULL, NULL, NULL, 44),
('81', '988888045', NULL, NULL, NULL, NULL, 45),
('81', '988888046', NULL, NULL, NULL, NULL, 46),
('81', '988888047', NULL, NULL, NULL, NULL, 47),
('81', '988888048', NULL, NULL, NULL, NULL, 48),
('81', '988888049', NULL, NULL, NULL, NULL, 49),
('81', '988888050', NULL, NULL, NULL, NULL, 50),
('81', '988888051', NULL, NULL, NULL, NULL, 51),
('81', '988888052', NULL, NULL, NULL, NULL, 52),
('81', '988888053', NULL, NULL, NULL, NULL, 53),
('81', '988888054', NULL, NULL, NULL, NULL, 54),
('81', '988888055', NULL, NULL, NULL, NULL, 55),
('81', '988888056', NULL, NULL, NULL, NULL, 56),
('81', '988888057', NULL, NULL, NULL, NULL, 57),
('81', '988888058', NULL, NULL, NULL, NULL, 58),
('81', '988888059', NULL, NULL, NULL, NULL, 59),
('81', '988888060', NULL, NULL, NULL, NULL, 60),
('81', '988888061', NULL, NULL, NULL, NULL, 61),
('81', '988888062', NULL, NULL, NULL, NULL, 62),
('81', '988888063', NULL, NULL, NULL, NULL, 63),
('81', '988888064', NULL, NULL, NULL, NULL, 64),
('81', '988888065', NULL, NULL, NULL, NULL, 65),
('81', '988888066', NULL, NULL, NULL, NULL, 66),
('81', '988888067', NULL, NULL, NULL, NULL, 67),
('81', '988888068', NULL, NULL, NULL, NULL, 68),
('81', '988888069', NULL, NULL, NULL, NULL, 69),
('81', '988888070', NULL, NULL, NULL, NULL, 70),
('81', '988888071', NULL, NULL, NULL, NULL, 71),
('81', '988888072', NULL, NULL, NULL, NULL, 72),
('81', '988888073', NULL, NULL, NULL, NULL, 73),
('81', '988888074', NULL, NULL, NULL, NULL, 74),
('81', '988888075', NULL, NULL, NULL, NULL, 75),
('81', '988888076', NULL, NULL, NULL, NULL, 76),
('81', '988888077', NULL, NULL, NULL, NULL, 77),
('81', '988888078', NULL, NULL, NULL, NULL, 78),
('81', '988888079', NULL, NULL, NULL, NULL, 79),
('81', '988888080', NULL, NULL, NULL, NULL, 80),
('81', '988888081', NULL, NULL, NULL, NULL, 81),
('81', '988888082', NULL, NULL, NULL, NULL, 82),
('81', '988888083', NULL, NULL, NULL, NULL, 83),
('81', '988888084', NULL, NULL, NULL, NULL, 84),
('81', '988888085', NULL, NULL, NULL, NULL, 85),
('81', '988888086', NULL, NULL, NULL, NULL, 86),
('81', '988888087', NULL, NULL, NULL, NULL, 87),
('81', '988888088', NULL, NULL, NULL, NULL, 88),
('81', '988888089', NULL, NULL, NULL, NULL, 89),
('81', '988888090', NULL, NULL, NULL, NULL, 90),
('81', '988888091', NULL, NULL, NULL, NULL, 91),
('81', '988888092', NULL, NULL, NULL, NULL, 92),
('81', '988888093', NULL, NULL, NULL, NULL, 93),
('81', '988888094', NULL, NULL, NULL, NULL, 94),
('81', '988888095', NULL, NULL, NULL, NULL, 95),
('81', '988888096', NULL, NULL, NULL, NULL, 96),
('81', '988888097', NULL, NULL, NULL, NULL, 97),
('81', '988888098', NULL, NULL, NULL, NULL, 98),
('81', '988888099', NULL, NULL, NULL, NULL, 99),
('81', '988888100', NULL, NULL, NULL, NULL, 100),
('81', '988888101', NULL, NULL, NULL, NULL, 101),
('81', '988888102', NULL, NULL, NULL, NULL, 102),
('81', '988888103', NULL, NULL, NULL, NULL, 103),
('81', '988888104', NULL, NULL, NULL, NULL, 104),
('81', '988888105', NULL, NULL, NULL, NULL, 105),
('81', '988888106', NULL, NULL, NULL, NULL, 106),
('81', '988888107', NULL, NULL, NULL, NULL, 107),
('81', '988888108', NULL, NULL, NULL, NULL, 108),
('81', '988888109', NULL, NULL, NULL, NULL, 109),
('81', '988888110', NULL, NULL, NULL, NULL, 110),
('81', '988888111', NULL, NULL, NULL, NULL, 111),
('81', '988888112', NULL, NULL, NULL, NULL, 112),
('81', '988888113', NULL, NULL, NULL, NULL, 113),
('81', '988888114', NULL, NULL, NULL, NULL, 114),
('81', '988888115', NULL, NULL, NULL, NULL, 115),
('81', '988888116', NULL, NULL, NULL, NULL, 116),
('81', '988888117', NULL, NULL, NULL, NULL, 117),
('81', '988888118', NULL, NULL, NULL, NULL, 118),
('81', '988888119', NULL, NULL, NULL, NULL, 119),
('81', '988888120', NULL, NULL, NULL, NULL, 120);

-- ============================
-- INSERÇÕES DE TELEFONES PARA COORDENADORES
-- ============================
INSERT INTO telefone (ddd, numero, aluno_id, coordenador_id, professor_id, coordenacao_id, responsavel_id) VALUES
('81', '987654321', NULL, '12345678901', NULL, NULL, NULL), -- Morrissey Smith
('81', '987654322', NULL, '23456789012', NULL, NULL, NULL), -- Eddie Vedder
('81', '987654323', NULL, '34567890123', NULL, NULL, NULL); -- Thom Yorke


-- ============================
-- INSERÇÕES DE TELEFONES PARA PROFESSORES
-- ============================
INSERT INTO telefone (ddd, numero, aluno_id, coordenador_id, professor_id, coordenacao_id, responsavel_id) VALUES
('81', '988001001', NULL, NULL, '45678901234', NULL, NULL), -- Liam Gallagher
('81', '988001002', NULL, NULL, '56789012345', NULL, NULL), -- Noel Gallagher
('81', '988001003', NULL, NULL, '67890123456', NULL, NULL), -- Brandon Flowers
('81', '988001004', NULL, NULL, '78901234567', NULL, NULL), -- Alex Turner
('81', '988001005', NULL, NULL, '89012345678', NULL, NULL), -- Matt Bellamy
('81', '988001006', NULL, NULL, '90123456789', NULL, NULL), -- Chris Martin
('81', '988001007', NULL, NULL, '01234567890', NULL, NULL), -- Robert Smith
('81', '988001008', NULL, NULL, '12345098765', NULL, NULL), -- Billy Corgan
('81', '988001009', NULL, NULL, '98765432101', NULL, NULL), -- Jack White
('81', '988001010', NULL, NULL, '19283746501', NULL, NULL); -- Kurt Cobain

-- ============================
-- ASSOCIAÇÕES DE TURMAS E ALUNOS
-- ============================
INSERT INTO aluno_turma (turma_id, aluno_id) VALUES
-- Alunos da Turma 1
(1, 1),
(1, 2),
(1, 4),
(1, 5),
(1, 8),
(1, 9),
(1, 10),
(1, 12),
(1, 13),
(1, 15),
(1, 16),
(1, 21),
(1, 22),
(1, 23),
(1, 24),
(1, 33),
(1, 34),
(1, 35),
(1, 36),
(1, 37),
(1, 47),
(1, 48),
(1, 56),
(1, 57),
(1, 58),
(1, 62),
(1, 64),
(1, 65),
(1, 72),
(1, 73),
(1, 75),
(1, 76),
(1, 77),
(1, 79),
(1, 81),
(1, 83),
(1, 84),
(1, 91),
(1, 92),
(1, 94),
(1, 96),
(1, 99),
(1, 100),

-- Alunos da Turma 2
(2, 3),
(2, 6),
(2, 7),
(2, 11),
(2, 14),
(2, 17),
(2, 18),
(2, 19),
(2, 20),
(2, 25),
(2, 26),
(2, 27),
(2, 28),
(2, 29),
(2, 30),
(2, 31),
(2, 32),
(2, 38),
(2, 39),
(2, 42),
(2, 45),
(2, 46),
(2, 49),
(2, 50),
(2, 53),
(2, 54),
(2, 55),
(2, 60),
(2, 61),
(2, 63),
(2, 68),
(2, 70),
(2, 71),
(2, 74),
(2, 78),
(2, 80),
(2, 82),
(2, 86),
(2, 87),
(2, 88),
(2, 90),
(2, 93),
(2, 95),
(2, 98),

-- Alunos da Turma 3
(3, 40),
(3, 41),
(3, 43),
(3, 44),
(3, 51),
(3, 52),
(3, 59),
(3, 66),
(3, 67),
(3, 69),
(3, 85),
(3, 89),
(3, 97);


-- ============================
-- ASSOCIAÇÕES DE DISCIPLINAS, PROFESSORES E TURMAS
-- ============================
INSERT INTO turma_disciplina_professor (id_turma, id_disciplina, id_professor) VALUES
-- Turma 1
(1, 1, '45678901234'),  -- Matemática, Liam Gallagher
(1, 2, '56789012345'),  -- Física, Noel Gallagher
(1, 3, '67890123456'),  -- Química, Brandon Flowers
(1, 4, '78901234567'),  -- Biologia, Alex Turner
(1, 5, '89012345678'),  -- Geografia, Matt Bellamy
(1, 6, '90123456789'),  -- História, Chris Martin
(1, 7, '01234567890'),  -- Filosofia, Robert Smith
(1, 8, '12345098765'),  -- Sociologia, Billy Corgan
(1, 9, '98765432101'),  -- Inglês, Jack White
(1, 10, '19283746501'), -- Português, Kurt Cobain

-- Turma 2
(2, 1, '45678901234'),  -- Matemática, Liam Gallagher
(2, 2, '56789012345'),  -- Física, Noel Gallagher
(2, 3, '67890123456'),  -- Química, Brandon Flowers
(2, 4, '78901234567'),  -- Biologia, Alex Turner
(2, 5, '89012345678'),  -- Geografia, Matt Bellamy
(2, 6, '90123456789'),  -- História, Chris Martin
(2, 7, '01234567890'),  -- Filosofia, Robert Smith
(2, 8, '12345098765'),  -- Sociologia, Billy Corgan
(2, 9, '98765432101'),  -- Inglês, Jack White
(2, 10, '19283746501'), -- Português, Kurt Cobain

-- Turma 3
(3, 11, '45678901234'), -- Arte, Liam Gallagher
(3, 12, '56789012345'), -- Educação Física, Noel Gallagher
(3, 13, '67890123456'), -- Música, Brandon Flowers
(3, 14, '78901234567'), -- Cinema, Alex Turner
(3, 15, '89012345678'), -- Tecnologia da Informação, Matt Bellamy
(3, 16, '90123456789'), -- Robótica, Chris Martin
(3, 17, '01234567890'), -- Astronomia, Robert Smith
(3, 18, '12345098765'), -- Geometria, Billy Corgan
(3, 19, '98765432101'), -- Lógica de Programação, Jack White
(3, 20, '19283746501'); -- Cálculo Diferencial, Kurt Cobain




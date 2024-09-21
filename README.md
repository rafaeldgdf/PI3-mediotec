# Documentação Técnica - Package `projeto.integrador3.senac.mediotec.pi3_mediotec`

O package `projeto.integrador3.senac.mediotec.pi3_mediotec` é o ponto central de inicialização e configuração do projeto. Ele contém a classe principal que inicializa a aplicação Spring Boot, a configuração do Swagger para a documentação da API, e as configurações de CORS para permitir o acesso ao backend por diferentes origens. Abaixo, está a explicação técnica de cada arquivo presente no package, seguindo a ordem: **Classe Principal > Configuração Swagger > Configuração CORS**.

---

## `Pi3MediotecApplication.java` (Classe Principal)

### Descrição
A classe `Pi3MediotecApplication` é a **classe principal** da aplicação Spring Boot. Ela serve como o ponto de entrada para a inicialização da aplicação.

### Anotações
- `@SpringBootApplication`: É uma anotação composta que inclui várias outras anotações do Spring Boot:
  - `@Configuration`: Permite que a classe seja usada como fonte de definições de bean do Spring.
  - `@EnableAutoConfiguration`: Habilita a configuração automática do Spring Boot com base nas dependências adicionadas no projeto.
  - `@ComponentScan`: Permite a varredura de componentes, serviços e repositórios na aplicação.

### Método Principal
- `public static void main(String[] args)`: Método que inicia a aplicação, invocando `SpringApplication.run(Pi3MediotecApplication.class, args);`, o que inicializa o contexto do Spring e as configurações definidas.

---

## `SwaggerConfig.java` (Configuração do Swagger)

### Descrição
A classe `SwaggerConfig` é responsável por configurar o Swagger para a documentação da API REST da aplicação, usando a especificação OpenAPI 3.

### Anotações
- `@Configuration`: Indica que a classe contém definições de beans Spring e pode ser usada pelo contêiner de IoC (Inversão de Controle) do Spring.

### Método Principal
- `@Bean public OpenAPI customOpenAPI()`: Configura o Swagger com informações sobre o sistema, como título, descrição, versão, contato e links externos.

### Configurações
- **`title`**: Define o título da API como "SGE - Sistema de Gerenciamento Escolar".
- **`version`**: Define a versão da API como "1.0.0".
- **`description`**: Descreve o projeto como "Faculdade Senac Pernambuco - Projeto Integrador III".
- **`contact`**: Adiciona as informações de contato do desenvolvedor responsável (nome, URL do LinkedIn e e-mail).
- **`externalDocs`**: Adiciona um link para o repositório do projeto no GitHub.

### Regras de Negócio
- O Swagger é usado para documentar e expor os endpoints da API, facilitando a integração e testes das funcionalidades do sistema.

---

## `WebConfig.java` (Configuração de CORS)

### Descrição
A classe `WebConfig` configura o **Cross-Origin Resource Sharing (CORS)** para a aplicação, permitindo que diferentes origens (URLs de frontend) acessem os endpoints do backend.

### Anotações
- `@Configuration`: Indica que a classe define beans de configuração do Spring.

### Implementação
- `public void addCorsMappings(CorsRegistry registry)`: Método que configura os mapeamentos de CORS.
  - `registry.addMapping("/**")`: Aplica a configuração a todos os endpoints da API.
  - `.allowedOrigins("http://localhost:3000")`: Permite que o frontend hospedado em `http://localhost:3000` acesse os endpoints do backend.
  - `.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")`: Define os métodos HTTP permitidos.
  - `.allowedHeaders("*")`: Permite que todos os cabeçalhos sejam enviados nas requisições.
  - `.allowCredentials(true)`: Permite o envio de credenciais (cookies, cabeçalhos de autenticação).

### Regras de Negócio
- Permite o acesso do frontend (React, Angular, etc.) em `http://localhost:3000` aos endpoints da API.
- Define explicitamente os métodos HTTP que podem ser utilizados nas requisições ao backend.

---

## Conclusão

O package `projeto.integrador3.senac.mediotec.pi3_mediotec` é fundamental para a inicialização e configuração da aplicação. Ele inclui a classe principal `Pi3MediotecApplication` que inicializa o projeto Spring Boot, `SwaggerConfig` que documenta e expõe a API, e `WebConfig` que define as políticas de CORS para permitir a comunicação segura e controlada entre o frontend e backend.

Essas configurações garantem que o sistema seja inicializado corretamente, documentado de forma clara e acessível para desenvolvedores e clientes, e que as requisições sejam permitidas apenas de origens autorizadas, mantendo a segurança e integridade do projeto.

--- 

# Documentação Técnica - Package `aluno`

O package `aluno` é responsável pelo gerenciamento das entidades e operações relacionadas ao aluno no sistema. Ele lida com persistência de dados, regras de negócio, comunicação com o banco de dados e a exposição de APIs REST. Abaixo está a explicação técnica de cada arquivo presente no package, seguindo a ordem **Entidade > Repositório > Serviço > DTOs > Controlador**.

---

## `Aluno.java` (Entidade)

### Descrição:
A classe `Aluno` representa a **entidade** aluno que será persistida no banco de dados. Ela herda de `Usuario`, contendo atributos e relacionamentos específicos do aluno. É a base para todos os dados do aluno e suas interações com outras entidades.

### Anotações:
- `@Entity`: Define que a classe será uma entidade JPA (mapeada para uma tabela no banco de dados).
- `@Table(name = "aluno")`: Especifica o nome da tabela no banco de dados como "aluno".
- `@Id`: Define o campo `id` como a chave primária.
- `@GeneratedValue(strategy = GenerationType.IDENTITY)`: Gera automaticamente o valor da chave primária com a estratégia `IDENTITY`.
- `@OneToMany`, `@ManyToMany`, `@ManyToOne`: Indicam os relacionamentos com outras entidades, como endereços, telefones, turmas, coordenação, presenças e responsáveis.
- `@JsonIgnore`: Evita a serialização de certas propriedades para evitar loops ou retornos excessivos de dados.

### Relacionamentos:
- **Endereços** (`OneToMany`): Um aluno pode ter vários endereços.
- **Telefones** (`OneToMany`): Um aluno pode ter vários telefones.
- **Turmas** (`ManyToMany`): Um aluno pode estar matriculado em várias turmas.
- **Responsáveis** (`OneToMany`): Um aluno pode ter vários responsáveis.
- **Presenças** (`OneToMany`): Um aluno pode ter múltiplas presenças, mas isso é ignorado na serialização via `@JsonIgnore`.
- **Coordenação** (`ManyToOne`): Um aluno pode estar vinculado a uma coordenação.

### Regras de Negócio:
- O campo **CPF** é único e obrigatório.
- O status (`boolean`) indica se o aluno está **ativo** ou **inativo**. Ao cadastrar um aluno, o **status é automaticamente definido como `true`**, ou seja, o aluno é cadastrado como ativo no sistema.
- Um aluno precisa de ao menos um **responsável** no momento do cadastro, especialmente se for menor de idade.

### Métodos Auxiliares:
- `addEndereco(Endereco endereco)`: Adiciona um endereço ao aluno e configura a relação bidirecional.
- `addTelefone(Telefone telefone)`: Adiciona um telefone ao aluno e configura a relação bidirecional.
- `addTurma(Turma turma)`: Adiciona uma turma ao aluno e garante a bidirecionalidade.
- `addResponsavel(Responsavel responsavel)`: Adiciona um responsável ao aluno.

---

## `AlunoRepository.java` (Repositório)

### Descrição:
Essa interface define a camada de **persistência** dos dados do aluno, estendendo o **`JpaRepository`**. Ela permite a execução de operações de CRUD diretamente com o banco de dados, como buscar, salvar e deletar alunos.

### Anotações:
- `@Repository`: Não é obrigatório especificar aqui, pois o `JpaRepository` automaticamente configura a interface como um repositório do Spring Data.

### Métodos Principais:
- `findAll()`: Retorna uma lista de todos os alunos.
- `findById(Long id)`: Busca um aluno por seu ID.
- `existsByCpf(String cpf)`: Verifica se um aluno com o CPF fornecido já existe no banco.

### Regras de Negócio:
- **CPF único**: Antes de criar ou atualizar um aluno, é verificado se o CPF já existe no banco de dados, garantindo a unicidade.

---

## `AlunoService.java` (Serviço)

### Descrição:
A classe `AlunoService` contém a lógica de **negócio** relacionada ao aluno. Ela intermedia a comunicação entre o `AlunoRepository` e o `AlunoController`, aplicando as regras de negócio antes de persistir ou retornar dados.

### Anotações:
- `@Service`: Define que a classe é um serviço do Spring, gerenciada pelo framework e contendo a lógica de negócio.

### Métodos:

#### `getAllAlunos()`
- Retorna uma lista de todos os alunos cadastrados no banco de dados.
- Validações:
  - Nenhuma validação adicional, retorna todos os registros de alunos.

#### `getAlunoById(Long idAluno)`
- Busca um aluno por seu ID e retorna suas informações detalhadas.
- Validações:
  - Verifica se o aluno existe. Caso contrário, lança uma exceção.

#### `saveAluno(AlunoResumidoDTO2 alunoResumidoDTO)`
- Persiste um novo aluno no banco de dados.
- Regras de Negócio:
  - O **CPF** do aluno deve ser único.
  - O aluno deve ser criado com um status **true** automaticamente, indicando que ele está ativo.
  - O aluno precisa de **ao menos um responsável** para ser cadastrado.
  
#### `updateAluno(Long idAluno, AlunoResumidoDTO2 alunoResumidoDTO)`
- Atualiza um aluno existente no banco.
- Regras de Negócio:
  - O CPF não pode ser duplicado.
  - O ID e o CPF precisam ser consistentes com os registros existentes.
  - O aluno deve continuar com pelo menos um responsável.

#### `deleteAluno(Long idAluno)`
- Remove um aluno do banco pelo ID.
- Regras de Negócio:
  - Verifica se o aluno não possui vínculos obrigatórios, como presenças ou registros em turmas, que podem impedir a exclusão.

### Regras de Negócio:

#### Cadastro de Alunos (Mínimo e Máximo):
- **Mínimo**:
  - O aluno deve ter um **CPF único** e ao menos um **responsável**.
  - O aluno deve ser criado com um **status ativo** (true) automaticamente.
  
- **Máximo**:
  - O aluno pode ter múltiplos **endereços** e **telefones**.
  - O aluno pode estar vinculado a várias **turmas**.

#### Atualização:
- O CPF deve ser único e inalterado após o cadastro inicial.

#### Exclusão:
- Um aluno só pode ser excluído se não houver dependências bloqueantes, como vínculos obrigatórios.

---

## `AlunoDTO.java` (DTO)

### Descrição:
Essa classe é o **Data Transfer Object (DTO)** que representa os dados completos de um aluno. Ela é usada para enviar/receber dados entre a aplicação e os clientes via API.

### Atributos:
- Contém todos os dados do aluno, incluindo listas de endereços, telefones, turmas e responsáveis.
- É o DTO principal para as operações de **leitura** de dados detalhados sobre o aluno.

---

## `AlunoResumidoDTO.java` (DTO)

### Descrição:
Esse DTO contém dados resumidos do aluno e é utilizado principalmente para **criação** ou **atualização** de registros, oferecendo apenas os dados necessários.

### Atributos:
- Contém informações básicas como nome, CPF, data de nascimento, além de listas simplificadas de endereços, telefones, etc.

---

## `AlunoResumidoDTO2.java` (DTO)

### Descrição:
Um segundo tipo de **DTO resumido** que pode ser utilizado para operações de criação e atualização, similar ao `AlunoResumidoDTO`, mas com pequenas variações de atributos dependendo do contexto de uso.

---

## `AlunoReduzidoDTO.java` (DTO)

### Descrição:
Esse DTO é usado para representar um aluno de forma mais resumida, evitando o carregamento de dados complexos ou detalhados em cenários onde esses dados não são necessários.

### Atributos:
- Inclui apenas os campos essenciais, como nome, CPF, e status.
- Usado principalmente para exibições mais simples ou listas de alunos onde não se exige detalhamento.

---

## `AlunoController.java` (Controlador)

### Descrição:
A classe `AlunoController` expõe os endpoints REST para operações relacionadas ao aluno. Ela usa o **Spring Framework** para facilitar o gerenciamento de requisições HTTP e interage com o `AlunoService`.

### Anotações:
- `@RestController`: Indica que a classe é um controlador REST, permitindo retornar dados JSON.
- `@RequestMapping("/alunos")`: Define que todas as rotas desse controlador começam com `/alunos`.
- `@Tag(name = "Aluno", description = "Operações relacionadas a Alunos")`: Anotação para a documentação Swagger, que agrupa as operações desse controlador.

### Rotas (Endpoints):

#### GET

- **GET `/alunos`**: Lista todos os alunos.
- **GET `/alunos/{id}`**: Busca um aluno pelo ID.
- **GET `/alunos/{idAluno}/conceitos`**: Lista todos os conceitos de um aluno.
- **GET `/alunos/{idAluno}/conceitos/disciplina/{idDisciplina}`**: Busca os conceitos de um aluno para uma disciplina específica.

#### POST

- **POST `/alunos`**: Cria um novo aluno com base nos dados fornecidos.
  - Regras de Negócio:
    - O CPF deve ser único.
    - O aluno deve ser criado com status `true` automaticamente.

#### PUT

- **PUT `/alunos/{id}`**: Atualiza as informações de um aluno existente.
  - Regras de Negócio:
    - O CPF não pode ser duplicado.
    - O aluno deve ter ao menos um responsável.

#### DELETE

- **DELETE `/alunos/{id}`**: Remove um aluno pelo ID.
  - Regras de Negócio:
    - O aluno só pode ser deletado se não houver vínculos obrigatórios, como presença ou turmas ativas.

---

## Conclusão

O package `aluno` é central para o gerenciamento de alunos no sistema, abrangendo desde a camada de persistência de dados até a exposição de APIs REST. As classes estão estruturadas de maneira modular, separando claramente a lógica de negócio (via `AlunoService`), a comunicação com o banco de dados (via `AlunoRepository`) e a definição de rotas REST (via `AlunoController`).

Os **DTOs** desempenham um papel importante na separação de responsabilidades, garantindo que apenas os dados necessários sejam trafegados nas requisições e respostas. O status do aluno, ao ser cadastrado, é definido automaticamente como `true` (ativo).



# Documentação Técnica - Package `comunicado`

O package `comunicado` é responsável pelo gerenciamento das operações e dados relacionados aos **comunicados** no sistema. O package inclui as classes de entidade, repositório, serviço, DTOs e o controlador. Abaixo está a explicação técnica de cada arquivo presente no package, seguindo a ordem **Entidade > Repositório > Serviço > DTOs > Controlador**.

---

## `Comunicado.java` (Entidade)

### Descrição:
A classe `Comunicado` é a **entidade** que representa um comunicado no sistema. Um comunicado pode ser enviado por um professor ou coordenador e pode ser destinado a vários alunos e turmas. Os dados incluem o conteúdo, data de envio, remetente e destinatários.

### Anotações:
- `@Entity`: Define que a classe será uma entidade JPA (mapeada para uma tabela no banco de dados).
- `@Id`: Define o campo `id` como a chave primária.
- `@GeneratedValue(strategy = GenerationType.IDENTITY)`: Gera automaticamente o valor da chave primária.
- `@Column`: Especifica o nome da coluna no banco de dados e define que alguns campos não podem ser nulos (`nullable = false`).
- `@ManyToOne`: Define o relacionamento de muitos para um entre a entidade `Comunicado` e o remetente (professor ou coordenador).
- `@ElementCollection`: Define coleções de elementos simples como listas de IDs, representando os destinatários (alunos e turmas) do comunicado.

### Atributos:
- **`id`** (`Long`): Chave primária, gerada automaticamente.
- **`conteudo`** (`String`): Conteúdo textual do comunicado.
- **`dataEnvio`** (`LocalDateTime`): Data e hora em que o comunicado foi enviado.
- **`remetenteProfessor`** (`Professor`): Professor que enviou o comunicado (opcional).
- **`remetenteCoordenacao`** (`Coordenacao`): Coordenação que enviou o comunicado (opcional).
- **`receptorAlunos`** (`List<Long>`): Lista de IDs dos alunos que receberam o comunicado.
- **`receptorTurmas`** (`List<Long>`): Lista de IDs das turmas que receberam o comunicado.

### Relacionamentos:
- **Professor** (`ManyToOne`): Um professor pode enviar vários comunicados, mas um comunicado só pode ter um remetente professor.
- **Coordenação** (`ManyToOne`): Uma coordenação pode enviar vários comunicados, mas um comunicado só pode ter uma coordenação como remetente.
- **Alunos e Turmas** (`ElementCollection`): Um comunicado pode ser enviado para vários alunos e turmas.

---

## `ComunicadoRepository.java` (Repositório)

### Descrição:
A interface `ComunicadoRepository` é responsável pela camada de persistência dos comunicados no banco de dados. Ela estende o `JpaRepository` do Spring Data JPA, fornecendo métodos prontos para operações CRUD (Create, Read, Update, Delete).

### Métodos Principais:
- **`findByRemetenteProfessor_Cpf(String cpf)`**: Retorna todos os comunicados enviados por um professor específico, identificado pelo CPF.
- **`findByReceptorAlunosContaining(Long alunoId)`**: Retorna todos os comunicados que tenham um determinado aluno como destinatário.
- **`findByReceptorTurmasContaining(Long turmaId)`**: Retorna todos os comunicados que tenham uma determinada turma como destinatária.

---

## `ComunicadoService.java` (Serviço)

### Descrição:
A classe `ComunicadoService` contém a lógica de negócio associada aos comunicados. Ela é responsável por validar as operações e chamar os métodos do repositório para manipular os dados no banco.

### Métodos

#### Criação de Comunicados
- **`criarComunicadoPorCoordenador(Long coordenacaoId, String coordenadorId, ComunicadoDTO comunicadoDTO)`**: Cria um comunicado emitido por um coordenador. Valida se o coordenador está vinculado à coordenação.
- **`criarComunicadoPorProfessor(String professorId, ComunicadoDTO comunicadoDTO)`**: Cria um comunicado emitido por um professor. Valida o professor pelo CPF.
- **`criarComunicadoParaTodos(Long coordenacaoId, String coordenadorId, ComunicadoDTO comunicadoDTO)`**: Cria um comunicado destinado a todos os alunos e turmas do sistema.

#### Atualização de Comunicados
- **`atualizarComunicadoPorCoordenador(Long coordenacaoId, String coordenadorId, Long comunicadoId, ComunicadoDTO comunicadoDTO)`**: Atualiza um comunicado enviado por um coordenador.
- **`atualizarComunicadoPorProfessor(String professorId, Long comunicadoId, ComunicadoDTO comunicadoDTO)`**: Atualiza um comunicado enviado por um professor.

#### Remoção de Comunicados
- **`deletarComunicadoPorCoordenador(Long coordenacaoId, String coordenadorId, Long comunicadoId)`**: Remove um comunicado enviado por um coordenador.
- **`deletarComunicadoPorProfessor(String professorId, Long comunicadoId)`**: Remove um comunicado enviado por um professor.

#### Listagem de Comunicados
- **`listarComunicadosPorCoordenador(Long coordenadorId)`**: Lista todos os comunicados enviados por um coordenador.
- **`listarComunicadosPorProfessor(String professorId)`**: Lista todos os comunicados enviados por um professor.
- **`listarComunicadosPorAluno(Long alunoId)`**: Lista todos os comunicados que foram enviados para um aluno específico.
- **`listarComunicadosPorTurma(Long turmaId)`**: Lista todos os comunicados que foram enviados para uma turma específica.
- **`listarComunicadosParaAlunos()`**: Lista todos os comunicados que tenham alunos como destinatários.
- **`listarComunicadosParaTurmas()`**: Lista todos os comunicados que tenham turmas como destinatárias.

---

## `ComunicadoDTO.java` (DTO)

### Descrição:
A classe `ComunicadoDTO` é um **Data Transfer Object (DTO)** utilizado para transferir dados de um comunicado entre o frontend e o backend. Ele contém os dados necessários para criar ou atualizar um comunicado.

### Atributos:
- **`conteudo`** (`String`): O conteúdo textual do comunicado.
- **`dataEnvio`** (`LocalDateTime`): A data de envio do comunicado.
- **`alunoIds`** (`List<Long>`): Lista de IDs de alunos destinatários do comunicado.
- **`turmaIds`** (`List<Long>`): Lista de IDs de turmas destinatárias do comunicado.

---

## `ComunicadoSimplesDTO.java` e `ComunicadoDetalhadoDTO.java` (DTOs)

### Descrição:
- **`ComunicadoSimplesDTO`**: Um DTO básico que contém apenas as informações mais importantes do comunicado, como ID, conteúdo e data de envio.
- **`ComunicadoDetalhadoDTO`**: Um DTO mais detalhado que inclui informações sobre o remetente, os destinatários (alunos e turmas), e outros detalhes.

### Atributos (ComunicadoSimplesDTO):
- **`id`** (`Long`): ID do comunicado.
- **`conteudo`** (`String`): O conteúdo textual do comunicado.
- **`dataEnvio`** (`LocalDateTime`): Data e hora de envio do comunicado.

### Atributos (ComunicadoDetalhadoDTO):
- **`id`** (`Long`): ID do comunicado.
- **`conteudo`** (`String`): O conteúdo textual do comunicado.
- **`dataEnvio`** (`LocalDateTime`): Data e hora de envio do comunicado.
- **`remetenteProfessor`** (`ProfessorResumidoDTO`): Informações resumidas sobre o professor remetente (se aplicável).
- **`remetenteCoordenacao`** (`CoordenacaoResumidaDTO`): Informações resumidas sobre a coordenação remetente (se aplicável).
- **`alunos`** (`List<AlunoResumidoDTO>`): Lista de alunos destinatários.
- **`turmas`** (`List<TurmaResumidaDTO>`): Lista de turmas destinatárias.

---

## `ComunicadoController.java` (Controlador)

### Descrição:
A classe `ComunicadoController` expõe as rotas REST que permitem a criação, atualização, remoção e listagem de comunicados. Ela utiliza os serviços fornecidos pela classe `ComunicadoService` e é responsável por responder às requisições HTTP.

### Anotações:
- `@RestController`: Indica que a classe é um controlador REST.
- `@RequestMapping("/comunicados")`: Define que todas as rotas dentro dessa classe começam com `/comunicados`.
- `@Tag(name = "Comunicados", description = "Operações relacionadas aos comunicados")`: Anotação utilizada para documentação Swagger, agrupando todas as rotas sob a tag "Comunicados".

### Rotas (Endpoints):

#### POST
- **POST `/coordenacao/{coordenacaoId}/coordenador/{coordenadorId}`**: Cria um comunicado enviado por um coordenador.
- **POST `/professor/{professorId}`**: Cria um comunicado enviado por um professor.
- **POST `/coordenacao/{coordenacaoId}/coordenador/{coordenadorId}/todos`**: Cria um comunicado enviado para todos os alunos e turmas.

#### PUT
- **PUT `/coordenacao/{coordenacaoId}/coordenador/{coordenadorId}/{comunicadoId}`**: Atualiza um comunicado enviado por um coordenador.
- **PUT `/professor/{professorId}/{comunicadoId}`**: Atualiza um comunicado enviado por um professor.

#### DELETE
- **DELETE `/coordenacao/{coordenacaoId}/coordenador/{coordenadorId}/{comunicadoId}`**: Remove um comunicado enviado por um coordenador.
- **DELETE `/professor/{professorId}/{comunicadoId}`**: Remove um comunicado enviado por um professor.

#### GET
- **`/`**: Lista todos os comunicados.
- **`/alunos`**: Lista todos os comunicados enviados para alunos.
- **`/aluno/{alunoId}`**: Lista comunicados enviados para um aluno específico.
- **`/turmas`**: Lista todos os comunicados enviados para turmas.
- **`/turma/{turmaId}`**: Busca comunicados enviados para uma turma específica.
- **`/coordenador/{coordenadorId}`**: Lista todos os comunicados enviados por um coordenador.
- **`/professor/{professorId}`**: Lista todos os comunicados enviados por um professor.

---

## Conclusão

O package `comunicado` é responsável pelo gerenciamento completo dos comunicados no sistema, permitindo que professores e coordenadores enviem notificações para alunos e turmas. Através de uma estrutura modular, o package engloba desde a camada de persistência com `ComunicadoRepository`, a lógica de negócio com `ComunicadoService`, até a exposição de APIs REST com `ComunicadoController`. A integração dos DTOs simplifica a transferência de dados entre o backend e o frontend, garantindo que apenas as informações necessárias sejam manipuladas. Essa abordagem modular facilita o gerenciamento eficiente e seguro dos comunicados, mantendo a flexibilidade de destinatários (alunos e turmas) e remetentes (professores e coordenadores).




# Documentação Técnica - Package `conceito`

O package `conceito` é responsável pela gestão e cálculo dos **conceitos** (notas) dos alunos em relação às disciplinas em que estão matriculados. Este package lida com a persistência das notas, o cálculo dos conceitos (como Excelente, Ótimo, Bom, etc.), e o status de aprovação dos alunos com base nas suas notas finais. Além disso, ele implementa uma **regra de recuperação** chamada **Nova Oportunidade de Aprendizado (NOA)**, permitindo que alunos melhorem suas notas com avaliações adicionais.

O fluxo de cálculo e as regras de negócio são fundamentais para garantir que os alunos sejam avaliados corretamente, levando em conta tanto as notas ao longo do ano quanto possíveis oportunidades de recuperação.

Abaixo está a explicação detalhada das classes e componentes do package, seguindo a ordem **Entidade > Repositório > Serviço > DTOs > Controlador**, com foco especial nos **serviços** e **regras de negócio**.

---

## `Conceito.java` (Entidade)

### Descrição:
A classe `Conceito` armazena todas as **notas** e **conceitos** atribuídos aos alunos para cada disciplina ao longo do ano letivo. Ela registra as notas das quatro unidades, além das notas de recuperação (NOA) e o status de aprovação baseado nas médias calculadas.

### Anotações:
- `@Entity`: Define que esta classe será uma entidade JPA, persistida em uma tabela do banco de dados.
- `@Table(name = "conceito")`: Especifica que os dados dessa classe serão armazenados na tabela `conceito`.
- `@Id`: Define o campo `id_conceito` como a chave primária.
- `@GeneratedValue(strategy = GenerationType.IDENTITY)`: Define que o campo será auto-gerado pela estratégia `IDENTITY`.

### Relacionamentos:
- **Aluno** (`ManyToOne`): Um conceito é atribuído a um único aluno.
- **TurmaDisciplinaProfessor** (`ManyToOne`): O conceito está associado a uma combinação específica de **turma**, **disciplina**, e **professor**.

### Campos:
- **Notas e Conceitos**:
  - `notaUnidade1`, `notaUnidade2`, `notaUnidade3`, `notaUnidade4`: Representam as notas numéricas das quatro unidades.
  - `noa1`, `noa2`, `noaFinal`: Notas de recuperação (NOA), dadas quando o aluno tem oportunidade de melhorar suas notas.
  - `conceitoNota1`, `conceitoNota2`, etc.: Conceitos descritivos baseados nas notas numéricas, como "Excelente", "Ótimo", "Bom".
  - `mediaFinal`: Média das notas após o cálculo das quatro unidades e aplicação das regras de recuperação.
  - `aprovado`: Status de aprovação, definido com base na média final (média >= 7 significa aprovação).

---

## `ConceitoRepository.java` (Repositório)

### Descrição:
O repositório `ConceitoRepository` é responsável por interagir diretamente com o banco de dados para **persistência** e **consulta** dos conceitos. Ele estende o `JpaRepository`, o que permite realizar operações de CRUD (Create, Read, Update, Delete) facilmente.

### Métodos Principais:
- `findById(Long id)`: Busca um conceito específico pelo seu ID.
- `findByAluno_Id(Long idAluno)`: Busca todos os conceitos de um aluno específico.
- `findByAluno_IdAndTurmaDisciplinaProfessor_Disciplina_Id(Long idAluno, Long idDisciplina)`: Busca os conceitos de um aluno para uma disciplina específica.
- `findByTurmaDisciplinaProfessor_Id(TurmaDisciplinaProfessorId id)`: Retorna todos os conceitos de uma turma específica em uma disciplina.

---

## `ConceitoService.java` (Serviço)

### Descrição:
A classe `ConceitoService` contém a **lógica de negócio** para gerenciar as operações dos conceitos dos alunos. Essa camada de serviço realiza operações de cálculo de notas, gerenciamento de recuperações (NOA), e garante a correta persistência dos dados de acordo com as regras de negócio.

### Métodos e Regras de Negócio:

#### 1. **`salvarConceito(ConceitoResumidoDTO conceitoResumidoDTO)`**
- Este método salva um novo conceito no banco de dados. Ele vincula o conceito a um aluno e a uma combinação de turma, disciplina e professor.
- **Regras de Negócio**:
  - O conceito é criado com as notas fornecidas para as quatro unidades.
  - A recuperação pode ser aplicada automaticamente ao salvar o conceito, recalculando as menores notas com base nos NOAs (caso estejam disponíveis).
  - O conceito final é gerado com base nas notas numéricas.

#### 2. **`atualizarConceito(Long id, ConceitoResumidoDTO conceitoResumidoDTO)`**
- Atualiza um conceito existente no banco de dados. As notas fornecidas são atualizadas e o sistema recalcula a média e o status de aprovação.
- **Regras de Negócio**:
  - Após a atualização das notas, os conceitos descritivos (como "Excelente", "Ótimo", etc.) são recalculados.
  - Caso o aluno tenha notas de recuperação, as regras de substituição de notas (descritas abaixo) são aplicadas novamente.

#### 3. **`deletarConceito(Long id)`**
- Remove um conceito existente.
- **Regras de Negócio**:
  - A exclusão é permitida apenas se o conceito estiver devidamente referenciado e não impactar outras relações no sistema.

---

### **Cálculo de Conceitos e Regras de Negócio**

#### 1. **Cálculo de Conceitos Descritivos**:
O sistema converte notas numéricas em **conceitos descritivos** com base em faixas de valores. Os conceitos são:
- **Excelente (E)**: Nota >= 9.5
- **Ótimo (O)**: Nota >= 8.5
- **Bom (B)**: Nota >= 7.0
- **Ainda Não Suficiente (ANS)**: Nota >= 5.0
- **Insuficiente (I)**: Nota < 5.0

Este cálculo é aplicado para cada uma das quatro unidades, bem como para as notas de recuperação (NOA) e média final.

#### 2. **Regras de Recuperação (NOA)**:
Os alunos têm a possibilidade de melhorar suas notas por meio de **NOA (Nova Oportunidade de Aprendizado)**. As **NOAs** são notas adicionais que podem substituir as menores notas do semestre, com o objetivo de aumentar a média final.

**Funcionamento das NOAs**:
- **NOA1**: Aplica-se às notas da 1ª e 2ª unidades. Se o aluno tirar uma nota de NOA1 superior à sua menor nota entre essas duas unidades, a nota mais baixa é substituída pela NOA1.
- **NOA2**: Aplica-se às notas da 3ª e 4ª unidades. Mesma lógica de substituição, porém entre as notas da 3ª e 4ª unidades.
- **NOA Final**: Caso a média final seja inferior a 7 e maior ou igual a 5, e o aluno tenha uma nota de NOA Final, essa nota é usada para recalcular a média final.

**Exemplo**:
- Se o aluno tirou **5.0** na 1ª unidade e **7.0** na 2ª unidade, e obteve **6.5** na NOA1, o sistema substituirá a nota **5.0** pela **6.5**, aumentando a média final.

#### 3. **Cálculo da Média Final e Aprovação**:
Após as substituições das notas pelas NOAs, a média final é calculada como a média aritmética das quatro unidades. A regra básica é:
- **Aprovação**: Média final >= 7.
- **Recuperação**: Média entre 5 e 6.9, e o aluno deve ter uma nota de recuperação (NOA Final).

Se, após a aplicação das NOAs, a média final ainda for inferior a 7, o status do aluno será marcado como **reprovado**.

---

## **Exemplo Completo de Cálculo**:
1. **Notas originais**:
   - 1ª unidade: 5.0
   - 2ª unidade: 7.0
   - 3ª unidade: 6.0
   - 4ª unidade: 8.0

2. **Aplicação de NOAs**:
   - NOA1: 6.5 (substitui a nota de 5.0 da 1ª unidade)
   - NOA2: Não aplicado (pois as notas da 3ª e 4ª unidades são adequadas).

3. **Média Final**:
   - Média final = (6.5 + 7.0 + 6.0 + 8.0) / 4 = **6.875** (Aluno aprovado, caso tenha NOA Final para recalcular a média).

4. **Conceito Final**:
   - Com base na média de 6.875, o conceito final seria: **Bom (B)**.

---

## DTOs (Data Transfer Objects)

# ConceitoResumidoDTO.java (DTO)

## Descrição:
O `ConceitoResumidoDTO` é um DTO usado para **criação** e **atualização** de conceitos. Ele contém apenas os dados essenciais necessários para realizar essas operações, sem incluir informações mais detalhadas ou relacionamentos complexos.

## Atributos:
- `alunoId`: O ID do aluno ao qual o conceito está sendo atribuído.
- `turmaId`: O ID da turma em que o conceito foi registrado.
- `disciplinaId`: O ID da disciplina relacionada ao conceito.
- `professorId`: O ID do professor que registrou o conceito.
- `notaUnidade1`, `notaUnidade2`, `notaUnidade3`, `notaUnidade4`: As notas das unidades fornecidas ao registrar ou atualizar um conceito.
- `noa1`, `noa2`, `noaFinal`: As notas de recuperação (NOA) fornecidas, se aplicáveis.

# ConceitoInputDTO.java (DTO)

## Descrição:
O `ConceitoInputDTO` é um DTO específico para entrada de dados, utilizado quando um professor está inserindo ou modificando as notas de um aluno em uma turma e disciplina específica. Ele permite que as notas de cada unidade e as notas de recuperação (NOA) sejam registradas ou atualizadas.

## Atributos:
- `notaUnidade1`, `notaUnidade2`, `notaUnidade3`, `notaUnidade4`: Notas das quatro unidades que podem ser atualizadas.
- `noa1`, `noa2`, `noaFinal`: Notas de recuperação, fornecidas pelo professor para substituir notas mais baixas.

# ConceitoOutputDTO.java (DTO)

## Descrição:
O `ConceitoOutputDTO` é um DTO usado para **exibir** os dados de saída relacionados aos conceitos, principalmente após a realização de uma operação de leitura ou atualização. Ele pode conter informações mais completas e formatadas, de forma que sejam apresentadas adequadamente para os usuários.

## Atributos:
- Contém todos os dados do `ConceitoDTO`, porém formatado e apresentado de forma mais organizada para exibição em relatórios ou interfaces de usuário.



# ConceitoController.java (Controlador)

## Descrição:
O `ConceitoController` expõe os **endpoints REST** que permitem realizar operações relacionadas aos conceitos dos alunos, como criar, atualizar, deletar e consultar conceitos. Ele utiliza o **Spring Framework** para gerenciar as requisições HTTP e se comunica diretamente com o `ConceitoService` para aplicar as regras de negócio.

## Anotações:
- `@RestController`: Indica que a classe é um controlador REST, permitindo retornar dados em formato JSON.
- `@RequestMapping("/conceitos")`: Define que todas as rotas deste controlador começam com `/conceitos`.
- `@Tag(name = "Conceitos", description = "Operações relacionadas aos conceitos dos alunos")`: Define o nome e a descrição do grupo de rotas no Swagger.

## Rotas (Endpoints):

### **GET** `/conceitos/{id}`

- **Objetivo**: Buscar um conceito pelo seu ID.
- **Descrição**: Busca e retorna um conceito específico com base no seu identificador único.
- **Retorno**: Um objeto `ConceitoDTO` contendo as informações detalhadas do conceito.
- **Erros**:
  - Retorna **404 (Not Found)** se o conceito com o ID fornecido não for encontrado.

### **POST** `/conceitos`

- **Objetivo**: Criar um novo conceito.
- **Descrição**: Cria um novo conceito com base nos dados fornecidos via `ConceitoResumidoDTO`. O serviço calcula os conceitos descritivos e a média final automaticamente.
- **Regras de Negócio**:
  - As notas das unidades e NOAs fornecidas serão usadas para calcular a média final e o conceito final.
  - O conceito é vinculado a um aluno, professor, turma e disciplina específicos.
- **Erros**:
  - Retorna **400 (Bad Request)** se houver algum erro na criação do conceito, como aluno, turma, ou professor não encontrados.

### **PUT** `/conceitos/{id}`

- **Objetivo**: Atualizar um conceito existente.
- **Descrição**: Atualiza um conceito existente com base no ID fornecido e nos dados enviados via `ConceitoResumidoDTO`.
- **Regras de Negócio**:
  - Recalcula a média e conceitos após a atualização das notas.
- **Erros**:
  - Retorna **404 (Not Found)** se o conceito com o ID fornecido não for encontrado.

### **DELETE** `/conceitos/{id}`

- **Objetivo**: Deletar um conceito pelo seu ID.
- **Descrição**: Remove um conceito existente com base no ID fornecido.
- **Erros**:
  - Retorna **404 (Not Found)** se o conceito com o ID fornecido não for encontrado.


---

## Conclusão

O package `conceito` lida com um dos aspectos mais complexos do sistema, sendo responsável pela **gestão das notas**, **recuperações** e **status de aprovação** dos alunos. Ele implementa regras robustas para garantir que os alunos sejam avaliados de forma justa, permitindo que eles melhorem suas notas por meio de **recuperações (NOAs)**. Além disso, as operações expostas via API oferecem flexibilidade para a criação, atualização e consulta de conceitos, garantindo transparência para professores e alunos.

Com o uso de **DTOs**, o sistema consegue trafegar dados de maneira eficiente, e as regras de negócio garantem que as notas sejam tratadas de acordo com as políticas de avaliação da instituição.

---

# Documentação Técnica - Package `coordenacao`

O package `coordenacao` é responsável pelo gerenciamento das operações relacionadas às **Coordenações** no sistema, incluindo CRUD de coordenações, e suas associações com **professores**, **coordenadores**, **turmas**, **endereços** e **telefones**. Abaixo está a explicação técnica de cada arquivo presente no package, seguindo a ordem **Entidade > Repositório > Serviço > DTOs > Controlador**.

---

## `Coordenacao.java` (Entidade)

### Descrição:
A classe `Coordenacao` representa a **entidade** que será persistida no banco de dados, modelando uma Coordenação e suas associações com outras entidades, como coordenadores, professores e turmas.

### Anotações:
- `@Entity`: Define que a classe será uma entidade JPA.
- `@Table(name = "coordenacao")`: Especifica o nome da tabela no banco de dados.
- `@Id`: Define o campo `id` como a chave primária.
- `@GeneratedValue(strategy = GenerationType.IDENTITY)`: Gera automaticamente o valor da chave primária.
- `@OneToMany`: Especifica relações **Um-para-Muitos** entre Coordenação e outras entidades, como endereços, telefones, coordenadores, professores e turmas.
- `@JsonIgnore`: Evita a serialização de relações potencialmente cíclicas, como com professores e turmas.

### Relacionamentos:
- **Endereços** (`OneToMany`): Uma coordenação pode ter vários endereços.
- **Telefones** (`OneToMany`): Uma coordenação pode ter vários telefones.
- **Coordenadores** (`OneToMany`): Uma coordenação pode ter vários coordenadores.
- **Turmas** (`OneToMany`): Uma coordenação pode ter várias turmas.
- **Professores** (`OneToMany`): Uma coordenação pode ter vários professores.

### Funções Bidirecionais:
- `addEndereco()`: Adiciona um endereço à coordenação, configurando a relação bidirecional.
- `addTelefone()`: Adiciona um telefone à coordenação, garantindo a relação bidirecional.
- `addCoordenador()`: Associa um coordenador à coordenação, assegurando bidirecionalidade.

### Regras de Negócio:
- A **Coordenação** precisa ter pelo menos um **nome** (entre 3 e 50 caracteres) e pode ter uma **descrição** opcional.
- Cada coordenação pode ter várias turmas, coordenadores, professores, telefones e endereços associados.

---

## `CoordenacaoRepository.java` (Repositório)

### Descrição:
A interface `CoordenacaoRepository` define a camada de **persistência** dos dados de uma coordenação, estendendo `JpaRepository`. Permite a execução de operações de CRUD diretamente com o banco de dados.

### Métodos Principais:
- `findAll()`: Retorna uma lista de todas as coordenações cadastradas no banco.
- `findById(Long id)`: Busca uma coordenação específica pelo ID.

### Regras de Negócio:
- O **nome da coordenação** é obrigatório.

---

## `CoordenacaoService.java` (Serviço)

### Descrição:
A classe `CoordenacaoService` contém a lógica de **negócio** relacionada às coordenações. Ela intermedia a comunicação entre o `CoordenacaoRepository` e o `CoordenacaoController`, aplicando as regras de negócio antes de persistir ou retornar dados.

### Anotações:
- `@Service`: Define que a classe é um serviço do Spring, gerenciada pelo framework.

### Métodos:

#### `getAllCoordenacoes()`
- Retorna uma lista de todas as coordenações cadastradas no banco de dados.

#### `getCoordenacaoById(Long id)`
- Busca uma coordenação por seu ID e retorna suas informações detalhadas.

#### `saveCoordenacao(CoordenacaoCadastroDTO coordenacaoDTO)`
- Persiste uma nova coordenação no banco de dados.
- Regras de Negócio:
  - A coordenação deve ter **nome** e pode ter **descrição** opcional.
  - Pode associar **endereços**, **telefones**, **coordenadores**, **professores** e **turmas** via seus IDs.
  - As relações são configuradas de forma bidirecional.

#### `updateCoordenacao(Long id, CoordenacaoCadastroDTO coordenacaoDTO)`
- Atualiza uma coordenação existente no banco.
- Regras de Negócio:
  - Pode atualizar as associações com endereços, telefones, coordenadores, professores e turmas.

#### `deleteCoordenacao(Long id)`
- Remove uma coordenação do banco pelo ID.
  
### Regras de Negócio:

#### Cadastro e Atualização:
- **Nome** é obrigatório, com no mínimo 3 caracteres e no máximo 50.
- **Descrição** é opcional.
- A coordenação pode associar vários coordenadores, professores, turmas, endereços e telefones.

---

## DTOs (Data Transfer Objects)

### `CoordenacaoDTO.java`
- Este DTO é utilizado para **transferência de dados** completos da coordenação. Ele contém informações detalhadas sobre a coordenação, como nome, descrição, e suas associações com endereços, telefones, coordenadores, professores e turmas.

### `CoordenacaoCadastroDTO.java`
- Este DTO é utilizado para **criação** ou **atualização** de uma coordenação. Ele contém os atributos necessários para cadastrar ou atualizar uma coordenação, incluindo os IDs dos coordenadores, professores e turmas que serão associados.

---

## `CoordenacaoController.java` (Controlador)

### Descrição:
A classe `CoordenacaoController` expõe os endpoints REST para operações relacionadas às coordenações. Ela usa o **Spring Framework** para facilitar o gerenciamento de requisições HTTP e interage com o `CoordenacaoService`.

### Anotações:
- `@RestController`: Define que a classe é um controlador REST, permitindo retornar dados JSON.
- `@RequestMapping("/coordenacoes")`: Define que todas as rotas desse controlador começam com `/coordenacoes`.
- `@Tag(name = "Coordenação", description = "Gerenciamento das Coordenações")`: Anotação para a documentação Swagger, que agrupa as operações desse controlador.

### Rotas (Endpoints):

#### GET

- **GET `/coordenacoes`**: Lista todas as coordenações.
- **GET `/coordenacoes/{id}`**: Busca uma coordenação pelo ID.

#### POST

- **POST `/coordenacoes`**: Cria uma nova coordenação com base nos dados fornecidos.

#### PUT

- **PUT `/coordenacoes/{id}`**: Atualiza as informações de uma coordenação existente.

#### DELETE

- **DELETE `/coordenacoes/{id}`**: Remove uma coordenação pelo ID.

---

## Conclusão

O package `coordenacao` é responsável pelo gerenciamento completo das **Coordenações** no sistema. Ele inclui funcionalidades para cadastrar, atualizar, buscar e deletar coordenações, além de associar coordenadores, professores, turmas, endereços e telefones à coordenação. O código está estruturado para garantir a bidirecionalidade das associações, utilizando o Spring para facilitar as operações e a JPA para persistência de dados no banco.

---

# Documentação Técnica - Package `coordenador`

O package `coordenador` é responsável pelo gerenciamento das entidades e operações relacionadas aos coordenadores do sistema. Ele lida com persistência de dados, regras de negócio, comunicação com o banco de dados e a exposição de APIs REST. Abaixo está a explicação técnica de cada arquivo presente no package, seguindo a ordem **Entidade > Repositório > Serviço > DTOs > Controlador**.

---

## `Coordenador.java` (Entidade)

### Descrição:
A classe `Coordenador` representa a **entidade coordenador** que herda da classe `Usuario`. Cada coordenador está associado a uma **Coordenação**, além de possuir **endereços** e **telefones**.

### Anotações:
- `@Entity`: Define que a classe será uma entidade JPA, mapeada para uma tabela no banco de dados.
- `@Table(name = "coordenador")`: Especifica que essa entidade será persistida na tabela `coordenador`.
- `@Id`: Define o campo `cpf` como a chave primária.
- `@NotNull`, `@Size`: Definem regras de validação para os campos como CPF.

### Relacionamentos:
- **Endereços** (`OneToMany`): Um coordenador pode ter vários endereços. O relacionamento é bidirecional.
- **Telefones** (`OneToMany`): Um coordenador pode ter vários telefones. O relacionamento também é bidirecional.
- **Coordenação** (`ManyToOne`): Um coordenador está vinculado a uma coordenação.

### Métodos Auxiliares:
- `addEndereco(Endereco endereco)`: Adiciona um endereço ao coordenador e configura a relação bidirecional.
- `addTelefone(Telefone telefone)`: Adiciona um telefone ao coordenador e configura a relação bidirecional.

---

## `CoordenadorRepository.java` (Repositório)

### Descrição:
Essa interface define a camada de **persistência** dos dados do coordenador. Estende a interface `JpaRepository`, o que permite a execução de operações de CRUD diretamente com o banco de dados.

### Métodos Principais:
- `findAll()`: Retorna uma lista de todos os coordenadores.
- `findById(String cpf)`: Busca um coordenador pelo CPF.
- `existsByCpf(String cpf)`: Verifica se um coordenador com o CPF fornecido já existe no banco.

---

## `CoordenadorService.java` (Serviço)

### Descrição:
A classe `CoordenadorService` contém a lógica de **negócio** relacionada ao coordenador. Ela realiza operações como buscar, salvar, atualizar e deletar coordenadores, interagindo com o `CoordenadorRepository` e, quando necessário, com outros repositórios como o de **Coordenação**.

### Métodos:

#### `getAllCoordenadores()`
- Retorna uma lista de todos os coordenadores cadastrados no banco de dados.
- Validações:
  - Nenhuma validação adicional, retorna todos os registros de coordenadores.

#### `getCoordenadorById(String cpf)`
- Busca um coordenador pelo CPF e retorna seus detalhes.
- Validações:
  - Verifica se o coordenador existe. Caso contrário, lança uma exceção.

#### `saveCoordenador(CoordenadorDTO coordenadorDTO)`
- Persiste um novo coordenador no banco de dados.
- Regras de Negócio:
  - O CPF do coordenador deve ser **único**.
  - Um coordenador pode ser associado a uma **coordenação**, além de possuir **endereços** e **telefones**.

#### `updateCoordenador(String cpf, CoordenadorDTO coordenadorDTO)`
- Atualiza um coordenador existente no banco de dados.
- Regras de Negócio:
  - O CPF não pode ser alterado.
  - O coordenador pode ser atualizado com novos endereços, telefones e coordenação.

#### `deleteCoordenador(String cpf)`
- Remove um coordenador do banco pelo CPF.
- Validações:
  - Verifica se o coordenador existe antes de removê-lo.

---

## `CoordenadorDTO.java` (DTO)

### Descrição:
Essa classe é o **Data Transfer Object (DTO)** que representa os dados completos de um coordenador. Ela é usada para enviar/receber dados entre a aplicação e os clientes via API.

### Atributos:
- `cpf`: Identificador único do coordenador.
- `nome`, `ultimoNome`: Nome e último nome do coordenador.
- `genero`, `data_nascimento`, `email`: Informações pessoais do coordenador.
- `enderecos`, `telefones`: Listas de endereços e telefones associados ao coordenador.
- `idCoordenacao`: Identificador da coordenação associada ao coordenador.

---

## `CoordenadorResumidoDTO.java` e `CoordenadorResumido2DTO.java` (DTOs)

### Descrição:
Esses DTOs representam dados mais resumidos do coordenador e são utilizados em cenários onde não são necessários todos os detalhes do coordenador. Contêm menos atributos em comparação com o `CoordenadorDTO`.

### Atributos Principais:
- **`CoordenadorResumidoDTO`**: Nome, último nome e e-mail do coordenador.
- **`CoordenadorResumido2DTO`**: CPF, nome completo, e-mails e telefones do coordenador.

---

## `CoordenadorController.java` (Controlador)

### Descrição:
A classe `CoordenadorController` expõe os endpoints REST para operações relacionadas ao coordenador. Utiliza o **Spring Framework** para gerenciar requisições HTTP e interagir com o `CoordenadorService`.

### Anotações:
- `@RestController`: Indica que a classe é um controlador REST.
- `@RequestMapping("/coordenadores")`: Define que todas as rotas desse controlador começam com `/coordenadores`.
- `@Tag(name = "Coordenador", description = "Gerenciamento dos Coordenadores")`: Anotação para a documentação Swagger, que agrupa as operações desse controlador.

### Rotas (Endpoints):

#### **GET `/coordenadores`**
- Lista todos os coordenadores.
  
#### **GET `/coordenadores/{cpf}`**
- Busca um coordenador pelo CPF.

#### **POST `/coordenadores`**
- Cria um novo coordenador com base nos dados fornecidos no corpo da requisição.
- Regras de Negócio:
  - O CPF deve ser **único**.

#### **PUT `/coordenadores/{cpf}`**
- Atualiza as informações de um coordenador existente.
  
#### **DELETE `/coordenadores/{cpf}`**
- Remove um coordenador pelo CPF.

---

## Conclusão

O package `coordenador` é responsável pelo gerenciamento de coordenadores no sistema. Através de uma estrutura modular e bem definida, ele oferece as funcionalidades de persistência de dados, lógica de negócio e exposição de APIs REST. A classe `CoordenadorService` desempenha um papel central na intermediação entre o repositório e o controlador, enquanto os DTOs garantem a transferência eficiente de dados entre as camadas do sistema. A associação dos coordenadores com **coordenações**, **endereços** e **telefones** é gerida de forma consistente, assegurando que todas as operações de CRUD funcionem corretamente.


# Documentação Técnica - Package `disciplina`

O package `disciplina` é responsável pelo gerenciamento das entidades e operações relacionadas às **disciplinas** no sistema **Mediotec**. Ele inclui a criação, atualização, listagem e remoção de disciplinas, bem como a associação das disciplinas com turmas e professores. Abaixo está a explicação técnica de cada arquivo presente no package, seguindo a ordem **Entidade > Repositório > Serviço > DTOs > Controlador**.

---

## `Disciplina.java` (Entidade)

### Descrição:
A classe `Disciplina` representa a **entidade** disciplina no sistema. Cada disciplina tem um nome, uma carga horária e pode estar associada a várias turmas e professores através da relação com a entidade `TurmaDisciplinaProfessor`.

### Anotações:
- `@Entity`: Define que a classe será uma entidade JPA (mapeada para uma tabela no banco de dados).
- `@Table(name = "disciplina")`: Especifica o nome da tabela como `disciplina`.
- `@Id`: Define o campo `id_disciplina` como a chave primária.
- `@GeneratedValue(strategy = GenerationType.IDENTITY)`: Gera automaticamente o valor da chave primária.
- `@NotNull`, `@Size`: Definem regras de validação para os campos como **nome** e **carga horária**.
- `@OneToMany(mappedBy = "disciplina")`: Relaciona a disciplina com a entidade `TurmaDisciplinaProfessor`.

### Relacionamentos:
- **TurmaDisciplinaProfessor** (`OneToMany`): A disciplina pode estar associada a várias combinações de turmas e professores.

### Atributos:
- **`id`** (`Long`): Chave primária da disciplina.
- **`nome`** (`String`): Nome da disciplina (mínimo de 3 e máximo de 100 caracteres).
- **`carga_horaria`** (`int`): Carga horária da disciplina.

### Regras de Negócio:
- A disciplina deve ter um **nome** único e uma **carga horária** definida.
- A disciplina pode estar associada a uma ou várias turmas e professores através da relação `TurmaDisciplinaProfessor`.

---

## `DisciplinaRepository.java` (Repositório)

### Descrição:
A interface `DisciplinaRepository` é responsável pela persistência dos dados da disciplina, estendendo o `JpaRepository`. Ela oferece os métodos padrão para operações de CRUD (Create, Read, Update, Delete) no banco de dados.

### Métodos:
- **`findAll()`**: Retorna uma lista de todas as disciplinas.
- **`findById(Long id)`**: Busca uma disciplina específica pelo seu ID.

---

## `DisciplinaService.java` (Serviço)

### Descrição:
A classe `DisciplinaService` contém a **lógica de negócio** associada às disciplinas, como a criação, atualização, listagem e remoção. Além disso, ela lida com as associações entre disciplinas, turmas e professores.

### Métodos Principais:

#### `getAllDisciplinas()`
- Retorna uma lista de todas as disciplinas cadastradas no banco de dados.
- Converte as disciplinas para o DTO `DisciplinaGetDTO` antes de retorná-las.

#### `getDisciplinaById(Long id)`
- Busca uma disciplina por seu ID e retorna seus detalhes no formato `DisciplinaGetDTO`.
- Lança uma exceção se a disciplina não for encontrada.

#### `saveDisciplina(DisciplinaDTO disciplinaDTO)`
- Persiste uma nova disciplina no banco de dados.
- Associa a disciplina a uma turma e (opcionalmente) a um professor, se os IDs forem fornecidos no DTO.
- Retorna um `DisciplinaResumidaDTO` contendo os dados resumidos da disciplina criada.

#### `updateDisciplina(Long id, DisciplinaDTO disciplinaDTO)`
- Atualiza uma disciplina existente no banco de dados.
- Remove as associações anteriores com turmas e professores e cria novas associações com base nos dados fornecidos.
- Retorna um `DisciplinaResumidaDTO` contendo os dados resumidos da disciplina atualizada.

#### `deleteDisciplina(Long id)`
- Remove uma disciplina existente com base no ID fornecido.

---

## **Regras de Negócio Implementadas:**

- **Associações**: A disciplina pode ser associada a várias turmas e professores. Se uma turma ou professor for fornecido no DTO, a associação será feita automaticamente ao salvar ou atualizar a disciplina.
- **Validações**: Validações como nome obrigatório e carga horária obrigatória são aplicadas antes de salvar a disciplina no banco de dados.
- **Atualizações**: Quando uma disciplina é atualizada, todas as associações anteriores com turmas e professores são removidas, e novas associações são criadas com base nos dados fornecidos.

---

## DTOs (Data Transfer Objects)

### 1. **DisciplinaDTO**
- Utilizado para a **criação** e **atualização** de disciplinas. Contém informações como nome, carga horária, e os IDs de turma e professor.

### 2. **DisciplinaGetDTO**
- Utilizado para exibir as informações de uma disciplina ao cliente nas operações **GET**. Contém o nome da disciplina, carga horária, e, se aplicável, o nome da turma e do professor associados.

### 3. **DisciplinaResumidaDTO**
- Usado para representar uma visão simplificada da disciplina, geralmente retornado após as operações **POST** e **PUT**.

---

## `DisciplinaController.java` (Controlador)

### Descrição:
A classe `DisciplinaController` expõe os endpoints REST para operações relacionadas às disciplinas. Utiliza o **Spring Framework** para gerenciar requisições HTTP e interage com o `DisciplinaService` para aplicar as regras de negócio.

### Anotações:
- `@RestController`: Indica que a classe é um controlador REST, permitindo retornar dados JSON.
- `@RequestMapping("/disciplinas")`: Define que todas as rotas desse controlador começam com `/disciplinas`.
- `@Tag(name = "Disciplina", description = "Operações relacionadas às disciplinas")`: Anotação para a documentação Swagger, que agrupa as operações desse controlador.

### Endpoints (Rotas):

#### **GET `/disciplinas`**
- Lista todas as disciplinas cadastradas no sistema.
  
#### **GET `/disciplinas/{id}`**
- Busca uma disciplina pelo ID e retorna suas informações detalhadas no formato `DisciplinaGetDTO`.

#### **POST `/disciplinas`**
- Cria uma nova disciplina com base nos dados fornecidos no corpo da requisição.
- Associa a disciplina a uma turma e (opcionalmente) a um professor.

#### **PUT `/disciplinas/{id}`**
- Atualiza uma disciplina existente no sistema com base no ID e nos dados fornecidos no corpo da requisição.

#### **DELETE `/disciplinas/{id}`**
- Remove uma disciplina existente com base no ID fornecido.

---

## Conclusão

O package `disciplina` gerencia a criação, leitura, atualização e remoção de disciplinas no sistema **Mediotec**. Ele também lida com as associações entre disciplinas, turmas e professores, garantindo que as operações de CRUD sejam realizadas de forma eficiente e segura. As validações de negócio, como nome e carga horária obrigatórios, estão implementadas, e os DTOs asseguram uma comunicação eficiente entre o backend e o frontend, transferindo apenas os dados necessários em cada operação. O **DisciplinaService** realiza as operações de lógica de negócio, enquanto o **DisciplinaController** expõe as APIs para interações externas.

--- 

# Documentação Técnica - Package `endereco`

O package `endereco` é responsável pelo gerenciamento dos dados de endereço associados a várias entidades no sistema, como **Aluno**, **Professor**, **Coordenador**, e **Coordenação**. Ele lida com o armazenamento, validação e transferência de informações de localização, como CEP, rua, número, bairro, cidade, e estado.

Abaixo estão as explicações detalhadas dos componentes principais deste package: a **Entidade `Endereco`** e o **`EnderecoDTO`**.

---

## Entidade `Endereco`

### Descrição
A entidade `Endereco` modela os dados de localização dos usuários no sistema. Cada `Endereco` é persistido em uma tabela no banco de dados e pode estar associado a um **Aluno**, **Professor**, **Coordenador**, ou **Coordenação**. As informações básicas incluem CEP, rua, número, bairro, cidade e estado, todas devidamente validadas quanto ao formato e tamanho.

### Atributos:
- **`id_endereco`** (`Long`): Identificador único do endereço, gerado automaticamente pelo banco de dados.
  - Anotações: `@Id`, `@GeneratedValue(strategy = GenerationType.IDENTITY)`
  
- **`cep`** (`String`): Código de Endereçamento Postal (CEP) do endereço.
  - Validação: Deve conter entre 8 e 9 caracteres. Não pode ser nulo.
  - Anotações: `@NotNull`, `@Size(min = 8, max = 9)`

- **`rua`** (`String`): Nome da rua ou logradouro.
  - Validação: Deve conter entre 3 e 100 caracteres. Não pode ser nulo.
  - Anotações: `@NotNull`, `@Size(min = 3, max = 100)`

- **`numero`** (`String`): Número da residência ou estabelecimento.
  - Validação: Deve conter entre 1 e 10 caracteres. Não pode ser nulo.
  - Anotações: `@NotNull`, `@Size(min = 1, max = 10)`

- **`bairro`** (`String`): Bairro onde o endereço está localizado.
  - Validação: Deve conter entre 3 e 50 caracteres. Não pode ser nulo.
  - Anotações: `@NotNull`, `@Size(min = 3, max = 50)`

- **`cidade`** (`String`): Cidade onde o endereço está localizado.
  - Validação: Deve conter entre 3 e 50 caracteres. Não pode ser nulo.
  - Anotações: `@NotNull`, `@Size(min = 3, max = 50)`

- **`estado`** (`String`): Estado (UF) onde o endereço está localizado.
  - Validação: Deve conter entre 2 e 20 caracteres. Não pode ser nulo.
  - Anotações: `@NotNull`, `@Size(min = 2, max = 20)`

### Relacionamentos
A entidade `Endereco` pode estar associada a várias outras entidades do sistema, como **Aluno**, **Professor**, **Coordenador**, e **Coordenação**. Esses relacionamentos são geridos de forma bidirecional, permitindo que tanto o endereço quanto a entidade relacionada tenham referências entre si.

- **Aluno** (`ManyToOne`): Um aluno pode ter vários endereços, mas cada endereço está associado a apenas um aluno.
  - Anotações: `@ManyToOne`, `@JoinColumn(name = "aluno_id")`, `@JsonIgnore`
  
- **Professor** (`ManyToOne`): Um professor pode ter vários endereços, mas cada endereço está associado a apenas um professor.
  - Anotações: `@ManyToOne`, `@JoinColumn(name = "professor_id")`, `@JsonIgnore`

- **Coordenador** (`ManyToOne`): Um coordenador pode ter vários endereços, mas cada endereço está associado a apenas um coordenador.
  - Anotações: `@ManyToOne`, `@JoinColumn(name = "coordenador_id")`, `@JsonIgnore`

- **Coordenação** (`ManyToOne`): Uma coordenação pode ter vários endereços, mas cada endereço está associado a apenas uma coordenação.
  - Anotações: `@ManyToOne`, `@JoinColumn(name = "coordenacao_id")`, `@JsonIgnore`

### Regras de Negócio
- Todos os campos do endereço (CEP, rua, número, bairro, cidade, estado) são obrigatórios e validados para garantir que as informações estejam no formato correto.
- Os relacionamentos com **Aluno**, **Professor**, **Coordenador**, e **Coordenação** são geridos de maneira bidirecional para garantir que ambos os lados da associação sejam corretamente atualizados no banco de dados.
- As associações são ignoradas durante a serialização JSON (`@JsonIgnore`), evitando problemas como loops infinitos ou grandes volumes de dados desnecessários sendo trafegados pela API.

### Métodos Sobrescritos
- **`hashCode()`**: Retorna um código de hash baseado no `id_endereco` para evitar ciclos e garantir a consistência durante comparações.
- **`equals(Object o)`**: Compara duas instâncias de `Endereco` pelo `id_endereco`, garantindo que a comparação seja feita corretamente.

---

## DTO `EnderecoDTO`

### Descrição
O **`EnderecoDTO`** é um **Data Transfer Object (DTO)** utilizado para transferir dados de um endereço entre o sistema e os clientes (como o frontend ou APIs REST). Ele é utilizado em operações de criação, atualização e leitura de endereços, facilitando o tráfego de dados de forma simplificada e segura.

### Atributos:
- **`cep`** (`String`): CEP do endereço.
  - Regras de Negócio: Deve conter entre 8 e 9 caracteres, sendo validado na entrada de dados.

- **`rua`** (`String`): Nome da rua ou logradouro.
  - Regras de Negócio: Deve conter entre 3 e 100 caracteres.

- **`numero`** (`String`): Número da residência ou estabelecimento.
  - Regras de Negócio: Deve conter entre 1 e 10 caracteres.

- **`bairro`** (`String`): Bairro onde o endereço está localizado.
  - Regras de Negócio: Deve conter entre 3 e 50 caracteres.

- **`cidade`** (`String`): Cidade onde o endereço está localizado.
  - Regras de Negócio: Deve conter entre 3 e 50 caracteres.

- **`estado`** (`String`): Estado (UF) onde o endereço está localizado.
  - Regras de Negócio: Deve conter entre 2 e 20 caracteres.

### Regras de Negócio
- O `EnderecoDTO` é usado nas operações de **criação**, **atualização**, e **consulta** de endereços no sistema.
- Validações como comprimento mínimo e máximo dos campos são aplicadas durante a entrada de dados, garantindo que o sistema receba informações formatadas corretamente.
- A utilização de DTOs evita o tráfego de dados sensíveis ou complexos (como IDs e relacionamentos bidirecionais), focando apenas nas informações relevantes para o usuário.

---

## Conclusão
O package `endereco` modela e gerencia as informações de localização dos usuários no sistema, permitindo que essas informações sejam facilmente associadas a outras entidades, como alunos, professores, coordenadores e coordenações. A entidade `Endereco` foi projetada com validações e relacionamentos eficientes, garantindo a integridade dos dados e a consistência das associações. O uso do `EnderecoDTO` simplifica o tráfego de informações entre o sistema e os clientes, garantindo que apenas os dados necessários sejam enviados e recebidos.

---

# Documentação Técnica - Package `professor`

O package `professor` é responsável pelo gerenciamento das entidades e operações relacionadas ao **Professor** no sistema. Ele lida com a persistência de dados, regras de negócio, comunicação com o banco de dados e a exposição de APIs REST. Abaixo está a explicação técnica de cada arquivo presente no package, seguindo a ordem **Entidade > Repositório > Serviço > DTOs > Controlador**.

---

## `Professor.java` (Entidade)

### Descrição:
A classe `Professor` representa a **entidade** professor que será persistida no banco de dados. Ela herda de `Usuario`, contendo atributos e relacionamentos específicos do professor. Esta classe é a base para todas as operações e interações relacionadas ao professor no sistema.

### Anotações:
- `@Entity`: Define que a classe será uma entidade JPA (mapeada para uma tabela no banco de dados).
- `@Table(name = "professor")`: Especifica o nome da tabela no banco de dados como "professor".
- `@Id`: Define o campo `cpf` como a chave primária da entidade.
- `@NotNull`, `@Column`: Validações aplicadas ao campo `cpf`, garantindo que ele seja único e não nulo.

### Relacionamentos:
- **Coordenacao** (`ManyToOne`): Um professor pode estar associado a uma coordenação.
- **Endereços** (`OneToMany`): Um professor pode ter vários endereços.
- **Telefones** (`OneToMany`): Um professor pode ter vários telefones.
- **Turmas e Disciplinas** (`OneToMany`): Relaciona o professor com suas turmas e disciplinas, através da entidade `TurmaDisciplinaProfessor`.

### Regras de Negócio:
- O campo **CPF** é obrigatório e único.
- O **status** do professor indica se ele está **ativo** no sistema.

### Métodos Auxiliares:
- `addEndereco(Endereco endereco)`: Adiciona um endereço ao professor e configura a relação bidirecional.
- `addTelefone(Telefone telefone)`: Adiciona um telefone ao professor e configura a relação bidirecional.
- `addTurmaDisciplinaProfessor(TurmaDisciplinaProfessor tdp)`: Adiciona uma relação de turma e disciplina ao professor.

---

## `ProfessorRepository.java` (Repositório)

### Descrição:
Essa interface define a camada de **persistência** dos dados do professor. Estende o **`JpaRepository`** e permite a execução de operações CRUD diretamente com o banco de dados.

### Métodos Principais:
- `existsByCpf(String cpf)`: Verifica se já existe um professor com o CPF fornecido.
- `findAll()`: Retorna todos os professores cadastrados.
- `findById(String cpf)`: Busca um professor pelo CPF.

### Regras de Negócio:
- **CPF único**: Verifica a unicidade do CPF ao criar um professor.

---

## `ProfessorService.java` (Serviço)

### Descrição:
A classe `ProfessorService` contém a lógica de **negócio** relacionada aos professores. Ela serve como intermediária entre o `ProfessorRepository` e o `ProfessorController`, aplicando regras de negócio antes de persistir ou retornar dados.

### Métodos:

#### `saveProfessor(ProfessorDTO professorDTO)`
- Cria e salva um novo professor no banco de dados.
- **Regras de Negócio**:
  - O CPF do professor deve ser único.
  - O professor pode ser associado a uma coordenação, turmas, disciplinas, endereços e telefones.

#### `updateProfessor(String cpf, ProfessorDTO professorDTO)`
- Atualiza as informações de um professor existente.
- **Regras de Negócio**:
  - O CPF do professor deve ser consistente com o banco de dados.
  - Associações com turmas, disciplinas, endereços e telefones podem ser alteradas.

#### `deleteProfessor(String cpf)`
- Remove um professor pelo CPF.
- **Regras de Negócio**:
  - Verifica se o professor existe no sistema antes de excluí-lo.

#### `getProfessorById(String cpf)`
- Retorna os detalhes de um professor pelo CPF.

#### `getAllProfessores()`
- Retorna a lista de todos os professores cadastrados no sistema.

---

## DTOs (Data Transfer Objects)

### `ProfessorDTO.java`
- **Descrição**: DTO usado para transferir dados de um professor nas operações de criação e atualização. Contém atributos como CPF, nome, gênero, data de nascimento, email, endereços, telefones e turmas/disciplina.

### `ProfessorResumidoDTO.java`
- **Descrição**: DTO usado para operações de leitura, contendo uma visão mais simplificada dos dados do professor. Usado principalmente em respostas de API.

### `ProfessorResumido2DTO.java`
- **Descrição**: Uma variação mais compacta de `ProfessorResumidoDTO`, contendo apenas os dados essenciais do professor.

---

## `ProfessorController.java` (Controlador)

### Descrição:
A classe `ProfessorController` expõe os **endpoints REST** para operações relacionadas ao professor, usando o **Spring Framework**. Interage com o `ProfessorService` para lidar com as requisições HTTP e aplicar as regras de negócio.

### Anotações:
- `@RestController`: Indica que a classe é um controlador REST.
- `@RequestMapping("/professores")`: Define que todas as rotas dentro dessa classe começam com `/professores`.
- `@Tag(name = "Professor", description = "Operações relacionadas aos Professores")`: Usado para a documentação do Swagger.

### Rotas (Endpoints):

#### **GET `/professores`**
- Retorna uma lista de todos os professores cadastrados.

#### **GET `/professores/{cpf}`**
- Retorna um professor específico baseado no CPF.

#### **POST `/professores`**
- Cria um novo professor no sistema.

#### **PUT `/professores/{cpf}`**
- Atualiza os dados de um professor existente.

#### **DELETE `/professores/{cpf}`**
- Remove um professor existente baseado no CPF.

---

## Conclusão

O package `professor` gerencia todas as operações relacionadas aos professores no sistema, desde a camada de persistência de dados até a exposição de APIs REST. A entidade `Professor` contém relacionamentos e regras de negócio específicos para o gerenciamento dos dados de professores, incluindo a validação de CPF único. As operações são expostas via endpoints REST no `ProfessorController`, enquanto a lógica de negócio é implementada no `ProfessorService`. Os DTOs garantem uma comunicação eficiente entre o frontend e o backend, focando nos dados essenciais para cada operação.

---

# Pacote `projeto.integrador3.senac.mediotec.pi3_mediotec.responsavel`

Este pacote é responsável pelo gerenciamento das operações e informações relacionadas à entidade `Responsavel` no sistema, que representa o responsável por um aluno. Aqui são armazenados dados como nome, CPF, grau de parentesco e telefones. As classes principais deste pacote são a **entidade `Responsavel`** e o **DTO `ResponsavelDTO`**.

---

## Classe `Responsavel.java` (Entidade)

### Descrição:
A classe `Responsavel` é uma entidade que mapeia a tabela `responsavel` no banco de dados. Esta classe contém os atributos essenciais para identificar e associar o responsável a um aluno, bem como armazenar seus telefones e o grau de parentesco com o aluno.

### Anotações:
- **@Entity**: Indica que a classe `Responsavel` será persistida como uma entidade JPA.
- **@Table(name = "responsavel")**: Define o nome da tabela no banco de dados como `responsavel`.
- **@Id**: Define o campo `id` como chave primária.
- **@GeneratedValue(strategy = GenerationType.IDENTITY)**: Especifica que o valor de `id` será gerado automaticamente.
- **@Column**: Configurações de mapeamento dos atributos para as colunas do banco de dados.
  - `nullable = false`: Indica que o campo não pode ser nulo.
  - `unique = true`: Indica que o campo `cpfResponsavel` deve ser único.

### Relacionamentos:
- **@ManyToOne**: Um responsável está associado a um aluno, ou seja, muitos responsáveis podem compartilhar o mesmo aluno.
- **@OneToMany**: Um responsável pode ter vários telefones. A relação é configurada como "um para muitos" e utiliza `cascade = CascadeType.ALL` para propagar operações ao banco de dados e `orphanRemoval = true` para garantir a exclusão automática de telefones órfãos.

### Atributos:
- **`id`**: Chave primária gerada automaticamente.
- **`cpfResponsavel`**: CPF do responsável, campo único e obrigatório.
- **`nome`**: Nome do responsável, campo obrigatório.
- **`ultimoNome`**: Sobrenome do responsável, campo obrigatório.
- **`grauParentesco`**: Grau de parentesco do responsável com o aluno, campo obrigatório.
- **`aluno`**: Relacionamento com a entidade `Aluno`, representando o aluno associado ao responsável.
- **`telefones`**: Conjunto de telefones associados ao responsável, representado pela entidade `Telefone`.

### Métodos Auxiliares:
- **`addTelefone(Telefone telefone)`**: Método utilizado para adicionar um telefone ao responsável e configurar a relação bidirecional.

---

## Classe `ResponsavelDTO.java`

### Descrição:
A classe `ResponsavelDTO` é um **Data Transfer Object** (DTO) utilizado para transferir dados entre o frontend e o backend em operações relacionadas ao responsável. O `ResponsavelDTO` contém informações essenciais sobre o responsável, como nome, CPF, grau de parentesco e os telefones associados.

### Atributos:
- **`nome`**: Nome do responsável.
- **`ultimoNome`**: Sobrenome do responsável.
- **`cpfResponsavel`**: CPF do responsável.
- **`telefones`**: Conjunto de telefones associados ao responsável, representado por `TelefoneDTO`.
- **`grauParentesco`**: Grau de parentesco do responsável com o aluno.

---

## Relação entre `Responsavel` e `ResponsavelDTO`:
A entidade `Responsavel` é mapeada diretamente para o DTO `ResponsavelDTO` em operações de criação, leitura e atualização. O `DTO` garante que apenas os dados essenciais sejam transferidos para o frontend, enquanto os dados mais complexos e a lógica de negócio são manipulados na entidade.

--- 

# Documentação Técnica - Package `telefone`

O package `telefone` é responsável por gerenciar as informações de contato (telefones) associadas a várias entidades do sistema, como **Aluno**, **Professor**, **Coordenador**, **Coordenação** e **Responsável**. Este package inclui a definição da **entidade `Telefone`** e do **DTO `TelefoneDTO`**, usados para armazenar, processar e transferir informações de telefones.

---

## Entidade `Telefone`

### Descrição:
A entidade `Telefone` é uma classe que modela os dados de telefone no sistema, como o **DDD** e o **número**. Esta entidade também define os relacionamentos com outras entidades que podem possuir telefones (como alunos, professores, coordenadores e responsáveis).

### Atributos Principais:
- **`id`**: Chave primária gerada automaticamente, usada para identificar unicamente cada registro de telefone no banco de dados.
- **`ddd`**: Código de área do telefone (obrigatório e com exatamente 2 caracteres).
- **`numero`**: O número de telefone (obrigatório e deve conter entre 8 e 9 caracteres).

### Relacionamentos:
A entidade `Telefone` tem vários relacionamentos **ManyToOne** com outras entidades do sistema:
- **Aluno**, **Professor**, **Coordenador**, **Coordenação** e **Responsável**: Cada telefone pode ser associado a uma dessas entidades, representando o telefone de um aluno, professor, coordenador, etc. Essas associações são configuradas usando `@ManyToOne`, o que significa que vários telefones podem ser associados a uma única instância dessas entidades.

Os relacionamentos são anotados com `@JsonIgnore`, o que evita que esses dados sejam incluídos em respostas JSON. Isso é útil para prevenir loops de serialização e garantir que a API retorne apenas os dados essenciais.

### Validações:
A entidade `Telefone` faz uso das anotações de validação:
- **`@NotNull`**: Assegura que os campos `ddd` e `numero` não sejam nulos.
- **`@Size`**: Garante que o `ddd` tenha exatamente 2 caracteres e que o número tenha entre 8 e 9 caracteres.

### Regras de Negócio:
- Cada telefone pode ser associado a apenas **um** aluno, professor, coordenador, coordenação ou responsável por vez.
- A exclusão de um telefone é feita em **cascata**, o que significa que, ao remover uma entidade associada, seus telefones também podem ser removidos automaticamente, dependendo da configuração do relacionamento.

---

## DTO `TelefoneDTO`

### Descrição:
O **`TelefoneDTO`** (Data Transfer Object) é usado para transferir dados de telefone entre o sistema e os clientes (como frontends ou APIs). Ele simplifica a manipulação dos dados, focando apenas nos atributos essenciais que precisam ser trafegados, como o **DDD** e o **número**, sem incluir informações de relacionamentos com outras entidades.

### Atributos:
- **`ddd`**: O código de área do telefone (apenas os 2 dígitos do DDD).
- **`numero`**: O número do telefone (8 a 9 dígitos).

### Objetivo e Funcionamento:
O `TelefoneDTO` é utilizado nas operações de **entrada e saída** de dados (como criar, atualizar ou exibir informações sobre um telefone). Quando o cliente deseja enviar ou receber dados de telefone, como durante a criação de um aluno ou professor, apenas os campos `ddd` e `numero` são necessários.

Ele é anotado com **`@JsonIgnoreProperties`**, o que significa que os atributos de relacionamento (como aluno, professor, coordenador) não serão enviados ou recebidos no tráfego de dados. Isso mantém a transferência de dados eficiente e evita que o cliente receba informações desnecessárias ou complexas.

### Exemplo de Utilização:
- **Entrada de Dados**: Ao criar ou atualizar um telefone, o sistema espera receber um `TelefoneDTO` com apenas o `ddd` e o `numero`. Informações relacionadas a qual entidade (aluno, professor, etc.) o telefone pertence são gerenciadas pela entidade `Telefone`, não pelo DTO.
- **Saída de Dados**: Quando o sistema responde a uma solicitação para obter informações de telefone, ele envia um `TelefoneDTO` contendo apenas o `ddd` e o `numero`, sem expor detalhes internos ou relacionamentos.

### Importância:
O uso do `TelefoneDTO` melhora a segurança e a eficiência do sistema ao:
- Garantir que apenas as informações essenciais sejam trafegadas entre o cliente e o servidor.
- Evitar a exposição de informações sensíveis ou desnecessárias, como os relacionamentos complexos que a entidade `Telefone` mantém com outras entidades.

---

## Relação entre `Telefone` e `TelefoneDTO`

- A **entidade `Telefone`** é usada internamente pelo sistema para gerenciar os dados completos de telefone, incluindo os relacionamentos com outras entidades.
- O **`TelefoneDTO`** é utilizado para simplificar a comunicação entre o backend e o frontend, expondo apenas os campos relevantes (como `ddd` e `numero`) nas operações de API, sem expor a complexidade das relações com outras entidades.

---

## Conclusão

O package `telefone` lida com a gestão dos dados de telefone, garantindo que as informações sejam armazenadas e manipuladas corretamente, mantendo relações com outras entidades do sistema (como aluno, professor, etc.). A entidade `Telefone` define essas relações e as regras de validação, enquanto o `TelefoneDTO` oferece uma interface simplificada para que os dados essenciais de telefone possam ser transferidos entre o sistema e os clientes, garantindo eficiência e segurança.

---

# Documentação Técnica - Package `turma`

O pacote `turma` é responsável pelo gerenciamento completo das turmas dentro do sistema, incluindo operações CRUD (criar, ler, atualizar e deletar) e a associação de turmas com alunos, professores e disciplinas.

## 1. Entidade `Turma`

### Descrição:
A classe `Turma` modela a entidade que representa uma turma no sistema educacional. Ela define as informações básicas da turma, como nome, ano letivo, turno, além dos relacionamentos com outras entidades como `Aluno`, `Disciplina` e `Professor`.

### Atributos:
- **`id`**: Identificador único da turma gerado automaticamente.
- **`nome`**: Nome da turma, gerado automaticamente com base no ID.
- **`anoLetivo`**: Ano letivo da turma.
- **`anoEscolar`**: Ano escolar correspondente (ex: 1º ano, 2º ano).
- **`turno`**: Turno em que a turma está alocada (manhã, tarde, noite).
- **`status`**: Status da turma (ativa/inativa).
  
### Relacionamentos:
- **`coordenacao`**: Relação `ManyToOne` com a entidade `Coordenacao`, representando a coordenação associada à turma.
- **`alunos`**: Relação `ManyToMany` com a entidade `Aluno`, representando os alunos que pertencem à turma.
- **`turmaDisciplinaProfessores`**: Relação `OneToMany` com a entidade `TurmaDisciplinaProfessor`, que mapeia a relação entre turmas, disciplinas e professores.

### Validações:
- **`@NotNull`**: O campo `anoLetivo` é obrigatório.
- **`@Size`**: O nome gerado da turma segue um formato padrão.

### Métodos Auxiliares:
- **`addAluno(Aluno aluno)`**: Adiciona um aluno à turma e configura a relação bidirecional.
- **`removeAluno(Aluno aluno)`**: Remove a associação de um aluno à turma.
- **`addTurmaDisciplinaProfessor(TurmaDisciplinaProfessor tdp)`**: Adiciona uma disciplina e um professor à turma.
- **`removeTurmaDisciplinaProfessor(TurmaDisciplinaProfessor tdp)`**: Remove a associação de uma disciplina e professor à turma.

---

## 2. Repositório `TurmaRepository`

### Descrição:
Interface `TurmaRepository` estende `JpaRepository` e fornece os métodos padrão para realizar operações CRUD sobre a entidade `Turma`. Essa interface permite que o framework Spring Data JPA forneça a implementação automaticamente.

### Métodos:
- **`findAll()`**: Retorna todas as turmas cadastradas.
- **`findById(Long id)`**: Retorna uma turma específica com base no seu ID.
- **`save(Turma turma)`**: Persiste uma nova turma ou atualiza uma existente no banco de dados.
- **`deleteById(Long id)`**: Remove uma turma com base no seu ID.

---

## 3. Serviço `TurmaService`

### Descrição:
O serviço `TurmaService` encapsula a lógica de negócios associada à entidade `Turma`. Ele interage com o repositório para realizar operações de persistência e aplica as regras de negócio como criação de turmas, associação de alunos e professores.

### Principais Métodos:

- **`saveTurma(TurmaInputDTO turmaDTO)`**:
  - Cria uma nova turma no sistema com base nas informações fornecidas no `TurmaInputDTO`.
  - Gera automaticamente o nome da turma após a criação.
  - Associa alunos e professores com as disciplinas fornecidas, validando as existências no sistema.

- **`updateTurma(Long id, TurmaInputDTO turmaDTO)`**:
  - Atualiza uma turma existente com os dados fornecidos no `TurmaInputDTO`.
  - Atualiza as associações com alunos, professores e disciplinas.

- **`getAllTurmas()`**:
  - Lista todas as turmas cadastradas no sistema.

- **`getTurmaById(Long id)`**:
  - Retorna os detalhes de uma turma específica com base no seu ID.

- **`deleteTurma(Long id)`**:
  - Remove uma turma e suas associações no sistema com base no ID fornecido.

- **Regras de Negócio**:
  - Cada turma deve ser associada a uma coordenação válida.
  - Associações de `Aluno`, `Disciplina` e `Professor` são opcionais, mas são validadas caso fornecidas.

---

## 4. DTOs (Data Transfer Objects)

### 4.1. `TurmaDTO`

#### Descrição:
DTO utilizado para transferir os dados detalhados de uma turma, incluindo suas associações com alunos, disciplinas e professores.

#### Atributos:
- **`nome`**: Nome da turma.
- **`anoEscolar`**: Ano escolar correspondente.
- **`turno`**: Turno em que a turma está alocada.
- **`status`**: Status da turma (ativa/inativa).
- **`coordenacao`**: Nome da coordenação associada.
- **`disciplinas`**: Conjunto de disciplinas associadas à turma.
- **`disciplinasProfessores`**: Lista de relações entre professores e disciplinas dentro da turma.
- **`alunos`**: Conjunto de alunos associados à turma.

### 4.2. `TurmaInputDTO`

#### Descrição:
DTO utilizado para receber os dados necessários para criar ou atualizar uma turma. Este DTO simplifica a entrada de dados, garantindo que apenas as informações relevantes sejam recebidas.

#### Atributos:
- **`anoLetivo`**: Ano letivo da turma.
- **`anoEscolar`**: Ano escolar correspondente.
- **`turno`**: Turno da turma.
- **`status`**: Status da turma.
- **`coordenacaoId`**: ID da coordenação a ser associada.
- **`alunosIds`**: Lista de IDs de alunos a serem associados à turma.
- **`disciplinasProfessores`**: Lista de disciplinas associadas a professores.

### 4.3. `TurmaResumidaDTO`

#### Descrição:
DTO utilizado para transferir um resumo das informações de uma turma. Focado em exibir as informações essenciais como nome da turma, ano escolar, turno e coordenação.

#### Atributos:
- **`nome`**: Nome da turma.
- **`anoEscolar`**: Ano escolar.
- **`turno`**: Turno da turma.
- **`coordenacao`**: Nome da coordenação associada.

---

## 5. Controlador `TurmaController`

### Descrição:
O `TurmaController` expõe as operações relacionadas à turma através de uma API REST. Ele recebe requisições HTTP, invoca o `TurmaService` para realizar as operações e retorna as respostas adequadas.

### Endpoints Principais:

- **`GET /turmas`**:
  - Lista todas as turmas cadastradas no sistema.
  - **Resposta**: `200 OK` com a lista de `TurmaDTO`.

- **`GET /turmas/{id}`**:
  - Retorna os detalhes de uma turma específica com base no ID fornecido.
  - **Resposta**: `200 OK` se encontrado, `404 Not Found` caso contrário.

- **`POST /turmas`**:
  - Cria uma nova turma com os dados fornecidos no `TurmaInputDTO`.
  - **Resposta**: `201 Created` com o `TurmaDTO` da turma criada.

- **`PUT /turmas/{id}`**:
  - Atualiza os dados de uma turma existente.
  - **Resposta**: `200 OK` com o `TurmaDTO` atualizado.

- **`DELETE /turmas/{id}`**:
  - Remove uma turma com base no seu ID.
  - **Resposta**: `204 No Content` se a remoção for bem-sucedida.

---

## Considerações Finais

O pacote `turma` é responsável pela gestão eficiente das turmas, garantindo a correta associação com alunos, disciplinas e professores. Ele encapsula as regras de negócio relacionadas à organização das turmas e suas operações, expondo uma API clara e otimizada para administração via controladores REST.

---


# Documentação Técnica - Package `turmaDisciplinaProfessor`

O package `turmaDisciplinaProfessor` é responsável por gerenciar a associação entre as entidades **Turma**, **Disciplina** e **Professor**. Ele lida com o relacionamento muitos-para-muitos entre essas três entidades, usando uma tabela intermediária e uma chave composta para manter a unicidade das associações. Este package também fornece DTOs para transferir dados entre as camadas de aplicação de forma eficiente.

## Entidade `TurmaDisciplinaProfessor`

### Descrição:
A entidade `TurmaDisciplinaProfessor` gerencia a relação entre as três entidades principais: **Turma**, **Disciplina** e **Professor**. Essa associação é persistida em uma tabela intermediária chamada `turma_disciplina_professor`, que armazena os IDs dessas três entidades para formar uma relação única e garantir que um professor esteja corretamente vinculado a uma turma e a uma disciplina.

### Principais Anotações:
- `@Entity` e `@Table`: Define que a classe será uma entidade JPA e mapeada para a tabela `turma_disciplina_professor` no banco de dados.
- `@EmbeddedId`: Utiliza uma chave composta (`TurmaDisciplinaProfessorId`) para identificar unicamente a associação.
- `@ManyToOne` e `@MapsId`: Mapeia as relações com **Turma**, **Disciplina** e **Professor**. Cada uma dessas entidades é referenciada com uma relação ManyToOne e mapeada com base nos IDs da chave composta.
- `fetch = FetchType.LAZY`: Carrega os dados das entidades associadas de forma "preguiçosa", ou seja, somente quando acessadas diretamente.

### Regras de Negócio:
- A associação entre **Turma**, **Disciplina** e **Professor** deve ser única, garantida pela chave composta.
- As operações que envolvem essa entidade geralmente são relacionadas ao gerenciamento de turmas, disciplinas ministradas e professores responsáveis.

## Entidade `TurmaDisciplinaProfessorId`

### Descrição:
Esta classe define a chave composta da entidade `TurmaDisciplinaProfessor`. Ela agrupa os identificadores de **turma**, **disciplina** e **professor** para garantir a unicidade da associação no banco de dados.

### Principais Anotações:
- `@Embeddable`: Indica que esta classe será embutida em outra entidade (neste caso, `TurmaDisciplinaProfessor`).
- `@Column`: Cada campo representa o identificador de uma entidade, como `turmaId`, `disciplinaId` e `professorId`.

### Métodos:
- `equals()` e `hashCode()`: Esses métodos garantem que dois objetos de `TurmaDisciplinaProfessorId` sejam comparados corretamente e possam ser usados em estruturas que dependem de hash, como coleções.

### Regras de Negócio:
- A combinação de `turmaId`, `disciplinaId` e `professorId` deve ser única, garantindo que não existam duplicações na relação entre essas entidades.

## DTOs

Os **Data Transfer Objects (DTOs)** simplificam a transferência de dados entre as diferentes camadas da aplicação, como a comunicação entre o back-end e o front-end. Existem três principais DTOs no package, cada um com um propósito específico.

### `TurmaDisciplinaProfessorDTO`

#### Descrição:
Este DTO é usado para representar a associação entre **turma**, **disciplina** e **professor** por meio de seus identificadores (IDs). Ele é utilizado quando não são necessários detalhes adicionais, apenas os IDs das entidades relacionadas.

#### Atributos:
- `turmaId`: ID da turma.
- `disciplinaId`: ID da disciplina.
- `professorId`: ID do professor.

#### Uso:
Ideal para operações onde apenas os identificadores são suficientes, como em criações ou atualizações rápidas da relação entre as entidades.

### `TurmaDisciplinaProfessorCompletoDTO`

#### Descrição:
Este DTO fornece uma visão mais completa da relação entre **turma**, **disciplina** e **professor**, incluindo os nomes dessas entidades em vez de apenas seus IDs.

#### Atributos:
- `nomeTurma`: Nome completo da turma.
- `nomeDisciplina`: Nome completo da disciplina.
- `nomeProfessor`: Nome do professor associado.

#### Uso:
Esse DTO é utilizado quando é necessário fornecer informações mais detalhadas para o usuário final, como em exibições ou listagens detalhadas.

### `TurmaDisciplinaProfessorIdDTO`

#### Descrição:
Este DTO encapsula apenas os dados referentes à chave composta da associação, ou seja, os IDs de **turma**, **disciplina** e **professor**. É uma representação simples e direta da chave.

#### Atributos:
- `turmaId`: ID da turma.
- `disciplinaId`: ID da disciplina.
- `professorId`: ID do professor.

#### Uso:
Utilizado em cenários onde é necessária a identificação da associação com base nos três IDs, sem informações adicionais.

## Conclusão
O package `turmaDisciplinaProfessor` é central para gerenciar a relação entre **Turma**, **Disciplina** e **Professor** no sistema. Através de uma entidade intermediária e uma chave composta, ele garante que as associações entre essas entidades sejam únicas e gerenciadas de forma eficiente. Os DTOs fornecem flexibilidade na transferência de dados, permitindo tanto operações rápidas com IDs quanto exibições detalhadas para o usuário final.

Este package contribui diretamente para a organização das turmas e para o correto mapeamento das disciplinas ministradas por professores em cada turma, mantendo a estrutura de dados clara e bem definida.

---

# Documentação Técnica - Pacote `usuario`

## Visão Geral
O pacote `usuario` define a estrutura básica para os usuários do sistema. A classe `Usuario` serve como uma superclasse abstrata, fornecendo atributos comuns a diferentes tipos de usuários, como **nome**, **último nome**, **gênero**, **data de nascimento** e **email**. Essa estrutura permite herança em outras classes, como `Aluno`, `Professor`, ou `Coordenador`, centralizando os dados essenciais e as regras de validação comuns a todos.

## Classe `Usuario.java`

### Descrição
A classe `Usuario` é uma superclasse abstrata que contém os atributos principais de um usuário no sistema. Outras classes herdam desta para evitar duplicação de código, centralizando a lógica e os atributos comuns. Como é marcada com `@MappedSuperclass`, seus atributos são compartilhados pelas subclasses, mas não diretamente mapeados para uma tabela no banco de dados.

### Atributos
- **nome**: Nome do usuário, obrigatório, entre 3 e 50 caracteres.
- **ultimoNome**: Sobrenome do usuário, obrigatório, entre 3 e 50 caracteres.
- **gênero**: Gênero do usuário, obrigatório.
- **data_nascimento**: Data de nascimento do usuário, obrigatório e no formato de data.
- **email**: Endereço de email do usuário, obrigatório, com validação de formato e único no banco de dados.

### Anotações Específicas
- `@MappedSuperclass`: Indica que a classe não será mapeada diretamente para uma tabela no banco de dados, mas suas subclasses herdarão seus atributos.
- `@NotNull`: Garante que o campo não pode ser nulo.
- `@Size`: Restringe o tamanho do campo (`nome` e `sobrenome`) entre 3 e 50 caracteres.
- `@Email`: Valida se o campo segue o formato de email.
- `@Temporal(TemporalType.DATE)`: Define que o campo `data_nascimento` será armazenado como uma data.
- `@Column(unique = true)`: Garante que o email seja único no banco de dados.

## Importância
A classe `Usuario` desempenha um papel crucial no sistema, pois padroniza os atributos essenciais de qualquer usuário. Dessa forma, qualquer tipo de usuário (como alunos, professores, coordenadores) pode herdar esses campos e suas validações, promovendo consistência e reaproveitamento de código em todo o sistema.

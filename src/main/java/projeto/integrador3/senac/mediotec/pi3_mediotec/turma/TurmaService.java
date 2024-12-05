package projeto.integrador3.senac.mediotec.pi3_mediotec.turma;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import java.io.IOException;


import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.Aluno;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoResumidoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.arquivo.Arquivo;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.Coordenacao;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoResumidaDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoResumidaDTO2;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.CoordenadorResumidoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.Disciplina;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.DisciplinaDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.DisciplinaRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.DisciplinaResumida2DTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.presenca.PresencaRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.Professor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.Turma;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorId;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TurmaService {

    // ============================= REPOSITORIES =============================
    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private CoordenacaoRepository coordenacaoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private TurmaDisciplinaProfessorRepository turmaDisciplinaProfessorRepository;
    
    @Autowired
    private PresencaRepository presencaRepository;

    
    // ============================= CREATE METHODS =============================

    /**
     * Cria uma nova turma e associa os dados fornecidos, como alunos, disciplinas e professores.
     * 
     * @param turmaDTO Dados resumidos da turma a ser criada.
     * @return TurmaDTO com os detalhes da turma criada.
     */
    @Transactional
    public TurmaDTO saveTurma(TurmaInputDTO turmaDTO) {
        // Cria um novo objeto Turma e define os atributos básicos
        Turma turma = new Turma();	
        turma.setAnoLetivo(turmaDTO.getAnoLetivo());
        turma.setAnoEscolar(turmaDTO.getAnoEscolar()); // Novo atributo anoEscolar
        turma.setTurno(turmaDTO.getTurno());
        turma.setStatus(turmaDTO.isStatus());         // Define status ao criar uma nova turma

        // Associa a coordenação
        Coordenacao coordenacao = coordenacaoRepository.findById(turmaDTO.getCoordenacaoId())
                .orElseThrow(() -> new RuntimeException("Coordenação não encontrada"));
        turma.setCoordenacao(coordenacao);

        // Salva a turma inicialmente para gerar o ID
        Turma savedTurma = turmaRepository.save(turma);

        // Gera o nome da turma baseado no ID
        String nomeGerado = String.format("Turma %02d", savedTurma.getId());
        savedTurma.setNome(nomeGerado);

        // Salva novamente a turma com o nome gerado
        turmaRepository.save(savedTurma);

        // Associa alunos à turma, se fornecidos
        this.associateAlunos(turmaDTO, savedTurma);

        // Associa disciplinas e professores à turma
        this.associateDisciplinasProfessores(turmaDTO, savedTurma);

        // Retorna a turma criada em formato DTO
        return convertToDto(savedTurma);
    }

    // ============================= UPDATE METHODS =============================

    /**
     * Atualiza uma turma existente com base no ID fornecido.
     * 
     * @param id ID da turma a ser atualizada.
     * @param turmaDTO Dados atualizados da turma.
     * @return TurmaDTO com as informações atualizadas.
     */
    @Transactional
    public TurmaDTO updateTurma(Long id, TurmaInputDTO turmaDTO) {
        // Busca a turma existente no banco de dados
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

        // Atualiza os atributos da turma
        turma.setAnoLetivo(turmaDTO.getAnoLetivo());
        turma.setAnoEscolar(turmaDTO.getAnoEscolar()); 
        turma.setTurno(turmaDTO.getTurno());          
        turma.setStatus(turmaDTO.isStatus());          

        // Atualiza a coordenação
        Coordenacao coordenacao = coordenacaoRepository.findById(turmaDTO.getCoordenacaoId())
                .orElseThrow(() -> new RuntimeException("Coordenação não encontrada"));
        turma.setCoordenacao(coordenacao);

        // Gera o nome novamente baseado no ID
        String nomeGerado = String.format("Turma %02d", turma.getId());
        turma.setNome(nomeGerado);

        // Atualiza os alunos associados
        this.associateAlunos(turmaDTO, turma);

        // Remove as associações antigas de Turma-Disciplina-Professor
        turmaDisciplinaProfessorRepository.deleteByTurmaId(turma.getId());

        // Atualiza as disciplinas e professores
        this.associateDisciplinasProfessores(turmaDTO, turma);

        // Salva a turma atualizada e retorna o DTO
        Turma updatedTurma = turmaRepository.save(turma);
        return convertToDto(updatedTurma);
    }
    
    
    
    /**
     * Atualiza o status de uma turma.
     *
     * @param id ID da turma a ser atualizada.
     * @param status Novo status da turma.
     */
    @Transactional
    public void updateStatus(Long id, boolean status) {
        // Busca a turma pelo ID
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

        // Atualiza o status
        turma.setStatus(status);

        // Salva a alteração no repositório
        turmaRepository.save(turma);
    }


    // ============================= DELETE METHODS =============================

    /**
     * Deleta uma turma existente com base no seu ID.
     * 
     * @param id ID da turma a ser deletada.
     */
    @Transactional
    public void deleteTurma(Long id) {
        try {
            System.out.println("Iniciando exclusão da turma com ID " + id);

            // Verifique se a turma existe
            Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));
            System.out.println("Turma encontrada: " + turma.getNome());

            // Obtenha as associações de turma-disciplina-professor
            List<TurmaDisciplinaProfessor> associacoes = turmaDisciplinaProfessorRepository.findByTurmaId(id);
            System.out.println("Associações encontradas: " + associacoes.size());

            // Exclua presenças associadas (se existirem)
            List<Long> turmaDisciplinaProfessorIds = associacoes.stream()
                .map(tdp -> tdp.getId().getTurmaId())
                .distinct()
                .collect(Collectors.toList());

            if (!turmaDisciplinaProfessorIds.isEmpty()) {
                presencaRepository.deleteByTurmaDisciplinaProfessorIds(turmaDisciplinaProfessorIds);
                System.out.println("Presenças removidas.");
            }

            // Exclua as associações de Turma-Disciplina-Professor
            turmaDisciplinaProfessorRepository.deleteByTurmaId(id);
            System.out.println("Associações de Turma-Disciplina-Professor removidas.");

            // Limpe os alunos associados à turma
            if (turma.getAlunos() != null) {
                turma.getAlunos().forEach(aluno -> aluno.getTurmas().remove(turma));
                turma.getAlunos().clear();
                System.out.println("Alunos desvinculados.");
            }

            // Remova o arquivo de horário, se existir
            turma.setArquivoHorario(null);
            System.out.println("Arquivo de horário removido.");

            // Exclua a própria turma
            turmaRepository.delete(turma);
            System.out.println("Turma excluída com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao excluir turma: " + e.getMessage());
            throw new RuntimeException("Erro ao excluir turma: " + e.getMessage(), e);
        }
    }







    // ============================= GET METHODS =============================

    /**
     * Lista todas as turmas cadastradas.
     * 
     * @return Lista de TurmaDTO com as informações de todas as turmas.
     */
    public List<TurmaDTO> getAllTurmas() {
        return turmaRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Busca uma turma pelo ID e retorna suas informações detalhadas.
     * 
     * @param id ID da turma a ser buscada.
     * @return Optional com TurmaDTO caso a turma seja encontrada, ou vazio se não for.
     */
    public Optional<TurmaDTO> getTurmaById(Long id) {
        return turmaRepository.findById(id)
                .map(this::convertToDto);
    }

    // ============================= ASSOCIATION METHODS =============================

    /**
     * Associa alunos à turma com base nos IDs fornecidos.
     */
    private void associateAlunos(TurmaInputDTO turmaDTO, Turma turma) {
        if (turmaDTO.getAlunosIds() != null) {
            // Limpa os alunos atuais
            turma.getAlunos().clear();

            // Associa os novos alunos à turma
            for (Long alunoId : turmaDTO.getAlunosIds()) {
                Aluno aluno = alunoRepository.findById(alunoId)
                        .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
                turma.addAluno(aluno);  // Atualiza a relação aluno-turma
            }
        }
    }

    /**
     * Associa disciplinas e professores à turma.
     */
    private void associateDisciplinasProfessores(TurmaInputDTO turmaDTO, Turma turma) {
        if (turmaDTO.getDisciplinasProfessores() != null) {
            for (DisciplinaProfessorInputDTO dpDTO : turmaDTO.getDisciplinasProfessores()) {
                Professor professor = professorRepository.findById(dpDTO.getProfessorId())
                    .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

                for (Long disciplinaId : dpDTO.getDisciplinasIds()) {
                    Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
                        .orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));

                    // Cria a relação entre Turma, Disciplina e Professor
                    TurmaDisciplinaProfessor turmaDisciplinaProfessor = new TurmaDisciplinaProfessor();
                    turmaDisciplinaProfessor.setId(new TurmaDisciplinaProfessorId(
                        turma.getId(), disciplina.getId(), professor.getCpf()));
                    turmaDisciplinaProfessor.setTurma(turma);
                    turmaDisciplinaProfessor.setDisciplina(disciplina);
                    turmaDisciplinaProfessor.setProfessor(professor);

                    // Salva a relação no repositório
                    turmaDisciplinaProfessorRepository.save(turmaDisciplinaProfessor);
                }
            }
        }
    }

    // ============================= CONVERSÃO =============================

    /**
     * Converte uma Turma para TurmaDTO, incluindo informações de disciplinas e professores.
     */
    private TurmaDTO convertToDto(Turma turma) {
        // Converte os alunos da turma para AlunoResumidoDTO
        Set<AlunoResumidoDTO> alunosDTO = turma.getAlunos() != null ?
            turma.getAlunos().stream()
                .map(aluno -> AlunoResumidoDTO.builder()
                    .id(aluno.getId()) // ID do aluno
                    .nomeAluno(aluno.getNome() + " " + aluno.getUltimoNome()) // Nome completo
                    .email(aluno.getEmail()) // Email
                    .cpf(aluno.getCpf()) // CPF
                    .status(aluno.isStatus()) // Status do aluno (ativo/inativo)
                    .build())
                .collect(Collectors.toSet()) : Collections.emptySet();

        // Converte as disciplinas associadas para DisciplinaResumida2DTO
        Set<DisciplinaResumida2DTO> disciplinasDTO = turma.getTurmaDisciplinaProfessores() != null ?
            turma.getTurmaDisciplinaProfessores().stream()
                .map(turmaDisciplinaProfessor -> turmaDisciplinaProfessor.getDisciplina()) // Obtém as disciplinas
                .distinct()
                .map(disciplina -> DisciplinaResumida2DTO.builder()
                    .nome(disciplina.getNome()) // Nome da disciplina
                    .build())
                .collect(Collectors.toSet()) : Collections.emptySet();

        // Converte disciplinas e professores para DisciplinaProfessorDTO
     // Converte disciplinas e professores para DisciplinaProfessorDTO
        Set<DisciplinaProfessorDTO> disciplinasProfessoresDTO = turma.getTurmaDisciplinaProfessores() != null ?
            turma.getTurmaDisciplinaProfessores().stream()
                .collect(Collectors.groupingBy(
                    tdp -> tdp.getProfessor().getCpf(),
                    Collectors.mapping(tdp -> tdp.getDisciplina(), Collectors.toSet())
                ))
                .entrySet().stream()
                .map(entry -> {
                    String professorCpf = entry.getKey();
                    Set<Disciplina> disciplinas = entry.getValue();

                    // Busca o professor pelo CPF
                    Professor professor = professorRepository.findById(professorCpf)
                        .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

                    // Mapeia os nomes das disciplinas
                    Set<String> nomesDisciplinas = disciplinas.stream()
                        .map(Disciplina::getNome)
                        .collect(Collectors.toSet());

                    // Cria o DTO de DisciplinaProfessor
                    return DisciplinaProfessorDTO.builder()
                        .professorId(professorCpf)
                        .nomeProfessor(professor.getNome() + " " + professor.getUltimoNome())
                        .email(professor.getEmail())
                        .nomesDisciplinas(nomesDisciplinas)
                        .build();
                })
                .collect(Collectors.toSet()) : Collections.emptySet();


        // Mapeia os dados da coordenação para CoordenacaoResumidaDTO
        CoordenacaoResumidaDTO2 coordenacaoDTO = turma.getCoordenacao() != null ?
            CoordenacaoResumidaDTO2.builder()
                .id(turma.getCoordenacao().getId()) // ID da coordenação
                .nome(turma.getCoordenacao().getNome()) // Nome da coordenação
                .coordenadores(turma.getCoordenacao().getCoordenadores().stream()
                    .map(coordenador -> CoordenadorResumidoDTO.builder()
                        .nomeCoordenador(coordenador.getNome() + " " + coordenador.getUltimoNome()) // Nome completo
                        .email(coordenador.getEmail()) // Email
                        .build())
                    .collect(Collectors.toList()))
                .build() : null;

        // Retorna o DTO completo da turma
        return TurmaDTO.builder()
            .id(turma.getId()) // ID da turma
            .nome(turma.getNome()) // Nome da turma
            .anoLetivo(turma.getAnoLetivo()) // Ano letivo
            .anoEscolar(turma.getAnoEscolar()) // Ano escolar
            .turno(turma.getTurno()) // Turno
            .status(turma.isStatus()) // Status da turma (ativo/inativo)
            .coordenacao(coordenacaoDTO) // DTO da coordenação
            .disciplinas(disciplinasDTO) // Disciplinas associadas
            .disciplinasProfessores(disciplinasProfessoresDTO) // Professores e disciplinas
            .horarioArquivoNome(turma.getArquivoHorario() != null ? turma.getArquivoHorario().getNome() : null) // Mapeia o nome do arquivo
            .alunos(alunosDTO) // Alunos associados
            .build();
    }
    
 // --------------------- HORARIO --------------------------- //

    @Transactional
    public TurmaDTO salvarHorario(Long turmaId, MultipartFile arquivo) {
        if (arquivo == null || arquivo.isEmpty()) {
            throw new IllegalArgumentException("Arquivo não pode estar vazio.");
        }

        try {
            // Buscar a turma pelo ID
            Turma turma = turmaRepository.findById(turmaId)
                    .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

            // Criar o objeto Arquivo
            Arquivo novoArquivo = Arquivo.builder()
                    .nome(arquivo.getOriginalFilename())
                    .tipo(arquivo.getContentType())
                    .dados(arquivo.getBytes()) // Salvar os dados do arquivo em bytes
                    .build();

            // Associar o arquivo à turma
            turma.setArquivoHorario(novoArquivo);

            // Salvar a turma no banco (com o arquivo associado)
            turmaRepository.save(turma);

            // Confirme que a transação foi concluída
            System.out.println("Arquivo salvo para a turma ID " + turmaId);

            return convertToDto(turma);

        } catch (IOException e) {
            throw new IllegalStateException("Erro ao processar o arquivo enviado", e);
        }
    }





    // Método para obter o arquivo de horário
    @Transactional(readOnly = true)
    public Arquivo obterHorario(Long turmaId) {
        try {
            // Log do ID recebido
            System.out.println("[DEBUG] Recebido ID da turma para baixar horário: " + turmaId);

            Turma turma = turmaRepository.findById(turmaId)
                    .orElseThrow(() -> {
                        System.err.println("[DEBUG] Turma com ID " + turmaId + " não encontrada no banco de dados.");
                        return new RuntimeException("Turma com ID " + turmaId + " não encontrada.");
                    });

            System.out.println("[DEBUG] Turma encontrada: " + turma.getNome());

            Arquivo horario = turma.getArquivoHorario();

            // Log se o arquivo não estiver associado
            if (horario == null) {
                System.err.println("[DEBUG] Nenhum arquivo de horário associado à turma com ID: " + turmaId);
                throw new RuntimeException("Nenhum arquivo de horário associado.");
            }

            System.out.println("[DEBUG] Arquivo de horário encontrado: " + horario.getNome());
            return horario;

        } catch (Exception e) {
            System.err.println("[ERROR] Erro ao obter horário para a turma ID " + turmaId + ": " + e.getMessage());
            e.printStackTrace(); // Adiciona stacktrace completo para facilitar a depuração
            throw e;
        }
    }

    
    
    @Transactional
    public void deletarHorario(Long turmaId) {
        // Busca a turma pelo ID
        Turma turma = turmaRepository.findById(turmaId)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

        // Verifica se a turma possui um arquivo de horário associado
        if (turma.getArquivoHorario() == null) {
            throw new RuntimeException("Nenhum arquivo de horário encontrado para a turma ID " + turmaId);
        }

        // Remove a referência ao arquivo de horário
        turma.setArquivoHorario(null);

        // Salva a alteração no banco de dados
        turmaRepository.save(turma);
    }
    
    
    
    
    
    
    
 // --------------------- turmas de professores e alunos --------------------------- //
    
    public List<TurmaResumida2DTO> getTurmasByProfessor(String cpf) {
        return turmaRepository.findTurmasByProfessorCpf(cpf).stream()
                .map(turma -> TurmaResumida2DTO.builder()
                        .id(turma.getId())
                        .nome(turma.getNome())
                        .anoLetivo(turma.getAnoLetivo())
                        .anoEscolar(turma.getAnoEscolar())
                        .turno(turma.getTurno())
                        .build())
                .collect(Collectors.toList());
    }

    public List<TurmaResumida2DTO> getTurmasByAluno(Long alunoId) {
        return turmaRepository.findTurmasByAlunoId(alunoId).stream()
                .map(turma -> TurmaResumida2DTO.builder()
                        .id(turma.getId())
                        .nome(turma.getNome())
                        .anoLetivo(turma.getAnoLetivo())
                        .anoEscolar(turma.getAnoEscolar())
                        .turno(turma.getTurno())
                        .build())
                .collect(Collectors.toList());
    }


}

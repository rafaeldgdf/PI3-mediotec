package projeto.integrador3.senac.mediotec.pi3_mediotec.turma;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.Aluno;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoResumidoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.Coordenacao;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoResumidaDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.Disciplina;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.DisciplinaDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.DisciplinaRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.DisciplinaResumida2DTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.Professor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorRepository;
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

    @Transactional
    public TurmaDTO saveTurma(TurmaInputDTO turmaDTO) {
        Turma turma = new Turma();	
        turma.setAnoLetivo(turmaDTO.getAno());
        turma.setAnoEscolar(turmaDTO.getAnoEscolar()); // Novo atributo anoEscolar
        turma.setTurno(turmaDTO.getTurno());
        turma.setStatus(turmaDTO.isStatus());         // Define status ao criar uma nova turma

        // Verifica se a coordenação foi fornecida
        Coordenacao coordenacao = coordenacaoRepository.findById(turmaDTO.getCoordenacaoId())
                .orElseThrow(() -> new RuntimeException("Coordenação não encontrada"));
        turma.setCoordenacao(coordenacao);

        // Salva a turma inicialmente para gerar o ID
        Turma savedTurma = turmaRepository.save(turma);

        // Gerar o nome da turma baseado no ID
        String nomeGerado = String.format("Turma %02d", savedTurma.getId());
        savedTurma.setNome(nomeGerado);

        // Salva novamente para incluir o nome gerado
        turmaRepository.save(savedTurma);

        // Associa alunos à turma, se forem fornecidos
        if (turmaDTO.getAlunosIds() != null && !turmaDTO.getAlunosIds().isEmpty()) {
            for (Long alunoId : turmaDTO.getAlunosIds()) {
                Aluno aluno = alunoRepository.findById(alunoId)
                        .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
                turma.addAluno(aluno);  // Atualiza a relação aluno-turma
            }
        }

        // Associa disciplinas e professores à turma, se forem fornecidos
        if (turmaDTO.getDisciplinasProfessores() != null && !turmaDTO.getDisciplinasProfessores().isEmpty()) {
            for (DisciplinaProfessorInputDTO dpDTO : turmaDTO.getDisciplinasProfessores()) {
                Professor professor = professorRepository.findById(dpDTO.getProfessorId())
                        .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

                for (Long disciplinaId : dpDTO.getDisciplinasIds()) {
                    Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
                            .orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));

                    // Cria a relação Turma-Disciplina-Professor
                    TurmaDisciplinaProfessor turmaDisciplinaProfessor = new TurmaDisciplinaProfessor();
                    turmaDisciplinaProfessor.setId(new TurmaDisciplinaProfessorId(savedTurma.getId(), disciplina.getId(), professor.getCpf()));
                    turmaDisciplinaProfessor.setTurma(savedTurma);
                    turmaDisciplinaProfessor.setDisciplina(disciplina);
                    turmaDisciplinaProfessor.setProfessor(professor);
                    
                    
                    // Salva a relação no repositório de TurmaDisciplinaProfessor
                    turmaDisciplinaProfessorRepository.save(turmaDisciplinaProfessor);
                }
            }
        }

        return convertToDto(savedTurma);
    }




// Atualiza uma turma existente
    @Transactional
    public TurmaDTO updateTurma(Long id, TurmaInputDTO turmaDTO) {
        // Busca a turma existente no banco de dados
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

        // Atualiza os atributos da turma
        turma.setAnoLetivo(turmaDTO.getAno());
        turma.setAnoEscolar(turmaDTO.getAnoEscolar()); 
        turma.setTurno(turmaDTO.getTurno());          
        turma.setStatus(turmaDTO.isStatus());          


        // Atualiza a coordenação se fornecida
        Coordenacao coordenacao = coordenacaoRepository.findById(turmaDTO.getCoordenacaoId())
                .orElseThrow(() -> new RuntimeException("Coordenação não encontrada"));
        turma.setCoordenacao(coordenacao);

        // Gera novamente o nome da turma baseado no ID (mantém a lógica de nome)
        String nomeGerado = String.format("Turma %02d", turma.getId());
        turma.setNome(nomeGerado);

        // Atualiza os alunos se fornecidos
        if (turmaDTO.getAlunosIds() != null) {
            // Limpa os alunos atuais
            turma.getAlunos().clear();

            // Associa os novos alunos
            for (Long alunoId : turmaDTO.getAlunosIds()) {
                Aluno aluno = alunoRepository.findById(alunoId)
                        .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
                turma.addAluno(aluno);  // Atualiza a relação aluno-turma
            }
        }

        // Limpa as associações antigas de Turma-Disciplina-Professor
        turmaDisciplinaProfessorRepository.deleteByTurmaId(turma.getId());

        // Atualiza as disciplinas e professores se fornecidos
        if (turmaDTO.getDisciplinasProfessores() != null) {
            for (DisciplinaProfessorInputDTO dpDTO : turmaDTO.getDisciplinasProfessores()) {
                Professor professor = professorRepository.findById(dpDTO.getProfessorId())
                        .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

                for (Long disciplinaId : dpDTO.getDisciplinasIds()) {
                    Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
                            .orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));

                    // Cria a nova relação entre Turma, Disciplina e Professor
                    TurmaDisciplinaProfessor turmaDisciplinaProfessor = new TurmaDisciplinaProfessor();
                    turmaDisciplinaProfessor.setId(new TurmaDisciplinaProfessorId(turma.getId(), disciplina.getId(), professor.getCpf()));
                    turmaDisciplinaProfessor.setTurma(turma);
                    turmaDisciplinaProfessor.setDisciplina(disciplina);
                    turmaDisciplinaProfessor.setProfessor(professor);

                    // Salva a nova relação no repositório
                    turmaDisciplinaProfessorRepository.save(turmaDisciplinaProfessor);
                }
            }
        }

        // Salva as atualizações da turma
        Turma updatedTurma = turmaRepository.save(turma);

        // Retorna a turma atualizada convertida para o DTO
        return convertToDto(updatedTurma);
    }



 // Método para listar todas as turmas
    public List<TurmaDTO> getAllTurmas() {
        return turmaRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Método para buscar uma turma por ID
    public Optional<TurmaDTO> getTurmaById(Long id) {
        return turmaRepository.findById(id)
                .map(this::convertToDto);
    }
    
    
    // Deleta uma turma
    @Transactional
    public void deleteTurma(Long id) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

        // Deleta todas as relações de Turma-Disciplina-Professor associadas à turma
        turmaDisciplinaProfessorRepository.deleteByTurmaId(turma.getId());

        turmaRepository.delete(turma);
    }
    
    //Pesquisar as disciplinas de uma turma
    public List<DisciplinaDTO> getDisciplinasByTurma(Long turmaId) {
        return turmaDisciplinaProfessorRepository.findByTurmaId(turmaId).stream()
                .map(turmaDisciplinaProfessor -> turmaDisciplinaProfessor.getDisciplina())  // Obtém a disciplina de cada associação
                .distinct()  // Remove duplicatas se houver
                .map(this::convertToDisciplinaDTO)  // Converte para DTO
                .collect(Collectors.toList());
    }
    
    private DisciplinaDTO convertToDisciplinaDTO(Disciplina disciplina) {
        return DisciplinaDTO.builder()
                .id(disciplina.getId())
                .nome(disciplina.getNome())
                .cargaHoraria(disciplina.getCarga_horaria())
                .build();
    }


 // Método auxiliar para converter Turma para TurmaDTO, incluindo nomes de professores e disciplinas
    private TurmaDTO convertToDto(Turma turma) {
        // Converte as disciplinas associadas para DisciplinaResumida2DTO, garantindo que a coleção não seja nula
        Set<DisciplinaResumida2DTO> disciplinasDTO = turma.getTurmaDisciplinaProfessores() != null ?
            turma.getTurmaDisciplinaProfessores().stream()
                .map(turmaDisciplinaProfessor -> turmaDisciplinaProfessor.getDisciplina())  // Obtém as disciplinas
                .distinct()  // Remove duplicatas, se necessário
                .map(disciplina -> DisciplinaResumida2DTO.builder()
                        .nome(disciplina.getNome())
                        .build())
                .collect(Collectors.toSet()) : Collections.emptySet();

        // Mantém o campo disciplinasProfessores, garantindo que a coleção não seja nula
        Set<DisciplinaProfessorDTO> disciplinasProfessoresDTO = turma.getTurmaDisciplinaProfessores() != null ?
            turma.getTurmaDisciplinaProfessores().stream()
                .collect(Collectors.groupingBy(
                    tdp -> tdp.getProfessor().getCpf(),
                    Collectors.mapping(tdp -> tdp.getDisciplina(), Collectors.toSet())
                ))
                .entrySet().stream()
                .map(e -> {
                    Professor professor = professorRepository.findById(e.getKey())
                            .orElseThrow(() -> new RuntimeException("Professor não encontrado: " + e.getKey()));

                    Set<String> nomesDisciplinas = e.getValue().stream()
                            .map(Disciplina::getNome)
                            .collect(Collectors.toSet());

                    return DisciplinaProfessorDTO.builder()
                            .professorId(professor.getCpf())
                            .nomeProfessor(professor.getNome() + " " + professor.getUltimoNome())  // Nome completo do professor
                            .email(professor.getEmail())
                            .nomesDisciplinas(nomesDisciplinas)  // Nomes das disciplinas associadas a esse professor
                            .build();
                })
                .collect(Collectors.toSet()) : Collections.emptySet();

        // Garante que a lista de alunos não seja nula
        Set<AlunoResumidoDTO> alunosDTO = turma.getAlunos() != null ?
            turma.getAlunos().stream()
                .map(aluno -> AlunoResumidoDTO.builder()
                    .id(aluno.getId())
                    .nomeAluno(aluno.getNome() + " " + aluno.getUltimoNome())
                    .email(aluno.getEmail())
                    .build())
                .collect(Collectors.toSet()) : Collections.emptySet();

        return TurmaDTO.builder()
            .id(turma.getId())
            .nome(turma.getNome())  // Nome já gerado automaticamente
            .anoEscolar(turma.getAnoEscolar())  // Novo campo anoEscolar
            .turno(turma.getTurno())            // Novo campo turno
            .status(turma.isStatus())           // Status da turma
            .coordenacao(CoordenacaoResumidaDTO.builder()
                    .nome(turma.getCoordenacao().getNome())
                    .build())
            .disciplinas(disciplinasDTO)  // Adiciona a lista de disciplinas resumidas
            .disciplinasProfessores(disciplinasProfessoresDTO)  // Mantém as disciplinas associadas aos professores
            .alunos(alunosDTO)  // Adiciona a lista de alunos
            .build();
    }



}

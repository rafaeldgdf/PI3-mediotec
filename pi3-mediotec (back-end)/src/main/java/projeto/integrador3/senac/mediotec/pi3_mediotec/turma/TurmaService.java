package projeto.integrador3.senac.mediotec.pi3_mediotec.turma;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.Aluno;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoReduzidoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.Coordenacao;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoResumidaDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.Disciplina;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.DisciplinaDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.DisciplinaRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.Professor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorId;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorRepository;

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
        turma.setNome(turmaDTO.getNome());
        turma.setAno(turmaDTO.getAno());

        // Verifica se a coordenação foi fornecida
        Coordenacao coordenacao = coordenacaoRepository.findById(turmaDTO.getCoordenacaoId())
                .orElseThrow(() -> new RuntimeException("Coordenação não encontrada"));
        turma.setCoordenacao(coordenacao);

        // Adiciona alunos à turma, se forem fornecidos
        if (turmaDTO.getAlunosIds() != null && !turmaDTO.getAlunosIds().isEmpty()) {
            for (Long alunoId : turmaDTO.getAlunosIds()) {
                Aluno aluno = alunoRepository.findById(alunoId)
                        .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
                turma.addAluno(aluno);
            }
        }

        // Salva a turma para gerar o ID
        Turma savedTurma = turmaRepository.save(turma);

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
        turma.setNome(turmaDTO.getNome());
        turma.setAno(turmaDTO.getAno());

        // Verifica se a coordenação foi fornecida e faz a associação
        Coordenacao coordenacao = coordenacaoRepository.findById(turmaDTO.getCoordenacaoId())
                .orElseThrow(() -> new RuntimeException("Coordenação não encontrada"));
        turma.setCoordenacao(coordenacao);

        // Atualiza os alunos apenas se fornecidos
        if (turmaDTO.getAlunosIds() != null) {
            // Limpa a lista atual de alunos
            turma.getAlunos().clear();

            // Associa os novos alunos
            for (Long alunoId : turmaDTO.getAlunosIds()) {
                Aluno aluno = alunoRepository.findById(alunoId)
                        .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
                turma.addAluno(aluno);  // Atualiza a relação bilateral
            }
        }

        // Limpa as associações antigas de Turma-Disciplina-Professor
        turmaDisciplinaProfessorRepository.deleteByTurmaId(turma.getId());

        // Atualiza as disciplinas e professores se fornecidos
        if (turmaDTO.getDisciplinasProfessores() != null) {
            for (DisciplinaProfessorInputDTO dpDTO : turmaDTO.getDisciplinasProfessores()) {
                Professor professor = professorRepository.findById(dpDTO.getProfessorId())
                        .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

                // Associa as disciplinas aos professores
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

        // Retorna a turma atualizada convertida para o DTO, incluindo nomes de professores e disciplinas
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
        return TurmaDTO.builder()
                .id(turma.getId())
                .nome(turma.getNome())
                .ano(turma.getAno())

                // Adicionando a coordenação
                .coordenacao(CoordenacaoResumidaDTO.builder()
                        .id(turma.getCoordenacao().getId())
                        .nome(turma.getCoordenacao().getNome())
                        .descricao(turma.getCoordenacao().getDescricao())
                        .build())

                // Adicionando nome, último nome e ID dos alunos
                .alunos(turma.getAlunos().stream()
                        .map(aluno -> AlunoReduzidoDTO.builder()
                                .id(aluno.getId())
                                .nomeAluno(aluno.getNome() + " " + aluno.getUltimoNome())  // Concatenando corretamente o nome e o último nome
                                .build())
                        .collect(Collectors.toSet()))  // Converte a lista de alunos para DTO de aluno

                // Adicionando nome e último nome dos professores, e as disciplinas associadas
                .disciplinasProfessores(turma.getTurmaDisciplinaProfessores().stream()
                        .collect(Collectors.groupingBy(
                                tdp -> tdp.getProfessor().getCpf(),
                                Collectors.mapping(tdp -> tdp.getDisciplina(), Collectors.toSet())
                        ))
                        .entrySet().stream()
                        .map(e -> {
                            Professor professor = professorRepository.findById(e.getKey())
                                .orElseThrow(() -> new RuntimeException("Professor não encontrado: " + e.getKey()));

                            Set<Long> disciplinasIds = e.getValue().stream()
                                .map(Disciplina::getId)
                                .collect(Collectors.toSet());

                            Set<String> nomesDisciplinas = e.getValue().stream()
                                .map(Disciplina::getNome)
                                .collect(Collectors.toSet());

                            return DisciplinaProfessorDTO.builder()
                                    .professorId(professor.getCpf())
                                    .nomeProfessor(professor.getNome() + " " + professor.getUltimoNome())  // Nome completo do professor
                                    .disciplinasIds(disciplinasIds)  // IDs das disciplinas
                                    .nomesDisciplinas(nomesDisciplinas)  // Nomes das disciplinas
                                    .build();
                        })
                        .collect(Collectors.toSet()))
                .build();
    }




}

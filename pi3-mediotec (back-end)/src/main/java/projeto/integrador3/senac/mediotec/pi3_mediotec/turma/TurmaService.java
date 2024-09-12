package projeto.integrador3.senac.mediotec.pi3_mediotec.turma;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.Aluno;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.Coordenacao;
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
    public TurmaDTO saveTurma(TurmaDTO turmaDTO) {
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
            for (DisciplinaProfessorDTO dpDTO : turmaDTO.getDisciplinasProfessores()) {
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
    public TurmaDTO updateTurma(Long id, TurmaDTO turmaDTO) {
        // Busca a turma existente
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

        // Atualiza os atributos da turma
        turma.setNome(turmaDTO.getNome());
        turma.setAno(turmaDTO.getAno());

        // Busca a coordenação pelo ID e associa à turma
        Coordenacao coordenacao = coordenacaoRepository.findById(turmaDTO.getCoordenacaoId())
                .orElseThrow(() -> new RuntimeException("Coordenação não encontrada"));
        turma.setCoordenacao(coordenacao);

        // Atualiza alunos apenas se fornecidos
        if (turmaDTO.getAlunosIds() != null) {
            // Limpa os alunos atuais e associa os novos alunos
            turma.getAlunos().clear();
            for (Long alunoId : turmaDTO.getAlunosIds()) {
                Aluno aluno = alunoRepository.findById(alunoId)
                        .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
                turma.addAluno(aluno);
            }
        }

        // Limpa as associações de Turma-Disciplina-Professor e adiciona as novas, se fornecidas
        if (turmaDTO.getDisciplinasProfessores() != null) {
            turmaDisciplinaProfessorRepository.deleteByTurmaId(turma.getId());

            // Associa as novas disciplinas e professores
            for (DisciplinaProfessorDTO dpDTO : turmaDTO.getDisciplinasProfessores()) {
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

    // Método auxiliar para converter Turma para TurmaDTO
    private TurmaDTO convertToDto(Turma turma) {
        return TurmaDTO.builder()
                .id(turma.getId())
                .nome(turma.getNome())
                .ano(turma.getAno())
                .coordenacaoId(turma.getCoordenacao().getId())
                .alunosIds(turma.getAlunos().stream().map(Aluno::getId).collect(Collectors.toSet()))
                .disciplinasProfessores(turma.getTurmaDisciplinaProfessores().stream()
                        .collect(Collectors.groupingBy(
                                tdp -> tdp.getProfessor().getCpf(),
                                Collectors.mapping(tdp -> tdp.getDisciplina().getId(), Collectors.toSet())
                        ))
                        .entrySet().stream()
                        .map(e -> DisciplinaProfessorDTO.builder()
                                .professorId(e.getKey())
                                .disciplinasIds(e.getValue())
                                .build())
                        .collect(Collectors.toSet()))
                .build();
    }

}

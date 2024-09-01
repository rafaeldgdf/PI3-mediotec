package projeto.integrador3.senac.mediotec.pi3_mediotec.turma;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.Aluno;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.Coordenacao;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.Disciplina;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.DisciplinaDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.Professor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorDTO; // Importando o novo DTO
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorIdDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessor; // Importando a nova entidade

import java.util.ArrayList;
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

    // Lista todas as turmas
    public List<TurmaDTO> getAllTurmas() {
        return turmaRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Busca turma pelo id
    public Optional<TurmaDTO> getTurmaById(Long id) {
        return turmaRepository.findById(id)
                .map(this::convertToDto);
    }

    @Transactional
    public Turma saveTurma(Turma turma, List<Long> alunosIds) {
        List<Aluno> alunos = new ArrayList<>();
        for (Long alunoId : alunosIds) {
            Aluno aluno = alunoRepository.findById(alunoId)
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
            alunos.add(aluno);
        }

        // Adiciona os alunos à turma
        for (Aluno aluno : alunos) {
            turma.addAluno(aluno);
        }

        return turmaRepository.save(turma);
    }


    public TurmaDTO saveTurma(TurmaDTO turmaDTO) {
        Turma turma = new Turma();
        turma.setNome(turmaDTO.getNome());
        turma.setAno(turmaDTO.getAno());

        Coordenacao coordenacao = coordenacaoRepository.findById(turmaDTO.getCoordenacao().getIdCoordenacao())
                .orElseThrow(() -> new RuntimeException("Coordenação não encontrada"));
        turma.setCoordenacao(coordenacao);

        List<Long> alunosIds = new ArrayList<>(turmaDTO.getAlunosIds());
        for (Long alunoId : alunosIds) {
            Aluno aluno = alunoRepository.findById(alunoId)
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
            turma.addAluno(aluno);
        }

        Turma savedTurma = turmaRepository.save(turma);
        return convertToDto(savedTurma);
    }

    @Transactional
    public TurmaDTO updateTurma(Long id, TurmaDTO turmaDTO) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

        turma.setNome(turmaDTO.getNome());
        turma.setAno(turmaDTO.getAno());

        Coordenacao coordenacao = coordenacaoRepository.findById(turmaDTO.getCoordenacao().getIdCoordenacao())
                .orElseThrow(() -> new RuntimeException("Coordenação não encontrada"));
        turma.setCoordenacao(coordenacao);

        List<Long> alunosIds = new ArrayList<>(turmaDTO.getAlunosIds());
        for (Long alunoId : alunosIds) {
            Aluno aluno = alunoRepository.findById(alunoId)
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
            turma.addAluno(aluno);
        }

        Turma updatedTurma = turmaRepository.save(turma);
        return convertToDto(updatedTurma);
    }


    // Edita turma
    @Transactional
    public TurmaDTO updateTurma(Long id, Turma turmaDetails) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

        turma.setNome(turmaDetails.getNome());
        turma.setAno(turmaDetails.getAno());
        turma.setAlunos(turmaDetails.getAlunos());
        turma.setCoordenacao(turmaDetails.getCoordenacao());
        turma.setTurmaDisciplinaProfessores(turmaDetails.getTurmaDisciplinaProfessores()); // Atualizado para refletir a nova entidade

        Turma updatedTurma = turmaRepository.save(turma);
        return convertToDto(updatedTurma);
    }

    // Deleta turma
    @Transactional
    public void deleteTurma(Long id) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));
        turmaRepository.delete(turma);
    }

// Converte Turma para TurmaDTO
    
    private TurmaDTO convertToDto(Turma turma) {
        return TurmaDTO.builder()
                .idTurma(turma.getId_turma())
                .nome(turma.getNome())
                .ano(turma.getAno())
                .coordenacao(CoordenacaoDTO.builder()
                        .idCoordenacao(turma.getCoordenacao().getId_coordenacao())
                        .nome(turma.getCoordenacao().getNome())
                        .build())
                .alunosIds(turma.getAlunos().stream()
                        .map(Aluno::getId_aluno)
                        .collect(Collectors.toSet()))
                .turmaDisciplinaProfessores(turma.getTurmaDisciplinaProfessores().stream()
                        .map(this::convertToTurmaDisciplinaProfessorDTO) // Usa o método auxiliar aqui
                        .collect(Collectors.toSet()))
                .build();
    }

    
    private ProfessorDTO convertToProfessorDTO(Professor professor) {
        return ProfessorDTO.builder()
                .cpf(professor.getCpf())
                .nome(professor.getNome())
                .build();
    }

    
    
    private DisciplinaDTO convertToDisciplinaDTO(Disciplina disciplina) {
        return DisciplinaDTO.builder()
                .idDisciplina(disciplina.getId_disciplina())
                .nome(disciplina.getNome())
                .cargaHoraria(disciplina.getCarga_horaria())
                .build();
    }

    
    private TurmaDisciplinaProfessorDTO convertToTurmaDisciplinaProfessorDTO(TurmaDisciplinaProfessor turmaDisciplinaProfessor) {
        TurmaDisciplinaProfessorIdDTO idDTO = TurmaDisciplinaProfessorIdDTO.builder()
                .turmaId(turmaDisciplinaProfessor.getId().getTurmaId())
                .disciplinaId(turmaDisciplinaProfessor.getId().getDisciplinaId())
                .professorId(turmaDisciplinaProfessor.getId().getProfessorId())
                .build();

        return TurmaDisciplinaProfessorDTO.builder()
                .id(idDTO)
                .turma(TurmaDTO.builder()
                        .idTurma(turmaDisciplinaProfessor.getTurma().getId_turma())
                        .build())
                .disciplina(convertToDisciplinaDTO(turmaDisciplinaProfessor.getDisciplina())) // Convertendo para DisciplinaDTO
                .professor(convertToProfessorDTO(turmaDisciplinaProfessor.getProfessor())) // Convertendo para ProfessorDTO
                .build();
    }



}

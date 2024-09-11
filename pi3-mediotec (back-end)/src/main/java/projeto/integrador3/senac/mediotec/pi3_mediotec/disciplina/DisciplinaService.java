package projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.Professor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.Turma;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorId;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private TurmaDisciplinaProfessorRepository turmaDisciplinaProfessorRepository;

    // Lista todas as disciplinas
    public List<DisciplinaDTO> getAllDisciplinas() {
        return disciplinaRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Busca disciplina pelo id
    public Optional<DisciplinaDTO> getDisciplinaById(Long id) {
        return disciplinaRepository.findById(id)
                .map(this::convertToDto);
    }

    // Cria nova disciplina e associa a turma e professor
    public DisciplinaDTO saveDisciplina(DisciplinaDTO disciplinaDTO) {
        // Verifica se a turma existe
        Turma turma = turmaRepository.findById(disciplinaDTO.getTurmaId())
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

        // Verifica se o professor existe
        Professor professor = professorRepository.findById(disciplinaDTO.getProfessorId())
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        // Cria a nova disciplina
        Disciplina disciplina = Disciplina.builder()
                .nome(disciplinaDTO.getNome())
                .carga_horaria(disciplinaDTO.getCargaHoraria())
                .build();

        Disciplina savedDisciplina = disciplinaRepository.save(disciplina);

        // Associa o professor e a turma à disciplina
        associarTurmaProfessor(savedDisciplina, turma, professor);

        return convertToDto(savedDisciplina);
    }

    // Atualiza disciplina e suas associações
    public DisciplinaDTO updateDisciplina(Long id, DisciplinaDTO disciplinaDTO) {
        Disciplina disciplina = disciplinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));

        // Verifica se a turma existe
        Turma turma = turmaRepository.findById(disciplinaDTO.getTurmaId())
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

        // Verifica se o professor existe
        Professor professor = professorRepository.findById(disciplinaDTO.getProfessorId())
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        // Atualiza os dados da disciplina
        disciplina.setNome(disciplinaDTO.getNome());
        disciplina.setCarga_horaria(disciplinaDTO.getCargaHoraria());

        Disciplina updatedDisciplina = disciplinaRepository.save(disciplina);

        // Remove as associações anteriores
        turmaDisciplinaProfessorRepository.deleteByDisciplina_Id(disciplina.getId());

        // Associa o professor e a turma novamente
        associarTurmaProfessor(updatedDisciplina, turma, professor);

        return convertToDto(updatedDisciplina);
    }

    // Método para associar a disciplina a uma turma e professor
    private void associarTurmaProfessor(Disciplina disciplina, Turma turma, Professor professor) {
        TurmaDisciplinaProfessor turmaDisciplinaProfessor = new TurmaDisciplinaProfessor();
        turmaDisciplinaProfessor.setId(new TurmaDisciplinaProfessorId(turma.getId(), disciplina.getId(), professor.getCpf()));
        turmaDisciplinaProfessor.setTurma(turma);
        turmaDisciplinaProfessor.setDisciplina(disciplina);
        turmaDisciplinaProfessor.setProfessor(professor);
        turmaDisciplinaProfessorRepository.save(turmaDisciplinaProfessor);
    }

    // Deleta disciplina
    public void deleteDisciplina(Long id) {
        Disciplina disciplina = disciplinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));
        disciplinaRepository.delete(disciplina);
    }

    // Converte Disciplina para DisciplinaDTO
    private DisciplinaDTO convertToDto(Disciplina disciplina) {
        return DisciplinaDTO.builder()
                .id(disciplina.getId())
                .nome(disciplina.getNome())
                .cargaHoraria(disciplina.getCarga_horaria())
                .build();
    }
}

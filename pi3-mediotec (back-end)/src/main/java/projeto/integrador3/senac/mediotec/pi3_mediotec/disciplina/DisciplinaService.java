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

 // Cria nova disciplina e associa a turma e (opcionalmente) professor
    public DisciplinaDTO saveDisciplina(DisciplinaDTO disciplinaDTO) {
        // Verifica se a turma existe
        Turma turma = turmaRepository.findById(disciplinaDTO.getTurmaId())
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

        // Cria a nova disciplina
        Disciplina disciplina = Disciplina.builder()
                .nome(disciplinaDTO.getNome())
                .carga_horaria(disciplinaDTO.getCargaHoraria())
                .build();

        Disciplina savedDisciplina = disciplinaRepository.save(disciplina);

        // Associa a disciplina à turma e (opcionalmente) a um professor
        if (disciplinaDTO.getProfessorId() != null) {
            Professor professor = professorRepository.findById(disciplinaDTO.getProfessorId())
                    .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
            associarTurmaProfessor(savedDisciplina, turma, professor);
        } else {
            associarTurmaProfessorSemProfessor(savedDisciplina, turma);
        }

        return convertToDto(savedDisciplina);
    }



 // Atualiza disciplina e suas associações
    public DisciplinaDTO updateDisciplina(Long id, DisciplinaDTO disciplinaDTO) {
        Disciplina disciplina = disciplinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));

        // Verifica se a turma existe
        Turma turma = turmaRepository.findById(disciplinaDTO.getTurmaId())
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

        // Atualiza os dados da disciplina
        disciplina.setNome(disciplinaDTO.getNome());
        disciplina.setCarga_horaria(disciplinaDTO.getCargaHoraria());

        Disciplina updatedDisciplina = disciplinaRepository.save(disciplina);

        // Remove as associações anteriores
        turmaDisciplinaProfessorRepository.deleteByDisciplina_Id(disciplina.getId());

        // Associa a disciplina à turma e (opcionalmente) a um professor
        if (disciplinaDTO.getProfessorId() != null) {
            Professor professor = professorRepository.findById(disciplinaDTO.getProfessorId())
                    .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
            associarTurmaProfessor(updatedDisciplina, turma, professor);
        } else {
            associarTurmaProfessorSemProfessor(updatedDisciplina, turma);
        }

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
    
 // Método para associar a disciplina a uma turma sem um professor
    private void associarTurmaProfessorSemProfessor(Disciplina disciplina, Turma turma) {
        TurmaDisciplinaProfessor turmaDisciplinaProfessor = new TurmaDisciplinaProfessor();
        
        // Criamos uma nova chave composta para a entidade de associação TurmaDisciplinaProfessor
        turmaDisciplinaProfessor.setId(new TurmaDisciplinaProfessorId(turma.getId(), disciplina.getId(), null));

        // Associamos a turma e a disciplina
        turmaDisciplinaProfessor.setTurma(turma);
        turmaDisciplinaProfessor.setDisciplina(disciplina);

        // O professor é opcional, então não associamos um professor aqui
        turmaDisciplinaProfessorRepository.save(turmaDisciplinaProfessor);
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

package projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.Disciplina;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.DisciplinaRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.Professor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.Turma;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TurmaDisciplinaProfessorService {

    @Autowired
    private TurmaDisciplinaProfessorRepository turmaDisciplinaProfessorRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    // Lista todas as TurmaDisciplinaProfessor
    public List<TurmaDisciplinaProfessor> getAllTurmaDisciplinaProfessores() {
        return turmaDisciplinaProfessorRepository.findAll();
    }

    // Busca TurmaDisciplinaProfessor pelo id (composto)
    public Optional<TurmaDisciplinaProfessor> getTurmaDisciplinaProfessorById(TurmaDisciplinaProfessorId id) {
        return turmaDisciplinaProfessorRepository.findById(id);
    }

    // Cria nova TurmaDisciplinaProfessor
    public TurmaDisciplinaProfessor saveTurmaDisciplinaProfessor(TurmaDisciplinaProfessor turmaDisciplinaProfessor) {
        // Validação de existência de Turma, Disciplina e Professor
        validateTurmaDisciplinaProfessor(turmaDisciplinaProfessor);

        // Criação da chave composta
        TurmaDisciplinaProfessorId id = new TurmaDisciplinaProfessorId();
        id.setTurmaId(turmaDisciplinaProfessor.getTurma().getId_turma());
        id.setDisciplinaId(turmaDisciplinaProfessor.getDisciplina().getId_disciplina());
        id.setProfessorId(turmaDisciplinaProfessor.getProfessor().getCpf()); // Adiciona o professorId

        turmaDisciplinaProfessor.setId(id);

        return turmaDisciplinaProfessorRepository.save(turmaDisciplinaProfessor);
    }

    // Edita TurmaDisciplinaProfessor
    public TurmaDisciplinaProfessor updateTurmaDisciplinaProfessor(TurmaDisciplinaProfessorId id, TurmaDisciplinaProfessor turmaDisciplinaProfessorDetails) {
        TurmaDisciplinaProfessor turmaDisciplinaProfessor = turmaDisciplinaProfessorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TurmaDisciplinaProfessor não encontrado"));

        turmaDisciplinaProfessor.setProfessor(turmaDisciplinaProfessorDetails.getProfessor());

        return turmaDisciplinaProfessorRepository.save(turmaDisciplinaProfessor);
    }

    // Deleta TurmaDisciplinaProfessor
    public void deleteTurmaDisciplinaProfessor(TurmaDisciplinaProfessorId id) {
        TurmaDisciplinaProfessor turmaDisciplinaProfessor = turmaDisciplinaProfessorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TurmaDisciplinaProfessor não encontrado"));
        turmaDisciplinaProfessorRepository.delete(turmaDisciplinaProfessor);
    }

    // Método para validar se Turma, Disciplina e Professor existem
    private void validateTurmaDisciplinaProfessor(TurmaDisciplinaProfessor turmaDisciplinaProfessor) {
        Optional<Turma> turma = turmaRepository.findById(turmaDisciplinaProfessor.getTurma().getId_turma());
        if (turma.isEmpty()) {
            throw new RuntimeException("Turma não encontrada");
        }

        Optional<Disciplina> disciplina = disciplinaRepository.findById(turmaDisciplinaProfessor.getDisciplina().getId_disciplina());
        if (disciplina.isEmpty()) {
            throw new RuntimeException("Disciplina não encontrada");
        }

        Optional<Professor> professor = professorRepository.findById(turmaDisciplinaProfessor.getProfessor().getCpf());
        if (professor.isEmpty()) {
            throw new RuntimeException("Professor não encontrado");
        }
    }
}

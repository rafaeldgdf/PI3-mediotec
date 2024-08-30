package projeto.integrador3.senac.mediotec.pi3_mediotec.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.Turma;
import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.Disciplina;
import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.Professor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.TurmaDisciplina;
import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.TurmaDisciplinaId;
import projeto.integrador3.senac.mediotec.pi3_mediotec.repositories.TurmaDisciplinaRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.repositories.TurmaRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.repositories.DisciplinaRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.repositories.ProfessorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TurmaDisciplinaService {

    @Autowired
    private TurmaDisciplinaRepository turmaDisciplinaRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    // Lista todas as TurmaDisciplinas
    public List<TurmaDisciplina> getAllTurmaDisciplinas() {
        return turmaDisciplinaRepository.findAll();
    }

    // Busca TurmaDisciplina pelo id (composto)
    public Optional<TurmaDisciplina> getTurmaDisciplinaById(TurmaDisciplinaId id) {
        return turmaDisciplinaRepository.findById(id);
    }

    // Cria nova TurmaDisciplina
    public TurmaDisciplina saveTurmaDisciplina(TurmaDisciplina turmaDisciplina) {
        // Validação de existência de Turma, Disciplina e Professor
        validateTurmaDisciplina(turmaDisciplina);

        // Criação da chave composta
        TurmaDisciplinaId id = new TurmaDisciplinaId();
        id.setTurmaId(turmaDisciplina.getTurma().getId_turma());
        id.setDisciplinaId(turmaDisciplina.getDisciplina().getId_disciplina());
        turmaDisciplina.setId(id);

        return turmaDisciplinaRepository.save(turmaDisciplina);
    }

    // Edita TurmaDisciplina
    public TurmaDisciplina updateTurmaDisciplina(TurmaDisciplinaId id, TurmaDisciplina turmaDisciplinaDetails) {
        TurmaDisciplina turmaDisciplina = turmaDisciplinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TurmaDisciplina não encontrada"));

        turmaDisciplina.setProfessor(turmaDisciplinaDetails.getProfessor());

        return turmaDisciplinaRepository.save(turmaDisciplina);
    }

    // Deleta TurmaDisciplina
    public void deleteTurmaDisciplina(TurmaDisciplinaId id) {
        TurmaDisciplina turmaDisciplina = turmaDisciplinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TurmaDisciplina não encontrada"));
        turmaDisciplinaRepository.delete(turmaDisciplina);
    }

    // Método para validar se Turma, Disciplina e Professor existem
    private void validateTurmaDisciplina(TurmaDisciplina turmaDisciplina) {
        Optional<Turma> turma = turmaRepository.findById(turmaDisciplina.getTurma().getId_turma());
        if (turma.isEmpty()) {
            throw new RuntimeException("Turma não encontrada");
        }

        Optional<Disciplina> disciplina = disciplinaRepository.findById(turmaDisciplina.getDisciplina().getId_disciplina());
        if (disciplina.isEmpty()) {
            throw new RuntimeException("Disciplina não encontrada");
        }

        Optional<Professor> professor = professorRepository.findById(turmaDisciplina.getProfessor().getCpf());
        if (professor.isEmpty()) {
            throw new RuntimeException("Professor não encontrado");
        }
    }
}

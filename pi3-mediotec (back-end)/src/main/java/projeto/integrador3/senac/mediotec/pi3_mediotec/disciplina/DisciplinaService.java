package projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.Professor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.Turma;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorId;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorRepository;

import java.util.List;
import java.util.Optional;
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

    // Lista todas as disciplinas (GET - usando DisciplinaGetDTO)
    public List<DisciplinaGetDTO> getAllDisciplinas() {
        return disciplinaRepository.findAll().stream()
                .map(this::convertToDisciplinaGetDTO)  // Convertendo para DisciplinaGetDTO
                .collect(Collectors.toList());
    }

    // Busca disciplina pelo id (GET - usando DisciplinaGetDTO)
    public Optional<DisciplinaGetDTO> getDisciplinaById(Long id) {
        return disciplinaRepository.findById(id)
                .map(this::convertToDisciplinaGetDTO);  // Convertendo para DisciplinaGetDTO
    }

 // Cria nova disciplina e associa à turma e (opcionalmente) professor (POST)
    public DisciplinaResumidaDTO saveDisciplina(DisciplinaDTO disciplinaDTO) {
        // Cria a nova disciplina
        Disciplina disciplina = Disciplina.builder()
                .nome(disciplinaDTO.getNome())
                .carga_horaria(disciplinaDTO.getCargaHoraria())
                .build();

        Disciplina savedDisciplina = disciplinaRepository.save(disciplina);

        // Associa a disciplina à turma se o ID da turma for fornecido
        if (disciplinaDTO.getTurmaId() != null) {
            Turma turma = turmaRepository.findById(disciplinaDTO.getTurmaId())
                    .orElseThrow(() -> new RuntimeException("Turma não encontrada"));
            
            // Associa a disciplina a uma turma e professor, se o professor for fornecido
            if (disciplinaDTO.getProfessorId() != null) {
                Professor professor = professorRepository.findById(disciplinaDTO.getProfessorId())
                        .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
                associarTurmaProfessor(savedDisciplina, turma, professor);
            } else {
                associarTurmaProfessorSemProfessor(savedDisciplina, turma);
            }
        }

        // Retorna o DTO resumido (DisciplinaResumidaDTO)
        return convertToResumidaDto(savedDisciplina);
    }


 // Atualiza disciplina e suas associações (PUT)
    public DisciplinaResumidaDTO updateDisciplina(Long id, DisciplinaDTO disciplinaDTO) {
        Disciplina disciplina = disciplinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));

        // Atualiza os dados da disciplina
        disciplina.setNome(disciplinaDTO.getNome());
        disciplina.setCarga_horaria(disciplinaDTO.getCargaHoraria());

        Disciplina updatedDisciplina = disciplinaRepository.save(disciplina);

        // Remove as associações anteriores
        turmaDisciplinaProfessorRepository.deleteByDisciplina_Id(disciplina.getId());

        // Associa a disciplina à turma se o ID da turma for fornecido
        if (disciplinaDTO.getTurmaId() != null) {
            Turma turma = turmaRepository.findById(disciplinaDTO.getTurmaId())
                    .orElseThrow(() -> new RuntimeException("Turma não encontrada"));
            
            // Associa a disciplina a uma turma e professor, se o professor for fornecido
            if (disciplinaDTO.getProfessorId() != null) {
                Professor professor = professorRepository.findById(disciplinaDTO.getProfessorId())
                        .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
                associarTurmaProfessor(updatedDisciplina, turma, professor);
            } else {
                associarTurmaProfessorSemProfessor(updatedDisciplina, turma);
            }
        }

        // Retorna o DTO resumido (DisciplinaResumidaDTO)
        return convertToResumidaDto(updatedDisciplina);
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

    // Método para associar a disciplina a uma turma sem um professor
    private void associarTurmaProfessorSemProfessor(Disciplina disciplina, Turma turma) {
        TurmaDisciplinaProfessor turmaDisciplinaProfessor = new TurmaDisciplinaProfessor();
        turmaDisciplinaProfessor.setId(new TurmaDisciplinaProfessorId(turma.getId(), disciplina.getId(), null));
        turmaDisciplinaProfessor.setTurma(turma);
        turmaDisciplinaProfessor.setDisciplina(disciplina);
        turmaDisciplinaProfessorRepository.save(turmaDisciplinaProfessor);
    }

    // Deleta disciplina (DELETE)
    public void deleteDisciplina(Long id) {
        Disciplina disciplina = disciplinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));
        disciplinaRepository.delete(disciplina);
    }

 // Converte Disciplina para DisciplinaResumidaDTO (para o POST/PUT)
    private DisciplinaResumidaDTO convertToResumidaDto(Disciplina disciplina) {
        // Busca as associações com turma e professor, se houver
        List<TurmaDisciplinaProfessor> turmaDisciplinaProfessores = turmaDisciplinaProfessorRepository
            .findByDisciplinaId(disciplina.getId());

        // Verifica se há associações, mas não lança exceção se estiverem vazias
        TurmaDisciplinaProfessor turmaDisciplinaProfessor = !turmaDisciplinaProfessores.isEmpty() 
            ? turmaDisciplinaProfessores.get(0) 
            : null; // Pega a primeira associação, se houver

        // Retorna os campos relevantes, sem lançar exceções, e permitindo que as associações sejam nulas
        return DisciplinaResumidaDTO.builder()
            .id(disciplina.getId())
            .nome(disciplina.getNome())
            .cargaHoraria(disciplina.getCarga_horaria())
            .build();
    }


 // Converte Disciplina para DisciplinaGetDTO (para o GET)
    private DisciplinaGetDTO convertToDisciplinaGetDTO(Disciplina disciplina) {
        // Busca as associações com turma e professor, se houver
        List<TurmaDisciplinaProfessor> turmaDisciplinaProfessores = turmaDisciplinaProfessorRepository
            .findByDisciplinaId(disciplina.getId());

        // Se não houver associações, consideramos nulo para professor/turma
        TurmaDisciplinaProfessor turmaDisciplinaProfessor = !turmaDisciplinaProfessores.isEmpty()
            ? turmaDisciplinaProfessores.get(0)
            : null;  // Pega a primeira associação, se existir

        // Retorna os campos relevantes para GET, verificando se há associações
        return DisciplinaGetDTO.builder()
            .id(disciplina.getId())
            .nome(disciplina.getNome())
            .carga_horaria(disciplina.getCarga_horaria())
            .nomeTurma(turmaDisciplinaProfessor != null && turmaDisciplinaProfessor.getTurma() != null
                ? turmaDisciplinaProfessor.getTurma().getNome()
                : null)  // Nome da turma, se houver
            .nomeProfessor(turmaDisciplinaProfessor != null && turmaDisciplinaProfessor.getProfessor() != null
                ? turmaDisciplinaProfessor.getProfessor().getNome() + " " + turmaDisciplinaProfessor.getProfessor().getUltimoNome()
                : null)  // Nome completo do professor, se houver
            .build();
    }


}

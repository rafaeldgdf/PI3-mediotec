package projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.Professor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorResumidoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.Turma;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaResumidaDTO;
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

    // Lista todas as disciplinas (GET - mais detalhado)
    public List<DisciplinaResumidaDTO> getAllDisciplinas() {
        return disciplinaRepository.findAll().stream()
                .map(this::convertToResumidaDto)  // Convertendo para o DTO mais completo
                .collect(Collectors.toList());
    }

    // Busca disciplina pelo id (GET - mais detalhado)
    public Optional<DisciplinaResumidaDTO> getDisciplinaById(Long id) {
        return disciplinaRepository.findById(id)
                .map(this::convertToResumidaDto);  // Convertendo para o DTO mais completo
    }

    // Cria nova disciplina e associa a turma e (opcionalmente) professor (POST)
    public DisciplinaResumidaDTO saveDisciplina(DisciplinaDTO disciplinaDTO) {
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

        // Retorna o DTO mais completo (DisciplinaResumidaDTO)
        return convertToResumidaDto(savedDisciplina);
    }

    // Atualiza disciplina e suas associações (PUT)
    public DisciplinaResumidaDTO updateDisciplina(Long id, DisciplinaDTO disciplinaDTO) {
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

        // Retorna o DTO mais completo (DisciplinaResumidaDTO)
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

    // Converte Disciplina para DisciplinaResumidaDTO (GET mais completo)
    private DisciplinaResumidaDTO convertToResumidaDto(Disciplina disciplina) {
        // Busca as associações com turma e professor
        List<TurmaDisciplinaProfessor> turmaDisciplinaProfessores = turmaDisciplinaProfessorRepository
            .findByDisciplinaId(disciplina.getId());

        if (turmaDisciplinaProfessores.isEmpty()) {
            throw new RuntimeException("Associação de Disciplina com Turma e Professor não encontrada");
        }

        TurmaDisciplinaProfessor turmaDisciplinaProfessor = turmaDisciplinaProfessores.get(0);  // Pega a primeira associação

        // Detalhes simples da turma
        TurmaResumidaDTO turmaDTO = TurmaResumidaDTO.builder()
            .id(turmaDisciplinaProfessor.getTurma().getId())
            .nome(turmaDisciplinaProfessor.getTurma().getNome())
            .ano(turmaDisciplinaProfessor.getTurma().getAno())
            .build();

        // Detalhes simples do professor (caso exista)
        ProfessorResumidoDTO professorDTO = null;
        if (turmaDisciplinaProfessor.getProfessor() != null) {
            professorDTO = ProfessorResumidoDTO.builder()
                .cpf(turmaDisciplinaProfessor.getProfessor().getCpf())
                .nome(turmaDisciplinaProfessor.getProfessor().getNome())
                .ultimoNome(turmaDisciplinaProfessor.getProfessor().getUltimoNome())
                .build();
        }

        // Retorna o DTO simplificado
        return DisciplinaResumidaDTO.builder()
                .id(disciplina.getId())
                .nome(disciplina.getNome())
                .cargaHoraria(disciplina.getCarga_horaria())
                .turma(turmaDTO)  // Adiciona detalhes simples da turma
                .professor(professorDTO)  // Adiciona detalhes simples do professor
                .build();
    }

}

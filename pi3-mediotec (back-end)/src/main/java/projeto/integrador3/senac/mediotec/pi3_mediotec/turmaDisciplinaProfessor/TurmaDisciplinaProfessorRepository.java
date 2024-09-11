package projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

public interface TurmaDisciplinaProfessorRepository extends JpaRepository<TurmaDisciplinaProfessor, TurmaDisciplinaProfessorId> {
    List<TurmaDisciplinaProfessor> findByTurmaId(Long turmaId);
    List<TurmaDisciplinaProfessor> findByDisciplinaId(Long disciplinaId);
    List<TurmaDisciplinaProfessor> findById_ProfessorId(String professorId);
    List<TurmaDisciplinaProfessor> findById_TurmaIdAndId_DisciplinaId(Long turmaId, Long disciplinaId);
    void deleteByTurmaId(Long turmaId);
    void deleteByDisciplina_Id(Long disciplinaId);
}





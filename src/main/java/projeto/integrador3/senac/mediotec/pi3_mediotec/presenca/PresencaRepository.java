package projeto.integrador3.senac.mediotec.pi3_mediotec.presenca;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.Aluno;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.Disciplina;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.Professor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorId;

import java.util.List;

@Repository
public interface PresencaRepository extends JpaRepository<Presenca, Long> {
    List<Presenca> findByAluno(Aluno aluno);
    List<Presenca> findByAlunoAndTurmaDisciplinaProfessor_Disciplina(Aluno aluno, Disciplina disciplina);
    List<Presenca> findByTurmaDisciplinaProfessor_Professor(Professor professor);
    
    
    @Query("SELECT p FROM Presenca p WHERE " +
    	       "p.turmaDisciplinaProfessor.id.turmaId = :idTurma AND " +
    	       "p.turmaDisciplinaProfessor.id.disciplinaId = :idDisciplina")
    	List<Presenca> findByTurmaAndDisciplina(@Param("idTurma") Long idTurma,
    	                                        @Param("idDisciplina") Long idDisciplina);
    
    
    @Transactional
    @Modifying
    @Query("DELETE FROM Presenca p WHERE p.turmaDisciplinaProfessor.id.turmaId = :turmaId")
    void deleteByTurmaDisciplinaProfessorIds(@Param("turmaId") Long turmaId);


    
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Presenca p WHERE p.turmaDisciplinaProfessor.id.turmaId IN :ids")
    void deleteByTurmaDisciplinaProfessorIds(@Param("ids") List<Long> ids);


    

}

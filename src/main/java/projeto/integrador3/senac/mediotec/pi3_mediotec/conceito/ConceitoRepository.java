package projeto.integrador3.senac.mediotec.pi3_mediotec.conceito;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorId;

import java.util.List;
import java.util.Optional;

public interface ConceitoRepository extends JpaRepository<Conceito, Long> {
	List<Conceito> findByAluno_Id(Long id);
    List<Conceito> findByTurmaDisciplinaProfessor_Id(TurmaDisciplinaProfessorId id); 
    


    // Busca conceitos de um aluno por uma disciplina específica
    List<Conceito> findByAluno_IdAndTurmaDisciplinaProfessor_Disciplina_Id(Long alunoId, Long disciplinaId);
    
    Optional<Conceito> findByAluno_IdAndTurmaDisciplinaProfessor_Turma_IdAndTurmaDisciplinaProfessor_Disciplina_IdAndTurmaDisciplinaProfessor_Professor_Cpf(
            Long idAluno, Long idTurma, Long idDisciplina, String cpfProfessor);
    
    @Modifying
    @Query("DELETE FROM Conceito c WHERE c.aluno.id = :idAluno")
    void deleteByAlunoId(@Param("idAluno") Long idAluno);
}


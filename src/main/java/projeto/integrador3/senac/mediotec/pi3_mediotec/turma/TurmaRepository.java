package projeto.integrador3.senac.mediotec.pi3_mediotec.turma;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TurmaRepository extends JpaRepository<Turma, Long> {
	Optional<Turma> findById(Long id);
	
    @Query("SELECT t.id FROM Turma t")
    List<Long> findAllIds();
    
    
    @Query("SELECT t FROM Turma t JOIN t.turmaDisciplinaProfessores tdp WHERE tdp.professor.cpf = :cpf")
    List<Turma> findTurmasByProfessorCpf(@Param("cpf") String cpf);
    
    @Query("SELECT t FROM Turma t JOIN t.alunos a WHERE a.id = :alunoId")
    List<Turma> findTurmasByAlunoId(@Param("alunoId") Long alunoId);

}


package projeto.integrador3.senac.mediotec.pi3_mediotec.conceito;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorId;

import java.util.List;

public interface ConceitoRepository extends JpaRepository<Conceito, Long> {
	List<Conceito> findByAluno_Id(Long id);
    List<Conceito> findByTurmaDisciplinaProfessor_Id(TurmaDisciplinaProfessorId id); 
}

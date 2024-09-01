package projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TurmaDisciplinaProfessorRepository extends JpaRepository<TurmaDisciplinaProfessor, TurmaDisciplinaProfessorId> {
	Optional<TurmaDisciplinaProfessor> findById(TurmaDisciplinaProfessorId id);
}


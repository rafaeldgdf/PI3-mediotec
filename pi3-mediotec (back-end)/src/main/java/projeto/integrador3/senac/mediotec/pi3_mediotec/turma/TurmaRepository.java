package projeto.integrador3.senac.mediotec.pi3_mediotec.turma;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TurmaRepository extends JpaRepository<Turma, Long> {
	Optional<Turma> findById(Long id);
}


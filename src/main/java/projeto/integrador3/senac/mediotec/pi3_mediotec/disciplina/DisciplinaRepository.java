package projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
	Optional<Disciplina> findById(Long id);
	

	
}


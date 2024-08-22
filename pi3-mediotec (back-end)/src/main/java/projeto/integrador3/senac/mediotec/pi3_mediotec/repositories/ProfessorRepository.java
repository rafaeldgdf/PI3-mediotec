package projeto.integrador3.senac.mediotec.pi3_mediotec.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, String> {
}

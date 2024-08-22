package projeto.integrador3.senac.mediotec.pi3_mediotec.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}


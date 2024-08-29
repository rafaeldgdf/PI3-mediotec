package projeto.integrador3.senac.mediotec.pi3_mediotec.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
	boolean existsByCpf(String cpf);
	
}


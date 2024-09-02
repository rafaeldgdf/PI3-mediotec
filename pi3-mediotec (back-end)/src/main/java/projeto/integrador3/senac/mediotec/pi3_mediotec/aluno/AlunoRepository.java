package projeto.integrador3.senac.mediotec.pi3_mediotec.aluno;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
	boolean existsByCpf(String cpf);
	Optional<Aluno> findById(Long id);
	
}


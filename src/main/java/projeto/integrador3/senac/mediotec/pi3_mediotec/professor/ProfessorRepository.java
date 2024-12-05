package projeto.integrador3.senac.mediotec.pi3_mediotec.professor;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.Coordenador;

public interface ProfessorRepository extends JpaRepository<Professor, String> {
	boolean existsByCpf(String cpf);
	Optional<Professor> findByCpf(String cpf); 
	Optional<Professor> findByEmail(String email);
	
}

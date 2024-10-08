package projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CoordenadorRepository extends JpaRepository<Coordenador, String> {
	boolean existsByCpf(String cpf);
	Optional<Coordenador> findByCpf(String cpf);
}

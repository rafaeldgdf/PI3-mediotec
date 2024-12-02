package projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import projeto.integrador3.senac.mediotec.pi3_mediotec.comunicado.Comunicado;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.Coordenador;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.Endereco;

public interface CoordenacaoRepository extends JpaRepository<Coordenacao, Long> {
	Optional<Coordenacao> findById(Long id);
	boolean existsByIdAndCoordenadores_Cpf(Long coordenacaoId, String cpf);
	Optional<Coordenacao> findByCoordenadores_Cpf(String cpf);
	
}


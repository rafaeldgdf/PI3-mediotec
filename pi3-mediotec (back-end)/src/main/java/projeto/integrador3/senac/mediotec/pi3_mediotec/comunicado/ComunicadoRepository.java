package projeto.integrador3.senac.mediotec.pi3_mediotec.comunicado;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ComunicadoRepository extends JpaRepository<Comunicado, Long> {

	List<Comunicado> findByEmitente_Professor_Cpf(String cpf);
	
    List<Comunicado> findByEmitente_Coordenacao_Id(Long coordenacaoId);

    List<Comunicado> findByReceptor_Aluno_Id(Long id);

    List<Comunicado> findByReceptor_Turma_Id(Long id);
;
}

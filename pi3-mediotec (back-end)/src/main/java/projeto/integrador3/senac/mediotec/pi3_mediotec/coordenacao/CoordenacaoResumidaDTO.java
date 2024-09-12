package projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.CoordenadorResumidoDTO;

@Builder
@Data
public class CoordenacaoResumidaDTO {
    private Long id;
    private String nome;
    private String descricao;
    private CoordenadorResumidoDTO coordenador; 
}

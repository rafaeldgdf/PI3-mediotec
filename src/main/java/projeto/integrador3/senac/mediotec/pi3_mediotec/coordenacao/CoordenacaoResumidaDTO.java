package projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao;

import java.util.Set;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.CoordenadorResumidoDTO;

@Builder
@Data
public class CoordenacaoResumidaDTO {
    private Long id;
    private String nome;
    private Set<CoordenadorResumidoDTO> coordenadores;  // Alterando para Set ao invés de List

    // Adicionando o construtor público explicitamente
    public CoordenacaoResumidaDTO(Long id, String nome, Set<CoordenadorResumidoDTO> coordenadores) {
        this.id = id;
        this.nome = nome;
        this.coordenadores = coordenadores;
    }
}

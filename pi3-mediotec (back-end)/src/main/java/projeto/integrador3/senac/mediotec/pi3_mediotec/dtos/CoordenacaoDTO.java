package projeto.integrador3.senac.mediotec.pi3_mediotec.dtos;

import lombok.Data;
import java.util.Set;

@Data
public class CoordenacaoDTO {
    private Long idCoordenacao;
    private String nome;
    private String descricao;
    private Set<EnderecoDTO> enderecos;
    private Set<TelefoneDTO> telefones;
    private CoordenadorDTO coordenador;
}

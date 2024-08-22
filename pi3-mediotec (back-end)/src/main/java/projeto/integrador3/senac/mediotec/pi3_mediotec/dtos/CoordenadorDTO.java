package projeto.integrador3.senac.mediotec.pi3_mediotec.dtos;

import lombok.Data;
import java.util.Set;

@Data
public class CoordenadorDTO {
    private String cpf;
    private String nome;
    private String genero;
    private String email;
    private Set<EnderecoDTO> enderecos;
    private Set<TelefoneDTO> telefones;
    private CoordenacaoDTO coordenacao;
}

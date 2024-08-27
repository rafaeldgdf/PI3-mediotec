package projeto.integrador3.senac.mediotec.pi3_mediotec.dtos;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.Presenca;

import java.util.Set;


@Builder
@Data
public class AlunoDTO {
    private Long idAluno;
    private String nome;
    private String genero;
    private String cpf;
    private String email;
    private Set<EnderecoDTO> enderecos;
    private Set<TelefoneDTO> telefones;
    private CoordenacaoDTO coordenacao;
    private Set<Presenca> presencas;
}

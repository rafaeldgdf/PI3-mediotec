package projeto.integrador3.senac.mediotec.pi3_mediotec.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Builder 
@Data
public class CoordenadorDTO {
    private String cpf;
    private String nome;
    private String ultimoNome;
    private String genero;
    private Date data_nascimento;
    private String email;
    private Set<EnderecoDTO> enderecos;
    private Set<TelefoneDTO> telefones;
}

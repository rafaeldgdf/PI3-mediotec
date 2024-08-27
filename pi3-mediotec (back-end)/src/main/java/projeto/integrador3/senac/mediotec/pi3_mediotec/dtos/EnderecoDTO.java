package projeto.integrador3.senac.mediotec.pi3_mediotec.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Data;


@Builder 
@Data
@JsonIgnoreProperties({"aluno", "professor", "coordenador", "coordenacao"})
public class EnderecoDTO {
    private Long idEndereco;
    private String cep;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
}


package projeto.integrador3.senac.mediotec.pi3_mediotec.dtos;

import lombok.Data;

@Data
public class EnderecoDTO {
    private Long idEndereco;
    private String cep;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
}


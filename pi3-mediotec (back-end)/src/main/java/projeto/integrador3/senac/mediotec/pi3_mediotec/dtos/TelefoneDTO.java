package projeto.integrador3.senac.mediotec.pi3_mediotec.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonIgnoreProperties({"aluno", "professor", "coordenador", "coordenacao"})
public class TelefoneDTO {
    private Long id;
    private String ddd;
    private String numero;
}

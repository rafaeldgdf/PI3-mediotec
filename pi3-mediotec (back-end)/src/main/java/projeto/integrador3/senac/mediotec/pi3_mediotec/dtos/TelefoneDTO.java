package projeto.integrador3.senac.mediotec.pi3_mediotec.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TelefoneDTO {
    private Long id;
    private String ddd;
    private String numero;
}

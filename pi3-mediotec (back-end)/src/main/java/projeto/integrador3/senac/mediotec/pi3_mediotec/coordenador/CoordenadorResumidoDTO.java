package projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CoordenadorResumidoDTO {
    private String cpf;
    private String nome;
    private String ultimoNome;
    
}

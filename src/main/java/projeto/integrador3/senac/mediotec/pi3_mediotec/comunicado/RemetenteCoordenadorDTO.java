package projeto.integrador3.senac.mediotec.pi3_mediotec.comunicado;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemetenteCoordenadorDTO {
    private String nome;
    private String coordenacao; // Nome da coordenação
}

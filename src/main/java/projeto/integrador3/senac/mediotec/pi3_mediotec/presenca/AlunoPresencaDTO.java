package projeto.integrador3.senac.mediotec.pi3_mediotec.presenca;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AlunoPresencaDTO {
	private Long idPresenca; 
    private Long idAluno;
    private String nome;
    private boolean presenca;


}

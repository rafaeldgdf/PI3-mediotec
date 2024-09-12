package projeto.integrador3.senac.mediotec.pi3_mediotec.aluno;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AlunoResumidoDTO {
    private Long id;  // ID do aluno
    private String nomeAluno;  // Nome do aluno
    private String email;
}

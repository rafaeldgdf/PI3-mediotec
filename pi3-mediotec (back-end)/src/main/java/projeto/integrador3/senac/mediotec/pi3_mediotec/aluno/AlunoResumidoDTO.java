package projeto.integrador3.senac.mediotec.pi3_mediotec.aluno;

import java.util.Set;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.responsavel.ResponsavelDTO;

@Builder
@Data
public class AlunoResumidoDTO {
    private String nomeAluno;  // Nome do aluno
    private String email;
}

package projeto.integrador3.senac.mediotec.pi3_mediotec.aluno;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
@AllArgsConstructor  // Gera o construtor necessário
public class AlunoResumidoDTO {
    private Long id; // ID do aluno
    private String nomeAluno; // Nome completo
    private String email; // Email
    private String cpf; // CPF
    private boolean status; // Status do aluno (ativo/inativo)
}


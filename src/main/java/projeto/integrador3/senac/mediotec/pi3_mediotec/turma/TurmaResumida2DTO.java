package projeto.integrador3.senac.mediotec.pi3_mediotec.turma;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
@AllArgsConstructor  // Gera o construtor necessário
public class TurmaResumida2DTO {
	private Long id; // ID da Turma
    private String nome;  // Nome da Turma
    private int anoLetivo;      // Ano da Turma
    private String anoEscolar;
    private String turno;
}


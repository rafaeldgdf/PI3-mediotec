package projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DisciplinaGetDTO {
	private long id;
    private String nome;       // Nome da Disciplina
    private int carga_horaria;
    private Long idTurma;      // ID da Turma
    private String idProfessor;  // CPF (ID) do Professor
}

package projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DisciplinaGetDTO {
    private Long id;           // ID da Disciplina
    private String nome;       // Nome da Disciplina
    private int carga_horaria;
    private String nomeTurma;  // Nome da Turma
    private String nomeProfessor;  // Nome do Professor
}

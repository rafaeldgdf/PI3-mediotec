package projeto.integrador3.senac.mediotec.pi3_mediotec.conceito;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoResumidoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorCompletoDTO;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
public class ConceitoDTO {
    private Long idConceito;
    private Float nota;
    private String conceito;
    private String unidade;

    // Usado no POST, apenas IDs são passados
    private Long alunoId;
    private Long turmaId;
    private Long disciplinaId;
    private String professorId;

    // Usado no GET, retorna dados resumidos
    private AlunoResumidoDTO aluno;
    private TurmaDisciplinaProfessorCompletoDTO turmaDisciplinaProfessor;
}

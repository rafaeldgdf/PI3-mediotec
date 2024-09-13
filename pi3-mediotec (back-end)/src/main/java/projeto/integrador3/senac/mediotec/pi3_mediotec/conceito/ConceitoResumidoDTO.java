package projeto.integrador3.senac.mediotec.pi3_mediotec.conceito;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
public class ConceitoResumidoDTO {
    private Long idConceito;
    private Float nota;
    private String conceito;
    private String unidade;
    private Long alunoId;
    private Long turmaId;
    private Long disciplinaId;
    private String professorId;
}
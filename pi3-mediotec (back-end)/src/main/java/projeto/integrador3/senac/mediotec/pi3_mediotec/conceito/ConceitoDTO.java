package projeto.integrador3.senac.mediotec.pi3_mediotec.conceito;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorDTO;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
public class ConceitoDTO {
    private Long idConceito;
    private Float nota;
    private String conceito;
    private String unidade;
    private TurmaDisciplinaProfessorDTO turmaDisciplinaProfessor;
    private AlunoDTO aluno; // Use o DTO ao invés de Aluno
    private CoordenacaoDTO coordenacao;
}

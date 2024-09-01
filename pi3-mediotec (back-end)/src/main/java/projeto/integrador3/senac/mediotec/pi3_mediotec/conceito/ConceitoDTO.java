package projeto.integrador3.senac.mediotec.pi3_mediotec.conceito;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorDTO;


@Builder
@Data
public class ConceitoDTO {
    private Long idConceito;
    private Float nota;
    private String conceito;
    private TurmaDisciplinaProfessorDTO turmaDisciplina;
    private AlunoDTO aluno;
    private CoordenacaoDTO coordenacao;
}


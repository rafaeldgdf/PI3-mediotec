package projeto.integrador3.senac.mediotec.pi3_mediotec.dtos;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class ConceitoDTO {
    private Long idConceito;
    private Float nota;
    private String conceito;
    private TurmaDisciplinaDTO turmaDisciplina;
    private AlunoDTO aluno;
    private CoordenacaoDTO coordenacao;
}


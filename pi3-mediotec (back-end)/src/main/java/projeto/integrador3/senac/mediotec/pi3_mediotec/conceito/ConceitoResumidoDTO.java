package projeto.integrador3.senac.mediotec.pi3_mediotec.conceito;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class ConceitoResumidoDTO {
    private Long idConceito;
    private Float notaUnidade1;
    private Float notaUnidade2;
    private Float notaUnidade3;
    private Float notaUnidade4;
    private Float recuperacaoNota;  // Nota da recuperação (se houver)
    private String unidade;
    private Long alunoId;
    private Long turmaId;
    private Long disciplinaId;
    private String professorId;
}

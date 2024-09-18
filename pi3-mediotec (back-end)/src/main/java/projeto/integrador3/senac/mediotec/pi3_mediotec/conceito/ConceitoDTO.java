package projeto.integrador3.senac.mediotec.pi3_mediotec.conceito;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoResumidoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorCompletoDTO;


@Builder
@Data
public class ConceitoDTO {
    private Long idConceito;
    private Float notaUnidade1;
    private Float notaUnidade2;
    private Float notaUnidade3;
    private Float notaUnidade4;
    private Float recuperacaoNota; 
    private Float mediaFinal;     
    private Boolean aprovado;      
    
    private AlunoResumidoDTO aluno;
    private TurmaDisciplinaProfessorCompletoDTO turmaDisciplinaProfessor;
}


package projeto.integrador3.senac.mediotec.pi3_mediotec.comunicado;

import java.sql.Date;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaDTO;

@Builder
@Data
public class ComunicadoDTO {
    private Long id_comunicado;
    private String conteudo;
    private Date data_envio;
    private CoordenacaoDTO coordenacao;  
    private ProfessorDTO professor;
    private AlunoDTO aluno;
    private TurmaDTO turma;
}

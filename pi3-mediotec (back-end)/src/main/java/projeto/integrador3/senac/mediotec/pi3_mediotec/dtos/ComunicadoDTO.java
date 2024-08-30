package projeto.integrador3.senac.mediotec.pi3_mediotec.dtos;

import java.sql.Date;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.Aluno;
import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.Coordenacao;
import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.Professor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.Turma;

@Builder
@Data
public class ComunicadoDTO {
	private Long id_comunicado;
	private String conteudo;
	private Date data_envio;
	private Coordenacao coordenacao;
	private Professor professor;
	private Aluno aluno;
	private Turma turma;

}

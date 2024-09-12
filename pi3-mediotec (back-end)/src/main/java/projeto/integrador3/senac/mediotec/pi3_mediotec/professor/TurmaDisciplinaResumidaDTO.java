package projeto.integrador3.senac.mediotec.pi3_mediotec.professor;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.DisciplinaResumida2DTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaResumidaDTO;

@Data
@Builder
public class TurmaDisciplinaResumidaDTO {
	private TurmaResumidaDTO turma;     // ID da turma preexistente
	private DisciplinaResumida2DTO disciplina;  // ID da disciplina preexistente
}

package projeto.integrador3.senac.mediotec.pi3_mediotec.presenca;

import java.util.Date;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoResumidoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.DisciplinaResumida2DTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorResumido3DTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaResumida2DTO;

@Builder
@Data
public class HistoricoDTO {
    private String data;
    private List<AlunoPresencaDTO> alunos;

}

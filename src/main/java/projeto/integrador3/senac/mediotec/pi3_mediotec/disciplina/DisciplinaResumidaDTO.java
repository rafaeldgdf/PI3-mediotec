package projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorResumidoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaResumidaDTO;

@Data
@Builder
public class DisciplinaResumidaDTO {
    private String nome; // Nome da disciplina
    private Integer cargaHoraria; // Carga horária da disciplina
    private TurmaResumidaDTO turma; // Informações da turma
    private Long idDisciplina; // ID da disciplina
    private Long idTurma; // ID da turma
    private String idProfessor; // ID do professor
}



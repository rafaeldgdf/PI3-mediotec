package projeto.integrador3.senac.mediotec.pi3_mediotec.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TurmaDisciplinaDTO {
    private TurmaDisciplinaIdDTO id;
    private TurmaDTO turma;
    private DisciplinaDTO disciplina;
    private ProfessorDTO professor;
}


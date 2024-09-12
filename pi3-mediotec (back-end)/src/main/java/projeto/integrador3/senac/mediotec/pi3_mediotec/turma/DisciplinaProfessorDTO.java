package projeto.integrador3.senac.mediotec.pi3_mediotec.turma;

import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DisciplinaProfessorDTO {
    private String professorId;  // ID do professor (CPF)
    private String nomeProfessor;
    private Set<Long> disciplinasIds;  // IDs das disciplinas lecionadas por esse professor
    private Set<String> nomesDisciplinas;
}

package projeto.integrador3.senac.mediotec.pi3_mediotec.turma;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TurmaInputDTO {

    private String nome;
    private int ano;
    private boolean status;
    private Long coordenacaoId;
    private Set<Long> alunosIds;
    private Set<DisciplinaProfessorInputDTO> disciplinasProfessores;
}


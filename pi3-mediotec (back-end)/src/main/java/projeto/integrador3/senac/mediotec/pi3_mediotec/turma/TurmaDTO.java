package projeto.integrador3.senac.mediotec.pi3_mediotec.turma;




import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
public class TurmaDTO {
    private Long id;
    private String nome;
    private int ano;
    private Long coordenacaoId;
    private Set<Long> alunosIds;
    private Set<DisciplinaProfessorDTO> disciplinasProfessores;
}

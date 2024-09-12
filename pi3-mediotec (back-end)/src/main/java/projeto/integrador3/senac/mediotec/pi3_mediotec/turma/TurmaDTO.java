	package projeto.integrador3.senac.mediotec.pi3_mediotec.turma;




import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoResumidoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoResumidaDTO;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
public class TurmaDTO {
    private Long id;
    private String nome;
    private int ano;
    private CoordenacaoResumidaDTO coordenacao;
    private Set<DisciplinaProfessorDTO> disciplinasProfessores;
    private Set<AlunoResumidoDTO> alunos;
}

package projeto.integrador3.senac.mediotec.pi3_mediotec.turma;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorDTO;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder 
@Data
public class TurmaDTO {
    private Long idTurma;
    private String nome;
    private int ano;
    private CoordenacaoDTO coordenacao;
    private Set<Long> alunosIds; 
    private Set<TurmaDisciplinaProfessorDTO> turmaDisciplinaProfessores;
    
}

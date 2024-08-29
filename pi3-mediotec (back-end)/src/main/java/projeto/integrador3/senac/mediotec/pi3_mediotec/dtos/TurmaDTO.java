package projeto.integrador3.senac.mediotec.pi3_mediotec.dtos;

import java.util.Set;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.Aluno;
import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.TurmaDisciplina;

@Builder 
@Data
public class TurmaDTO {
    private Long idTurma;
    private String nome;
    private int ano;
    private CoordenacaoDTO coordenacao;
    private Set<Aluno> alunos;
    private Set<TurmaDisciplina> turmaDisciplinas;
}

package projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoDTO;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder 
@Data
public class DisciplinaDTO {
    private Long idDisciplina;
    private String nome;
    private int cargaHoraria;
    private CoordenacaoDTO coordenacao;
}

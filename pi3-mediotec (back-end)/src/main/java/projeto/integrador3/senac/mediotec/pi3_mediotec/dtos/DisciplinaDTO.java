package projeto.integrador3.senac.mediotec.pi3_mediotec.dtos;

import lombok.Builder;
import lombok.Data;

@Builder 
@Data
public class DisciplinaDTO {
    private Long idDisciplina;
    private String nome;
    private int cargaHoraria;
    private CoordenacaoDTO coordenacao;
}

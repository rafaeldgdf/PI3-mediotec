package projeto.integrador3.senac.mediotec.pi3_mediotec.dtos;

import lombok.Data;

@Data
public class TurmaDTO {
    private Long idTurma;
    private String nome;
    private int ano;
    private CoordenacaoDTO coordenacao;
}

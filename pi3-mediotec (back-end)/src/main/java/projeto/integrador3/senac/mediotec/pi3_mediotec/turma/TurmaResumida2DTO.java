package projeto.integrador3.senac.mediotec.pi3_mediotec.turma;


import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class TurmaResumida2DTO {
    private String nome;  // Nome da Turma
    private int anoLetivo;      // Ano da Turma
    private String anoEscolar;
    private String turno;
}


package projeto.integrador3.senac.mediotec.pi3_mediotec.turma;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TurmaResumidaDTO {
    private Long id;     // ID da Turma
    private String nome;  // Nome da Turma
    private int ano;      // Ano da Turma
}


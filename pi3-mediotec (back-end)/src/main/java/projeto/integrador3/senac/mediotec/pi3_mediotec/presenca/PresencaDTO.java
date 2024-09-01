package projeto.integrador3.senac.mediotec.pi3_mediotec.presenca;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Builder
@Data
public class PresencaDTO {
    private Long id_presenca;
    private Date data;
    private Boolean presenca;
    private Long alunoId;
    private Long professorId;
    private Long coordenacaoId; 
}

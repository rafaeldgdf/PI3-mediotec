package projeto.integrador3.senac.mediotec.pi3_mediotec.dtos;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Builder
@Data
public class PresencaDTO {
    private Long id;
    private Date data;
    private Boolean presente;
    private Long alunoId;
    private Long professorId;
    private Long coordenacaoId; 
}

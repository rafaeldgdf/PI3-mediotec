package projeto.integrador3.senac.mediotec.pi3_mediotec.dtos;

import lombok.Builder;
import lombok.Data;

@Builder 
@Data
public class TurmaDisciplinaIdDTO {
    private Long turmaId;
    private Long disciplinaId;
}


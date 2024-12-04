package projeto.integrador3.senac.mediotec.pi3_mediotec.presenca;

import java.util.Map;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class DisciplinaFaltasDTO {
    private Long idDisciplina;
    private String nomeDisciplina;
    private Map<String, Long> faltasPorMes;
}

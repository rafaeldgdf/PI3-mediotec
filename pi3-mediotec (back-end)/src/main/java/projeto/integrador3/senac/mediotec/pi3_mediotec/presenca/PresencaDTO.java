package projeto.integrador3.senac.mediotec.pi3_mediotec.presenca;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorId;

import java.sql.Date;

@Builder
@Data
public class PresencaDTO {
    private Long id_presenca;
    private Date data;
    private Boolean presenca;
    private Long alunoId;
    private TurmaDisciplinaProfessorId turmaDisciplinaProfessorId; 
}

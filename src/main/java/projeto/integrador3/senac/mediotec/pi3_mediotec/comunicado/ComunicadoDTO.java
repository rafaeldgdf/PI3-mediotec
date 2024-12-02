package projeto.integrador3.senac.mediotec.pi3_mediotec.comunicado;

import java.util.List;

import lombok.Data;

@Data
public class ComunicadoDTO {
    private String titulo;
    private String conteudo;
    private List<Long> alunoIds;
    private List<Long> turmaIds;
}

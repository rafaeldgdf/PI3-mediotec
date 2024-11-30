package projeto.integrador3.senac.mediotec.pi3_mediotec.comunicado;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorResumido3DTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoResumidaDTO;

@Data
@Builder
public class ComunicadoDTO {
	private String titulo;
    private String conteudo;
    private LocalDateTime dataEnvio;
    private List<Long> alunoIds; // Lista de IDs de alunos
    private List<Long> turmaIds; // Lista de IDs de turmas

    // Dados do remetente
    private ProfessorResumido3DTO professorDTO;
    private CoordenacaoResumidaDTO coordenacaoDTO;
}

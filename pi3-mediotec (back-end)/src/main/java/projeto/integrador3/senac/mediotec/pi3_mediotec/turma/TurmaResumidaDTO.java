package projeto.integrador3.senac.mediotec.pi3_mediotec.turma;

import java.util.Set;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoResumidaDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.DisciplinaResumida2DTO;

@Builder
@Data
public class TurmaResumidaDTO {
    private Long id;     // ID da Turma
    private String nome;  // Nome da Turma
    private int ano;      // Ano da Turma
    private CoordenacaoResumidaDTO coordenacao;
    private Set<DisciplinaProfessorDTO> disciplinaProfessores;
}


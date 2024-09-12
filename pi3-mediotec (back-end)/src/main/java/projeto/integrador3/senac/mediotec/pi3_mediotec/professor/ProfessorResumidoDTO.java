package projeto.integrador3.senac.mediotec.pi3_mediotec.professor;

import java.util.Set;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.CoordenadorResumidoDTO;

@Builder
@Data
public class ProfessorResumidoDTO {
    private String cpf;         // CPF do Professor
    private String nome;        // Nome do Professor
    private String ultimoNome;  // Sobrenome do Professor
    private String email;
    private CoordenadorResumidoDTO coordenador;
    private Set<TurmaDisciplinaResumidaDTO> turmasDisciplinas;
}


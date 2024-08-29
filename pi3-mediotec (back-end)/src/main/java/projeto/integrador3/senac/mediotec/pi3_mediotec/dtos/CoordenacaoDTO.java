package projeto.integrador3.senac.mediotec.pi3_mediotec.dtos;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.Professor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.Turma;

import java.util.Set;

@Builder
@Data
public class CoordenacaoDTO {
    private Long idCoordenacao;
    private String nome;
    private String descricao;
    private Set<EnderecoDTO> enderecos;
    private Set<TelefoneDTO> telefones;
    private CoordenadorDTO coordenador;
    private Set<TurmaDTO> turmas;
    private Set<ProfessorDTO> professores;
}

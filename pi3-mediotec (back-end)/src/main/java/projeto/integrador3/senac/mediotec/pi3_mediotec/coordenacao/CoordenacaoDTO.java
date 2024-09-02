package projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.CoordenadorDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.EnderecoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.TelefoneDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaDTO;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
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


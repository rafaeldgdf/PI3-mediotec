package projeto.integrador3.senac.mediotec.pi3_mediotec.aluno;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.EnderecoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.presenca.Presenca;
import projeto.integrador3.senac.mediotec.pi3_mediotec.presenca.PresencaDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.TelefoneDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.Turma;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaDTO;

import java.util.Set;


@Builder
@Data
public class AlunoDTO {
    private Long idAluno;
    private String nome;
    private String ultimoNome;
    private String genero;
    private String cpf;
    private String email;
    private Set<EnderecoDTO> enderecos;
    private Set<TelefoneDTO> telefones;
    private Set<TurmaDTO> turmas;
    private Set<PresencaDTO> presencas;
}

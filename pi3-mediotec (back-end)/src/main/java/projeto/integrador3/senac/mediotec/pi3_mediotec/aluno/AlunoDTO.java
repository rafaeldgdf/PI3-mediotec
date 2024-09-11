package projeto.integrador3.senac.mediotec.pi3_mediotec.aluno;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.EnderecoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.presenca.PresencaDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.TelefoneDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaDTO;

import java.sql.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
public class AlunoDTO {
    private Long id;
    private String nome;
    private String ultimoNome;
    private String genero;
    private Date data_nascimento;
    private String cpf;
    private String email;
    private Set<EnderecoDTO> enderecos;
    private Set<TelefoneDTO> telefones;
    private Set<TurmaDTO> turmas;
    private Set<PresencaDTO> presencas;
}

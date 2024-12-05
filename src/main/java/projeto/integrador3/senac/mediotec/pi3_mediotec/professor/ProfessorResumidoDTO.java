package projeto.integrador3.senac.mediotec.pi3_mediotec.professor;


import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoResumidaDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.CoordenadorResumidoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.EnderecoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.TelefoneDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
public class ProfessorResumidoDTO {
    private String cpf;
    private String nome;
    private String ultimoNome;
    private String genero;
    private Date data_nascimento;
    private String email;
    private boolean status;
    private Long idCoordenacao; // Mantenha para compatibilidade
    private CoordenacaoResumidaDTO coordenacao; // Inclua para dados completos da coordenação
    private Set<TurmaDisciplinaResumidaDTO> turmaDisciplinaProfessores;
    private Set<EnderecoDTO> enderecos = new HashSet<>();
    private Set<TelefoneDTO> telefones = new HashSet<>();
}
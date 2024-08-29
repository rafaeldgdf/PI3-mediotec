package projeto.integrador3.senac.mediotec.pi3_mediotec.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projeto.integrador3.senac.mediotec.pi3_mediotec.dtos.CoordenacaoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.dtos.CoordenadorDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.dtos.EnderecoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.dtos.TelefoneDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.dtos.TurmaDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.dtos.ProfessorDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.Coordenacao;
import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.Coordenador;
import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.Endereco;
import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.Professor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.Telefone;
import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.Turma;
import projeto.integrador3.senac.mediotec.pi3_mediotec.repositories.CoordenacaoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CoordenacaoService {

    @Autowired
    private CoordenacaoRepository coordenacaoRepository;

    // Lista todas as coordenacoes
    public List<CoordenacaoDTO> getAllCoordenacoes() {
        return coordenacaoRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Busca coordenacao pelo id
    public Optional<CoordenacaoDTO> getCoordenacaoById(Long id) {
        return coordenacaoRepository.findById(id)
                .map(this::convertToDto);
    }

    // Cria nova coordenacao
    public CoordenacaoDTO saveCoordenacao(Coordenacao coordenacao) {
        Coordenacao savedCoordenacao = coordenacaoRepository.save(coordenacao);
        return convertToDto(savedCoordenacao);
    }

    // Edita coordenacao
    public CoordenacaoDTO updateCoordenacao(Long id, Coordenacao coordenacaoDetails) {
        Coordenacao coordenacao = coordenacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coordenacao não encontrada"));

        coordenacao.setNome(coordenacaoDetails.getNome());
        coordenacao.setDescricao(coordenacaoDetails.getDescricao());
        coordenacao.setEnderecos(coordenacaoDetails.getEnderecos());
        coordenacao.setTelefones(coordenacaoDetails.getTelefones());
        coordenacao.setTurmas(coordenacaoDetails.getTurmas());
        coordenacao.setProfessores(coordenacaoDetails.getProfessores());

        Coordenacao updatedCoordenacao = coordenacaoRepository.save(coordenacao);
        return convertToDto(updatedCoordenacao);
    }

    // Deleta coordenacao
    public void deleteCoordenacao(Long id) {
        Coordenacao coordenacao = coordenacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coordenacao não encontrada"));
        coordenacaoRepository.delete(coordenacao);
    }
    
    // Converte Coordenacao para CoordenacaoDTO
    private CoordenacaoDTO convertToDto(Coordenacao coordenacao) {
        return CoordenacaoDTO.builder()
            .idCoordenacao(coordenacao.getId_coordenacao())
            .nome(coordenacao.getNome())
            .descricao(coordenacao.getDescricao())
            .enderecos(coordenacao.getEnderecos().stream()
                .map(this::convertToEnderecoDto)
                .collect(Collectors.toSet()))
            .telefones(coordenacao.getTelefones().stream()
                .map(this::convertToTelefoneDto)
                .collect(Collectors.toSet()))
            .turmas(coordenacao.getTurmas().stream()
                    .map(this::convertToTurmaDto) 
                    .collect(Collectors.toSet()))
            .professores(coordenacao.getProfessores().stream()
                .map(this::convertToProfessorDto)
                .collect(Collectors.toSet()))
            .coordenador(convertToCoordenadorDto(coordenacao.getCoordenador()))
            .build();
    }

    private EnderecoDTO convertToEnderecoDto(Endereco endereco) {
        return EnderecoDTO.builder()
            .cep(endereco.getCep())
            .rua(endereco.getRua())
            .numero(endereco.getNumero())
            .bairro(endereco.getBairro())
            .cidade(endereco.getCidade())
            .estado(endereco.getEstado())
            .build();
    }

    private TelefoneDTO convertToTelefoneDto(Telefone telefone) {
        return TelefoneDTO.builder()
            .ddd(telefone.getDdd())
            .numero(telefone.getNumero())
            .build();
    }

    private TurmaDTO convertToTurmaDto(Turma turma) {
        return TurmaDTO.builder()
                .idTurma(turma.getId_turma())
                .nome(turma.getNome())
                .ano(turma.getAno())
                .build();
    }

    private ProfessorDTO convertToProfessorDto(Professor professor) {
        return ProfessorDTO.builder()
            .cpf(professor.getCpf())
            .nome(professor.getNome())
            .email(professor.getEmail())
            .build();
    }

    private CoordenadorDTO convertToCoordenadorDto(Coordenador coordenador) {
        return CoordenadorDTO.builder()
            .cpf(coordenador.getCpf())
            .nome(coordenador.getNome())
            .email(coordenador.getEmail())
            .build();
    }

}

package projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.CoordenadorDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.EnderecoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.TelefoneDTO;

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
        // Configura a associação da coordenacao com seus endereços e telefones
        if (coordenacao.getEnderecos() != null) {
            coordenacao.getEnderecos().forEach(endereco -> endereco.setCoordenacao(coordenacao));
        }

        if (coordenacao.getTelefones() != null) {
            coordenacao.getTelefones().forEach(telefone -> telefone.setCoordenacao(coordenacao));
        }
        // Verifica se a coleção turmas e professores não são nulas antes de iterar sobre elas
        if (coordenacao.getTurmas() != null) {
            coordenacao.getTurmas().forEach(turma -> turma.setCoordenacao(coordenacao));
        }
        if (coordenacao.getProfessores() != null) {
            coordenacao.getProfessores().forEach(professor -> professor.setCoordenacao(coordenacao));
        }

        // Salva a coordenacao junto com seus endereços, telefones, turmas e professores (se existirem)
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

        // Configura novamente a associação da coordenacao com seus endereços, telefones, turmas e professores
        coordenacao.getEnderecos().forEach(endereco -> endereco.setCoordenacao(coordenacao));
        coordenacao.getTelefones().forEach(telefone -> telefone.setCoordenacao(coordenacao));
        coordenacao.getTurmas().forEach(turma -> turma.setCoordenacao(coordenacao));
        coordenacao.getProfessores().forEach(professor -> professor.setCoordenacao(coordenacao));

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
        CoordenadorDTO coordenadorDTO = CoordenadorDTO.builder()
            .cpf(coordenacao.getCoordenador().getCpf())
            .nome(coordenacao.getCoordenador().getNome())
            .ultimoNome(coordenacao.getCoordenador().getUltimoNome())
            .build();

        return CoordenacaoDTO.builder()
            .id(coordenacao.getId())
            .nome(coordenacao.getNome())
            .descricao(coordenacao.getDescricao())
            .enderecos(coordenacao.getEnderecos().stream()
                .map(endereco -> EnderecoDTO.builder()
                    .cep(endereco.getCep())
                    .rua(endereco.getRua())
                    .numero(endereco.getNumero())
                    .bairro(endereco.getBairro())
                    .cidade(endereco.getCidade())
                    .estado(endereco.getEstado())
                    .build())
                .collect(Collectors.toSet()))
            .telefones(coordenacao.getTelefones().stream()
                .map(telefone -> TelefoneDTO.builder()
                    .ddd(telefone.getDdd())
                    .numero(telefone.getNumero())
                    .build())
                .collect(Collectors.toSet()))
            .coordenador(coordenadorDTO)  // Passa o DTO do coordenador
            .build();
    }
}

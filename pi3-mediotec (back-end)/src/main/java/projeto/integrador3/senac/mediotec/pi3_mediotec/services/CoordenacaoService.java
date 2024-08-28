package projeto.integrador3.senac.mediotec.pi3_mediotec.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projeto.integrador3.senac.mediotec.pi3_mediotec.dtos.CoordenacaoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.dtos.EnderecoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.dtos.TelefoneDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.Coordenacao;
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
                    .<EnderecoDTO>map(endereco -> EnderecoDTO.builder()
                            .cep(endereco.getCep())
                            .rua(endereco.getRua())
                            .numero(endereco.getNumero())
                            .bairro(endereco.getBairro())
                            .cidade(endereco.getCidade())
                            .estado(endereco.getEstado())
                            .build())
                    .collect(Collectors.toSet()))
                .telefones(coordenacao.getTelefones().stream()
                    .<TelefoneDTO>map(telefone -> TelefoneDTO.builder()
                            .ddd(telefone.getDdd())
                            .numero(telefone.getNumero())
                            .build())
                    .collect(Collectors.toSet()))
                .build();
    }

}

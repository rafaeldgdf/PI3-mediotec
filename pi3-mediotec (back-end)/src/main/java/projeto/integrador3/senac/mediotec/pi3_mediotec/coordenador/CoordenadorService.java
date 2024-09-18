package projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.Endereco;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.EnderecoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.Telefone;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.TelefoneDTO;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CoordenadorService {

    @Autowired
    private CoordenadorRepository coordenadorRepository;

    // Lista todos os coordenadores e retorna uma lista de CoordenadorDTO
    public List<CoordenadorDTO> getAllCoordenadores() {
        return coordenadorRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Busca coordenador por ID (CPF) e retorna CoordenadorDTO
    public Optional<CoordenadorDTO> getCoordenadorById(String id) {
        return coordenadorRepository.findById(id)
                .map(this::convertToDto);
    }

    public CoordenadorDTO saveCoordenador(CoordenadorDTO coordenadorDTO) {
        // Verifica se o CPF já existe
        if (coordenadorRepository.existsByCpf(coordenadorDTO.getCpf())) {
            throw new RuntimeException("Coordenador com CPF " + coordenadorDTO.getCpf() + " já existe");
        }

        // Converte DTO diretamente para entidade dentro do método
        Coordenador coordenador = new Coordenador();
        coordenador.setCpf(coordenadorDTO.getCpf());
        coordenador.setNome(coordenadorDTO.getNome());
        coordenador.setUltimoNome(coordenadorDTO.getUltimoNome());
        coordenador.setGenero(coordenadorDTO.getGenero());
        coordenador.setData_nascimento(coordenadorDTO.getData_nascimento());
        coordenador.setEmail(coordenadorDTO.getEmail());
        coordenador.setStatus(true); // Define o status inicial como ativo

        // Adiciona e associa endereços ao coordenador
        if (coordenadorDTO.getEnderecos() != null && !coordenadorDTO.getEnderecos().isEmpty()) {
            // Limpa a coleção existente antes de adicionar novos itens
            coordenador.getEnderecos().clear();
            coordenadorDTO.getEnderecos().forEach(enderecoDTO -> {
                Endereco endereco = Endereco.builder()
                    .cep(enderecoDTO.getCep())
                    .rua(enderecoDTO.getRua())
                    .numero(enderecoDTO.getNumero())
                    .bairro(enderecoDTO.getBairro())
                    .cidade(enderecoDTO.getCidade())
                    .estado(enderecoDTO.getEstado())
                    .coordenador(coordenador) // Associação bidirecional
                    .build();
                coordenador.getEnderecos().add(endereco);
            });
        }

        // Adiciona e associa telefones ao coordenador
        if (coordenadorDTO.getTelefones() != null && !coordenadorDTO.getTelefones().isEmpty()) {
            // Limpa a coleção existente antes de adicionar novos itens
            coordenador.getTelefones().clear();
            coordenadorDTO.getTelefones().forEach(telefoneDTO -> {
                Telefone telefone = Telefone.builder()
                    .ddd(telefoneDTO.getDdd())
                    .numero(telefoneDTO.getNumero())
                    .coordenador(coordenador) // Associação bidirecional
                    .build();
                coordenador.getTelefones().add(telefone);
            });
        }

        // Salva a entidade e retorna o DTO correspondente
        Coordenador savedCoordenador = coordenadorRepository.save(coordenador);
        return convertToDto(savedCoordenador);
    }



    public CoordenadorDTO updateCoordenador(String id, CoordenadorDTO coordenadorDTO) {
        // Busca o coordenador existente no banco
        Coordenador coordenador = coordenadorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Coordenador não encontrado"));

        // Atualiza os dados do coordenador diretamente a partir do DTO
        coordenador.setNome(coordenadorDTO.getNome());
        coordenador.setUltimoNome(coordenadorDTO.getUltimoNome());
        coordenador.setGenero(coordenadorDTO.getGenero());
        coordenador.setData_nascimento(coordenadorDTO.getData_nascimento());
        coordenador.setEmail(coordenadorDTO.getEmail());
        coordenador.setStatus(coordenadorDTO.isStatus());

        // Atualiza os endereços do coordenador
        if (coordenadorDTO.getEnderecos() != null && !coordenadorDTO.getEnderecos().isEmpty()) {
            // Limpa a coleção existente antes de adicionar novos itens
            coordenador.getEnderecos().clear();
            coordenadorDTO.getEnderecos().forEach(enderecoDTO -> {
                Endereco endereco = Endereco.builder()
                    .cep(enderecoDTO.getCep())
                    .rua(enderecoDTO.getRua())
                    .numero(enderecoDTO.getNumero())
                    .bairro(enderecoDTO.getBairro())
                    .cidade(enderecoDTO.getCidade())
                    .estado(enderecoDTO.getEstado())
                    .coordenador(coordenador) // Associação bidirecional
                    .build();
                coordenador.getEnderecos().add(endereco);
            });
        }

        // Atualiza os telefones do coordenador
        if (coordenadorDTO.getTelefones() != null && !coordenadorDTO.getTelefones().isEmpty()) {
            // Limpa a coleção existente antes de adicionar novos itens
            coordenador.getTelefones().clear();
            coordenadorDTO.getTelefones().forEach(telefoneDTO -> {
                Telefone telefone = Telefone.builder()
                    .ddd(telefoneDTO.getDdd())
                    .numero(telefoneDTO.getNumero())
                    .coordenador(coordenador) // Associação bidirecional
                    .build();
                coordenador.getTelefones().add(telefone);
            });
        }

        // Salva a entidade e retorna o DTO atualizado
        Coordenador updatedCoordenador = coordenadorRepository.save(coordenador);
        return convertToDto(updatedCoordenador);
    }



    // Deleta um coordenador por ID (CPF)
    public void deleteCoordenador(String id) {
        Coordenador coordenador = coordenadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coordenador não encontrado"));
        coordenadorRepository.delete(coordenador);
    }

    // Converte Coordenador para CoordenadorDTO
    private CoordenadorDTO convertToDto(Coordenador coordenador) {
        return CoordenadorDTO.builder()
                .cpf(coordenador.getCpf())
                .nome(coordenador.getNome())
                .ultimoNome(coordenador.getUltimoNome())
                .genero(coordenador.getGenero())
                .data_nascimento(coordenador.getData_nascimento())
                .email(coordenador.getEmail())
                .status(coordenador.isStatus())
                .enderecos(coordenador.getEnderecos().stream()
                        .map(this::convertEnderecoToDto)
                        .collect(Collectors.toSet()))
                .telefones(coordenador.getTelefones().stream()
                        .map(this::convertTelefoneToDto)
                        .collect(Collectors.toSet()))
                .build();
    }

    // Converte Endereco (entidade) para EnderecoDTO
    private EnderecoDTO convertEnderecoToDto(Endereco endereco) {
        return EnderecoDTO.builder()
                .cep(endereco.getCep())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .bairro(endereco.getBairro())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .build();
    }

    // Converte Telefone (entidade) para TelefoneDTO
    private TelefoneDTO convertTelefoneToDto(Telefone telefone) {
        return TelefoneDTO.builder()
                .ddd(telefone.getDdd())
                .numero(telefone.getNumero())
                .build();
    }
}

package projeto.integrador3.senac.mediotec.pi3_mediotec.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projeto.integrador3.senac.mediotec.pi3_mediotec.dtos.CoordenacaoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.dtos.CoordenadorDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.dtos.EnderecoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.dtos.TelefoneDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.Coordenador;
import projeto.integrador3.senac.mediotec.pi3_mediotec.repositories.CoordenadorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CoordenadorService {

    @Autowired
    private CoordenadorRepository coordenadorRepository;

//CRUD 

    // Lista todos
    public List<CoordenadorDTO> getAllCoordenadores() {
        return coordenadorRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Busca pelo id
    public Optional<CoordenadorDTO> getCoordenadorById(String id) {
        return coordenadorRepository.findById(id)
                .map(this::convertToDto);
    }

    // Cria
    public CoordenadorDTO saveCoordenador(Coordenador coordenador) {
        
        if (coordenadorRepository.existsByCpf(coordenador.getCpf())) {
            throw new RuntimeException("Coordenador com CPF " + coordenador.getCpf() + " já existe");
        }

        // Configura a relação bidirecional entre coordenador e endereços/telefones
        coordenador.getEnderecos().forEach(endereco -> endereco.setCoordenador(coordenador));
        coordenador.getTelefones().forEach(telefone -> telefone.setCoordenador(coordenador));

        Coordenador savedCoordenador = coordenadorRepository.save(coordenador);
        return convertToDto(savedCoordenador);
    }

    // Edita
    public CoordenadorDTO updateCoordenador(String id, Coordenador coordenadorDetails) {
        Coordenador coordenador = coordenadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coordenador não encontrado"));

        if (coordenadorDetails.getCpf() != null && !coordenadorDetails.getCpf().isEmpty()) {
            if (!coordenador.getCpf().equals(coordenadorDetails.getCpf()) &&
                    coordenadorRepository.existsById(coordenadorDetails.getCpf())) {
                throw new RuntimeException("Coordenador com CPF " + coordenadorDetails.getCpf() + " já existe");
            }
            coordenador.setCpf(coordenadorDetails.getCpf());
        }

        coordenador.setNome(coordenadorDetails.getNome());
        coordenador.setUltimoNome(coordenadorDetails.getUltimoNome());
        coordenador.setGenero(coordenadorDetails.getGenero());
        coordenador.setData_nascimento(coordenadorDetails.getData_nascimento());
        coordenador.setEmail(coordenadorDetails.getEmail());
        coordenador.setEnderecos(coordenadorDetails.getEnderecos());
        coordenador.setTelefones(coordenadorDetails.getTelefones());
   

        Coordenador updatedCoordenador = coordenadorRepository.save(coordenador);
        return convertToDto(updatedCoordenador);
    }

    // Deleta
    public void deleteCoordenador(String id) {
        Coordenador coordenador = coordenadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coordenador not found"));
        coordenadorRepository.delete(coordenador);
    }
    
//Métodos específicos e tratamentos de erros da classe 
    
    // Converte Coordenador para CoordenadorDTO
    private CoordenadorDTO convertToDto(Coordenador coordenador) {
        return CoordenadorDTO.builder()
                .cpf(coordenador.getCpf())
                .nome(coordenador.getNome())
                .ultimoNome(coordenador.getUltimoNome())
                .genero(coordenador.getGenero())
                .data_nascimento(coordenador.getData_nascimento())
                .email(coordenador.getEmail())
                .enderecos(coordenador.getEnderecos().stream()
                        .<EnderecoDTO>map(endereco -> EnderecoDTO.builder()
                                .cep(endereco.getCep())
                                .rua(endereco.getRua())
                                .numero(endereco.getNumero())
                                .bairro(endereco.getBairro())
                                .cidade(endereco.getCidade())
                                .estado(endereco.getEstado())
                                .build())
                        .collect(Collectors.toSet()))
                .telefones(coordenador.getTelefones().stream()
                        .<TelefoneDTO>map(telefone -> TelefoneDTO.builder()
                                .ddd(telefone.getDdd())
                                .numero(telefone.getNumero())
                                .build())
                        .collect(Collectors.toSet()))
                .build(); 
    }
}
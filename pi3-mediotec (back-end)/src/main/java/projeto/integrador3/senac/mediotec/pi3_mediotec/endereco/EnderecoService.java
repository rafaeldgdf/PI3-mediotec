package projeto.integrador3.senac.mediotec.pi3_mediotec.endereco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.CoordenadorRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private CoordenacaoRepository coordenacaoRepository;

    @Autowired
    private CoordenadorRepository coordenadorRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Transactional
    public EnderecoDTO salvarEndereco(EnderecoDTO enderecoDTO) {
        Endereco endereco = convertToEntity(enderecoDTO);
        Endereco savedEndereco = enderecoRepository.save(endereco);
        return convertToDTO(savedEndereco);
    }

    @Transactional
    public EnderecoDTO atualizarEndereco(Long id, EnderecoDTO enderecoDTO) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));

        endereco.setCep(enderecoDTO.getCep());
        endereco.setRua(enderecoDTO.getRua());
        endereco.setNumero(enderecoDTO.getNumero());
        endereco.setBairro(enderecoDTO.getBairro());
        endereco.setCidade(enderecoDTO.getCidade());
        endereco.setEstado(enderecoDTO.getEstado());

        Endereco updatedEndereco = enderecoRepository.save(endereco);
        return convertToDTO(updatedEndereco);
    }

    public List<EnderecoDTO> listarEnderecos() {
        return enderecoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<EnderecoDTO> buscarEnderecoPorId(Long id) {
        return enderecoRepository.findById(id)
                .map(this::convertToDTO);
    }

    public void deletarEndereco(Long id) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));
        enderecoRepository.delete(endereco);
    }

    private EnderecoDTO convertToDTO(Endereco endereco) {
        return EnderecoDTO.builder()
                .idEndereco(endereco.getId_endereco())
                .cep(endereco.getCep())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .bairro(endereco.getBairro())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .build();
    }

    private Endereco convertToEntity(EnderecoDTO enderecoDTO) {
        return Endereco.builder()
                .id_endereco(enderecoDTO.getIdEndereco())
                .cep(enderecoDTO.getCep())
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .bairro(enderecoDTO.getBairro())
                .cidade(enderecoDTO.getCidade())
                .estado(enderecoDTO.getEstado())
                .build();
    }
}

package projeto.integrador3.senac.mediotec.pi3_mediotec.telefone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.Aluno;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.Coordenacao;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.Coordenador;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.CoordenadorRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.Professor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TelefoneService {

    @Autowired
    private TelefoneRepository telefoneRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private CoordenadorRepository coordenadorRepository;

    @Autowired
    private CoordenacaoRepository coordenacaoRepository;

    @Transactional
    public TelefoneDTO salvarTelefone(TelefoneDTO telefoneDTO) {
        Telefone telefone = convertToEntity(telefoneDTO);
        Telefone savedTelefone = telefoneRepository.save(telefone);
        return convertToDTO(savedTelefone);
    }

    @Transactional
    public TelefoneDTO atualizarTelefone(Long id, TelefoneDTO telefoneDTO) {
        Telefone telefone = telefoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Telefone não encontrado"));

        telefone.setDdd(telefoneDTO.getDdd());
        telefone.setNumero(telefoneDTO.getNumero());

        Telefone updatedTelefone = telefoneRepository.save(telefone);
        return convertToDTO(updatedTelefone);
    }

    public List<TelefoneDTO> listarTelefones() {
        return telefoneRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<TelefoneDTO> buscarTelefonePorId(Long id) {
        return telefoneRepository.findById(id)
                .map(this::convertToDTO);
    }

    public void deletarTelefone(Long id) {
        Telefone telefone = telefoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Telefone não encontrado"));
        telefoneRepository.delete(telefone);
    }

    private TelefoneDTO convertToDTO(Telefone telefone) {
        return TelefoneDTO.builder()
                .id(telefone.getId())
                .ddd(telefone.getDdd())
                .numero(telefone.getNumero())
                .build();
    }

    private Telefone convertToEntity(TelefoneDTO telefoneDTO) {
        return Telefone.builder()
                .id(telefoneDTO.getId())
                .ddd(telefoneDTO.getDdd())
                .numero(telefoneDTO.getNumero())
                .build();
    }

}

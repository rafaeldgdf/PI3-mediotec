package projeto.integrador3.senac.mediotec.pi3_mediotec.presenca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.Aluno;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.Coordenacao;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorId;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PresencaService {

    @Autowired
    private PresencaRepository presencaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private CoordenacaoRepository coordenacaoRepository;

    @Autowired
    private TurmaDisciplinaProfessorRepository turmaDisciplinaProfessorRepository;

    @Transactional
    public PresencaDTO salvarPresenca(PresencaDTO presencaDTO) {
        Presenca presenca = convertToEntity(presencaDTO);
        Presenca savedPresenca = presencaRepository.save(presenca);
        return convertToDTO(savedPresenca);
    }

    @Transactional
    public PresencaDTO atualizarPresenca(Long id, PresencaDTO presencaDTO) {
        Presenca presenca = presencaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Presença não encontrada"));

        presenca.setData(presencaDTO.getData());
        presenca.setPresenca(presencaDTO.getPresenca());
        presenca.setAluno(buscarAlunoPorId(presencaDTO.getAlunoId()));
        presenca.setTurmaDisciplinaProfessor(buscarTurmaDisciplinaProfessorPorId(presencaDTO.getTurmaDisciplinaProfessorId()));
        presenca.setCoordenacao(buscarCoordenacaoPorId(presencaDTO.getCoordenacaoId()));

        Presenca updatedPresenca = presencaRepository.save(presenca);
        return convertToDTO(updatedPresenca);
    }

    public List<PresencaDTO> listarPresencas() {
        return presencaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<PresencaDTO> buscarPresencaPorId(Long id) {
        return presencaRepository.findById(id)
                .map(this::convertToDTO);
    }

    public void deletarPresenca(Long id) {
        Presenca presenca = presencaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Presença não encontrada"));
        presencaRepository.delete(presenca);
    }

    private PresencaDTO convertToDTO(Presenca presenca) {
        return PresencaDTO.builder()
                .id_presenca(presenca.getId_presenca())
                .data(new java.sql.Date(presenca.getData().getTime())) // Conversão de java.util.Date para java.sql.Date
                .presenca(presenca.getPresenca())
                .alunoId(presenca.getAluno() != null ? presenca.getAluno().getId() : null)
                .turmaDisciplinaProfessorId(presenca.getTurmaDisciplinaProfessor() != null ? presenca.getTurmaDisciplinaProfessor().getId() : null) // Passando o ID do TurmaDisciplinaProfessor
                .coordenacaoId(presenca.getCoordenacao() != null ? presenca.getCoordenacao().getId_coordenacao() : null)
                .build();
    }

    private Presenca convertToEntity(PresencaDTO presencaDTO) {
        return Presenca.builder()
                .id_presenca(presencaDTO.getId_presenca())
                .data(presencaDTO.getData())
                .presenca(presencaDTO.getPresenca())
                .aluno(buscarAlunoPorId(presencaDTO.getAlunoId()))
                .turmaDisciplinaProfessor(buscarTurmaDisciplinaProfessorPorId(presencaDTO.getTurmaDisciplinaProfessorId()))
                .coordenacao(buscarCoordenacaoPorId(presencaDTO.getCoordenacaoId()))
                .build();
    }

    private Aluno buscarAlunoPorId(Long idAluno) {
        return alunoRepository.findById(idAluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
    }

    private Coordenacao buscarCoordenacaoPorId(Long idCoordenacao) {
        return coordenacaoRepository.findById(idCoordenacao)
                .orElseThrow(() -> new RuntimeException("Coordenação não encontrada"));
    }

    private TurmaDisciplinaProfessor buscarTurmaDisciplinaProfessorPorId(TurmaDisciplinaProfessorId turmaDisciplinaProfessorId) {
        return turmaDisciplinaProfessorRepository.findById(turmaDisciplinaProfessorId)
                .orElseThrow(() -> new RuntimeException("TurmaDisciplinaProfessor não encontrado"));
    }

}

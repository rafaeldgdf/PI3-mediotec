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
    private TurmaDisciplinaProfessorRepository turmaDisciplinaProfessorRepository;

    @Transactional
    public PresencaDTO salvarPresenca(Long idAluno, PresencaDTO presencaDTO) {
        Aluno aluno = buscarAlunoPorId(idAluno);
        Presenca presenca = convertToEntity(presencaDTO);
        presenca.setAluno(aluno);
        Presenca savedPresenca = presencaRepository.save(presenca);
        return convertToDTO(savedPresenca);
    }

    @Transactional
    public PresencaDTO atualizarPresenca(Long idAluno, Long id, PresencaDTO presencaDTO) {
        Presenca presenca = presencaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Presença não encontrada"));

        presenca.setData(presencaDTO.getData());
        presenca.setPresenca(presencaDTO.getPresenca());
        presenca.setAluno(buscarAlunoPorId(idAluno));
        presenca.setTurmaDisciplinaProfessor(buscarTurmaDisciplinaProfessorPorId(presencaDTO.getTurmaDisciplinaProfessorId()));

        Presenca updatedPresenca = presencaRepository.save(presenca);
        return convertToDTO(updatedPresenca);
    }

    public List<PresencaDTO> listarPresencasPorAluno(Long idAluno) {
        Aluno aluno = buscarAlunoPorId(idAluno);
        return presencaRepository.findByAluno(aluno).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<PresencaDTO> buscarPresencaPorId(Long idAluno, Long id) {
        buscarAlunoPorId(idAluno); // Valida se o aluno existe
        return presencaRepository.findById(id)
                .map(this::convertToDTO);
    }

    public void deletarPresenca(Long idAluno, Long id) {
        buscarAlunoPorId(idAluno); // Valida se o aluno existe
        Presenca presenca = presencaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Presença não encontrada"));
        presencaRepository.delete(presenca);
    }

    private PresencaDTO convertToDTO(Presenca presenca) {
        return PresencaDTO.builder()
                .id_presenca(presenca.getId_presenca())
                .data(new java.sql.Date(presenca.getData().getTime()))
                .presenca(presenca.getPresenca())
                .alunoId(presenca.getAluno() != null ? presenca.getAluno().getId() : null)
                .turmaDisciplinaProfessorId(presenca.getTurmaDisciplinaProfessor() != null ? presenca.getTurmaDisciplinaProfessor().getId() : null)
                .build();
    }

    private Presenca convertToEntity(PresencaDTO presencaDTO) {
        return Presenca.builder()
                .id_presenca(presencaDTO.getId_presenca())
                .data(presencaDTO.getData())
                .presenca(presencaDTO.getPresenca())
                .turmaDisciplinaProfessor(buscarTurmaDisciplinaProfessorPorId(presencaDTO.getTurmaDisciplinaProfessorId()))
                .build();
    }

    private Aluno buscarAlunoPorId(Long idAluno) {
        return alunoRepository.findById(idAluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
    }


    private TurmaDisciplinaProfessor buscarTurmaDisciplinaProfessorPorId(TurmaDisciplinaProfessorId turmaDisciplinaProfessorId) {
        return turmaDisciplinaProfessorRepository.findById(turmaDisciplinaProfessorId)
                .orElseThrow(() -> new RuntimeException("TurmaDisciplinaProfessor não encontrado"));
    }

}

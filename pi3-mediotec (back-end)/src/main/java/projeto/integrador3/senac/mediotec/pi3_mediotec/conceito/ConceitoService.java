package projeto.integrador3.senac.mediotec.pi3_mediotec.conceito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.Aluno;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.Coordenacao;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.DisciplinaDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorId;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConceitoService {

    @Autowired
    private ConceitoRepository conceitoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private TurmaDisciplinaProfessorRepository turmaDisciplinaProfessorRepository;

    @Transactional
    public ConceitoDTO salvarConceito(ConceitoDTO conceitoDTO) {
        Conceito conceito = convertToEntity(conceitoDTO);
        Conceito savedConceito = conceitoRepository.save(conceito);
        return convertToDTO(savedConceito);
    }

    @Transactional
    public ConceitoDTO atualizarConceito(Long id, ConceitoDTO conceitoDTO) {
        Conceito conceito = conceitoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conceito não encontrado"));

        conceito.setNota(conceitoDTO.getNota());
        conceito.setConceito(conceitoDTO.getConceito());
        conceito.setAluno(buscarAlunoPorId(conceitoDTO.getAluno().getId()));
        
        // Corrigindo para usar os métodos corretos de TurmaDisciplinaProfessorDTO
        conceito.setTurmaDisciplinaProfessor(buscarTurmaDisciplinaProfessorPorId(
                new TurmaDisciplinaProfessorId(
                        conceitoDTO.getTurmaDisciplinaProfessor().getTurmaId(),   
                        conceitoDTO.getTurmaDisciplinaProfessor().getDisciplinaId(),  
                        conceitoDTO.getTurmaDisciplinaProfessor().getProfessorId()   
                )));

        Conceito updatedConceito = conceitoRepository.save(conceito);
        return convertToDTO(updatedConceito);
    }


    public List<ConceitoDTO> listarConceitos() {
        return conceitoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ConceitoDTO> buscarConceitoPorId(Long id) {
        return conceitoRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Transactional
    public void deletarConceito(Long id) {
        Conceito conceito = conceitoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conceito não encontrado"));
        conceitoRepository.delete(conceito);
    }

    public List<ConceitoDTO> buscarConceitosPorAluno(Long id) {
        List<Conceito> conceitos = conceitoRepository.findByAluno_Id(id);
        return conceitos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }



    private ConceitoDTO convertToDTO(Conceito conceito) {
        return ConceitoDTO.builder()
                .idConceito(conceito.getId_conceito())
                .nota(conceito.getNota())
                .conceito(conceito.getConceito())
                .unidade(conceito.getUnidade())
                .aluno(AlunoDTO.builder()
                        .id(conceito.getAluno().getId())
                        .nome(conceito.getAluno().getNome())
                        .build())
                .turmaDisciplinaProfessor(TurmaDisciplinaProfessorDTO.builder()
                        .turmaId(conceito.getTurmaDisciplinaProfessor().getId().getTurmaId())
                        .disciplinaId(conceito.getTurmaDisciplinaProfessor().getId().getDisciplinaId())
                        .professorId(conceito.getTurmaDisciplinaProfessor().getId().getProfessorId())
                        .build())
                .build();
    }



    private Conceito convertToEntity(ConceitoDTO conceitoDTO) {
        return Conceito.builder()
                .id_conceito(conceitoDTO.getIdConceito())
                .nota(conceitoDTO.getNota())
                .conceito(conceitoDTO.getConceito())
                .aluno(buscarAlunoPorId(conceitoDTO.getAluno().getId()))
                .turmaDisciplinaProfessor(buscarTurmaDisciplinaProfessorPorId(
                        new TurmaDisciplinaProfessorId(
                                conceitoDTO.getTurmaDisciplinaProfessor().getTurmaId(),
                                conceitoDTO.getTurmaDisciplinaProfessor().getDisciplinaId(),
                                conceitoDTO.getTurmaDisciplinaProfessor().getProfessorId()
                        )))
                .build();
    }


    private Aluno buscarAlunoPorId(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno com ID " + id + " não encontrado"));
    }

    private TurmaDisciplinaProfessor buscarTurmaDisciplinaProfessorPorId(TurmaDisciplinaProfessorId id) {
        return turmaDisciplinaProfessorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TurmaDisciplinaProfessor com ID " + id + " não encontrado"));
    }



}

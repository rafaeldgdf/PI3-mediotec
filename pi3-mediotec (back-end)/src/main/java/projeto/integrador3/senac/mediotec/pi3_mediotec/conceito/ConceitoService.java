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
    private CoordenacaoRepository coordenacaoRepository;

    @Autowired
    private TurmaDisciplinaProfessorRepository turmaDisciplinaProfessorRepository;

    @Transactional
    public ConceitoDTO salvarConceito(ConceitoDTO conceitoDTO) {
        Conceito conceito = convertToEntity(conceitoDTO);
        conceito.setConceito(calcularConceito(conceito.getNota())); // Atribui o conceito automaticamente
        Conceito savedConceito = conceitoRepository.save(conceito);
        return convertToDTO(savedConceito);
    }

    @Transactional
    public ConceitoDTO atualizarConceito(Long id, ConceitoDTO conceitoDTO) {
        Conceito conceito = conceitoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conceito não encontrado"));

        conceito.setNota(conceitoDTO.getNota());
        conceito.setConceito(calcularConceito(conceitoDTO.getNota())); // Atribui o conceito automaticamente
        conceito.setAluno(buscarAlunoPorId(conceitoDTO.getAluno().getIdAluno()));
        conceito.setCoordenacao(buscarCoordenacaoPorId(conceitoDTO.getCoordenacao().getIdCoordenacao()));
        
        TurmaDisciplinaProfessorId turmaDisciplinaProfessorId = new TurmaDisciplinaProfessorId(
            conceitoDTO.getTurmaDisciplina().getTurma().getIdTurma(),
            conceitoDTO.getTurmaDisciplina().getDisciplina().getIdDisciplina(),
            conceitoDTO.getTurmaDisciplina().getProfessor().getCpf() 
        );
        
        conceito.setTurmaDisciplinaProfessor(buscarTurmaDisciplinaProfessorPorId(turmaDisciplinaProfessorId));

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

    public void deletarConceito(Long id) {
        Conceito conceito = conceitoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conceito não encontrado"));
        conceitoRepository.delete(conceito);
    }

    private ConceitoDTO convertToDTO(Conceito conceito) {
        return ConceitoDTO.builder()
                .idConceito(conceito.getId_conceito())
                .nota(conceito.getNota())
                .conceito(conceito.getConceito())
                .aluno(AlunoDTO.builder()
                        .idAluno(conceito.getAluno().getId_aluno())
                        .nome(conceito.getAluno().getNome())
                        .build())
                .coordenacao(CoordenacaoDTO.builder()
                        .idCoordenacao(conceito.getCoordenacao().getId_coordenacao())
                        .nome(conceito.getCoordenacao().getNome())
                        .build())
                .turmaDisciplina(TurmaDisciplinaProfessorDTO.builder()
                	    .id(new TurmaDisciplinaProfessorId(
                	        conceito.getTurmaDisciplinaProfessor().getTurma().getId_turma(),
                	        conceito.getTurmaDisciplinaProfessor().getDisciplina().getId_disciplina(),
                	        conceito.getTurmaDisciplinaProfessor().getProfessor().getCpf()
                	    ))
                	    .build())

                .build();
    }


    private Conceito convertToEntity(ConceitoDTO conceitoDTO) {
        // Cria uma instância de TurmaDisciplinaProfessorId a partir do DTO
        TurmaDisciplinaProfessorId turmaDisciplinaProfessorId = new TurmaDisciplinaProfessorId(
            conceitoDTO.getTurmaDisciplina().getTurma().getIdTurma(), 
            conceitoDTO.getTurmaDisciplina().getDisciplina().getIdDisciplina(), 
            conceitoDTO.getTurmaDisciplina().getProfessor().getCpf());

        return Conceito.builder()
            .id_conceito(conceitoDTO.getIdConceito())
            .nota(conceitoDTO.getNota())
            .conceito(calcularConceito(conceitoDTO.getNota())) // Atribui o conceito automaticamente
            .aluno(buscarAlunoPorId(conceitoDTO.getAluno().getIdAluno()))
            .coordenacao(buscarCoordenacaoPorId(conceitoDTO.getCoordenacao().getIdCoordenacao()))
            .turmaDisciplinaProfessor(buscarTurmaDisciplinaProfessorPorId(turmaDisciplinaProfessorId))
            .build();
    }

    // Método para determinar o conceito com base na nota
    private String calcularConceito(Float nota) {
        if (nota >= 9.5 && nota <= 10.0) {
            return "Excelente";
        } else if (nota >= 9.0 && nota < 9.5) {
            return "Ótimo";
        } else if (nota >= 7.0 && nota < 9.0) {
            return "Bom";
        } else if (nota >= 6.0 && nota < 7.0) {
            return "Suficiente";
        } else if (nota >= 0.0 && nota < 6.0) {
            return "Insuficiente";
        } else {
            throw new IllegalArgumentException("Nota fora do intervalo permitido: " + nota);
        }
    }

    private Aluno buscarAlunoPorId(Long idAluno) {
        return alunoRepository.findById(idAluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
    }

    private Coordenacao buscarCoordenacaoPorId(Long idCoordenacao) {
        return coordenacaoRepository.findById(idCoordenacao)
                .orElseThrow(() -> new RuntimeException("Coordenação não encontrada"));
    }

    private TurmaDisciplinaProfessor buscarTurmaDisciplinaProfessorPorId(TurmaDisciplinaProfessorId id) {
        return turmaDisciplinaProfessorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TurmaDisciplinaProfessor não encontrado"));
    }
}

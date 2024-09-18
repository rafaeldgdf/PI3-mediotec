package projeto.integrador3.senac.mediotec.pi3_mediotec.conceito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.Aluno;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoResumidoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorCompletoDTO;
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

    // POST de conceito com apenas IDs (aluno, turma, disciplina, professor) - usando ConceitoResumidoDTO
    @Transactional
    public ConceitoDTO salvarConceito(ConceitoResumidoDTO conceitoResumidoDTO) {
        // Busca o aluno pelo ID passado
        Aluno aluno = alunoRepository.findById(conceitoResumidoDTO.getAlunoId())
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        // Busca TurmaDisciplinaProfessor pelo ID composto
        TurmaDisciplinaProfessor turmaDisciplinaProfessor = turmaDisciplinaProfessorRepository.findById(
                new TurmaDisciplinaProfessorId(
                        conceitoResumidoDTO.getTurmaId(),
                        conceitoResumidoDTO.getDisciplinaId(),
                        conceitoResumidoDTO.getProfessorId()))
                .orElseThrow(() -> new RuntimeException("TurmaDisciplinaProfessor não encontrado"));

        // Criação do conceito
        Conceito conceito = Conceito.builder()
                .notaUnidade1(conceitoResumidoDTO.getNotaUnidade1())
                .notaUnidade2(conceitoResumidoDTO.getNotaUnidade2())
                .notaUnidade3(conceitoResumidoDTO.getNotaUnidade3())
                .notaUnidade4(conceitoResumidoDTO.getNotaUnidade4())
                .recuperacaoNota(conceitoResumidoDTO.getRecuperacaoNota())
                .aluno(aluno)
                .turmaDisciplinaProfessor(turmaDisciplinaProfessor)
                .build();

        // Valida e calcula a média e status de aprovação
        conceito.calcularMediaEStatus();
        
        Conceito savedConceito = conceitoRepository.save(conceito);
        return convertToDTO(savedConceito);
    }

    // PUT de conceito com apenas IDs (aluno, turma, disciplina, professor) - usando ConceitoResumidoDTO
    @Transactional
    public ConceitoDTO atualizarConceito(Long id, ConceitoResumidoDTO conceitoResumidoDTO) {
        Conceito conceito = conceitoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conceito não encontrado"));

        // Atualiza os campos do conceito
        conceito.setNotaUnidade1(conceitoResumidoDTO.getNotaUnidade1());
        conceito.setNotaUnidade2(conceitoResumidoDTO.getNotaUnidade2());
        conceito.setNotaUnidade3(conceitoResumidoDTO.getNotaUnidade3());
        conceito.setNotaUnidade4(conceitoResumidoDTO.getNotaUnidade4());
        conceito.setRecuperacaoNota(conceitoResumidoDTO.getRecuperacaoNota());


        // Valida e recalcula o conceito, média e status de aprovação
        conceito.calcularMediaEStatus();

        // Salva o conceito atualizado
        Conceito updatedConceito = conceitoRepository.save(conceito);

        // Retorna o DTO atualizado
        return convertToDTO(updatedConceito);
    }

    // GET de conceito resumido
    public List<ConceitoDTO> listarConceitos() {
        return conceitoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Busca conceito por ID
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

    // Busca todos os conceitos de um aluno
    public List<ConceitoDTO> buscarConceitosPorAluno(Long alunoId) {
        return conceitoRepository.findByAluno_Id(alunoId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Busca conceitos de um aluno por uma disciplina específica
    public List<ConceitoDTO> buscarConceitosPorAlunoEDisciplina(Long alunoId, Long disciplinaId) {
        return conceitoRepository.findByAluno_IdAndTurmaDisciplinaProfessor_Disciplina_Id(alunoId, disciplinaId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    
    @Transactional
    public ConceitoDTO salvarConceitoParaAluno(String idProfessor, Long idAluno, Long idDisciplina, ConceitoResumidoDTO conceitoResumidoDTO) {
        // Busca o aluno pelo ID passado
        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        // Busca TurmaDisciplinaProfessor pelo ID composto
        TurmaDisciplinaProfessor turmaDisciplinaProfessor = turmaDisciplinaProfessorRepository.findById(
                new TurmaDisciplinaProfessorId(
                        conceitoResumidoDTO.getTurmaId(),
                        idDisciplina,
                        idProfessor))
                .orElseThrow(() -> new RuntimeException("TurmaDisciplinaProfessor não encontrado"));

        // Criação do conceito
        Conceito conceito = Conceito.builder()
                .notaUnidade1(conceitoResumidoDTO.getNotaUnidade1())
                .notaUnidade2(conceitoResumidoDTO.getNotaUnidade2())
                .notaUnidade3(conceitoResumidoDTO.getNotaUnidade3())
                .notaUnidade4(conceitoResumidoDTO.getNotaUnidade4())
                .recuperacaoNota(conceitoResumidoDTO.getRecuperacaoNota())
                .aluno(aluno)
                .turmaDisciplinaProfessor(turmaDisciplinaProfessor)
                .build();

        // Valida e calcula a média e status de aprovação
        conceito.calcularMediaEStatus();
        
        Conceito savedConceito = conceitoRepository.save(conceito);
        return convertToDTO(savedConceito);
    }

    @Transactional
    public ConceitoDTO atualizarConceitoParaAluno(String idProfessor, Long idAluno, Long idDisciplina, Long idConceito, ConceitoResumidoDTO conceitoResumidoDTO) {
        Conceito conceito = conceitoRepository.findById(idConceito)
                .orElseThrow(() -> new RuntimeException("Conceito não encontrado"));

        // Atualiza os campos do conceito
        conceito.setNotaUnidade1(conceitoResumidoDTO.getNotaUnidade1());
        conceito.setNotaUnidade2(conceitoResumidoDTO.getNotaUnidade2());
        conceito.setNotaUnidade3(conceitoResumidoDTO.getNotaUnidade3());
        conceito.setNotaUnidade4(conceitoResumidoDTO.getNotaUnidade4());
        conceito.setRecuperacaoNota(conceitoResumidoDTO.getRecuperacaoNota());

        // Valida e recalcula o conceito, média e status de aprovação
        conceito.calcularMediaEStatus();

        // Salva o conceito atualizado
        Conceito updatedConceito = conceitoRepository.save(conceito);

        // Retorna o DTO atualizado
        return convertToDTO(updatedConceito);
    }

    
    
    // Método auxiliar para converter a entidade Conceito em ConceitoDTO
    private ConceitoDTO convertToDTO(Conceito conceito) {
        return ConceitoDTO.builder()
                .idConceito(conceito.getId_conceito())
                .notaUnidade1(conceito.getNotaUnidade1())
                .notaUnidade2(conceito.getNotaUnidade2())
                .notaUnidade3(conceito.getNotaUnidade3())
                .notaUnidade4(conceito.getNotaUnidade4())
                .recuperacaoNota(conceito.getRecuperacaoNota())
                .mediaFinal(conceito.getMediaFinal())
                .aprovado(conceito.getAprovado())
                .aluno(AlunoResumidoDTO.builder()
                        .nomeAluno(conceito.getAluno().getNome())
                        .email(conceito.getAluno().getEmail())
                        .build())
                .turmaDisciplinaProfessor(TurmaDisciplinaProfessorCompletoDTO.builder()
                        .nomeTurma(conceito.getTurmaDisciplinaProfessor().getTurma().getNome())
                        .nomeProfessor(conceito.getTurmaDisciplinaProfessor().getProfessor().getNome() + " " +
                                       conceito.getTurmaDisciplinaProfessor().getProfessor().getUltimoNome())
                        .nomeDisciplina(conceito.getTurmaDisciplinaProfessor().getDisciplina().getNome())
                        .build())
                .build();
    }
}
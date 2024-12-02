package projeto.integrador3.senac.mediotec.pi3_mediotec.comunicado;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.Coordenacao;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.Coordenador;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.CoordenadorRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.Professor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaRepository;

import projeto.integrador3.senac.mediotec.pi3_mediotec.comunicado.AlunoResumidoComTurmaDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.comunicado.ComunicadoCompletoDTO;

@Service
public class ComunicadoService {

    @Autowired
    private ComunicadoRepository comunicadoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private CoordenacaoRepository coordenacaoRepository;
    
    @Autowired
    private CoordenadorRepository coordenadorRepository;

    // ============================== CRIAR COMUNICADO ==============================

    public ComunicadoCompletoDTO criarComunicadoPorCoordenador(String coordenadorCpf, ComunicadoDTO comunicadoDTO) {
        // Buscar o coordenador pelo CPF
        Coordenador coordenador = coordenadorRepository.findByCpf(coordenadorCpf)
                .orElseThrow(() -> new RuntimeException("Coordenador não encontrado"));

        // Construir o comunicado
        Comunicado comunicado = Comunicado.builder()
                .titulo(comunicadoDTO.getTitulo())
                .conteudo(comunicadoDTO.getConteudo())
                .dataEnvio(LocalDateTime.now())
                .remetenteCoordenador(coordenador)
                .receptorAlunos(comunicadoDTO.getAlunoIds())
                .receptorTurmas(comunicadoDTO.getTurmaIds())
                .build();

        // Salvar o comunicado
        comunicadoRepository.save(comunicado);

        // Converter e retornar o DTO completo
        return converterParaDTOCompleto(comunicado);
    }



    public ComunicadoCompletoDTO criarComunicadoPorProfessor(String professorCpf, ComunicadoDTO comunicadoDTO) {
        Professor professor = professorRepository.findByCpf(professorCpf)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        Comunicado comunicado = Comunicado.builder()
                .titulo(comunicadoDTO.getTitulo())
                .conteudo(comunicadoDTO.getConteudo())
                .dataEnvio(LocalDateTime.now())
                .remetenteProfessor(professor)
                .receptorAlunos(comunicadoDTO.getAlunoIds())
                .receptorTurmas(comunicadoDTO.getTurmaIds())
                .build();

        comunicadoRepository.save(comunicado);

        return converterParaDTOCompleto(comunicado);
    }

    // ============================== LISTAR COMUNICADOS ==============================

    public List<ComunicadoCompletoDTO> listarComunicadosPorCoordenador(String coordenadorCpf) {
        List<Comunicado> comunicados = comunicadoRepository.findByRemetenteCoordenador_Cpf(coordenadorCpf);
        return comunicados.stream().map(this::converterParaDTOCompleto).collect(Collectors.toList());
    }


    public List<ComunicadoCompletoDTO> listarComunicadosPorProfessor(String professorCpf) {
        List<Comunicado> comunicados = comunicadoRepository.findByRemetenteProfessor_Cpf(professorCpf);
        return comunicados.stream().map(this::converterParaDTOCompleto).collect(Collectors.toList());
    }

    public List<ComunicadoCompletoDTO> listarComunicadosPorAluno(String identificador) {
        List<Comunicado> comunicados;

        if (identificador.matches("\\d+")) {
            // Identificador é um número (ID do aluno)
            Long alunoId = Long.parseLong(identificador);
            comunicados = comunicadoRepository.findByReceptorAlunosId(alunoId);
        } else if (identificador.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
            // Identificador é um CPF
            comunicados = comunicadoRepository.findByReceptorAlunosCpf(identificador);
        } else {
            // Identificador é um email
            comunicados = comunicadoRepository.findByReceptorAlunosEmail(identificador);
        }

        // Converter os comunicados encontrados para DTOs completos
        return comunicados.stream().map(this::converterParaDTOCompleto).collect(Collectors.toList());
    }


    public List<ComunicadoCompletoDTO> listarComunicadosPorTurma(Long turmaId) {
        List<Comunicado> comunicados = comunicadoRepository.findByReceptorTurmasContaining(turmaId);
        return comunicados.stream().map(this::converterParaDTOCompleto).collect(Collectors.toList());
    }

    // ============================== ATUALIZAR COMUNICADO ==============================

    public ComunicadoCompletoDTO atualizarComunicado(Long comunicadoId, ComunicadoDTO comunicadoDTO) {
        Comunicado comunicado = comunicadoRepository.findById(comunicadoId)
                .orElseThrow(() -> new RuntimeException("Comunicado não encontrado"));

        comunicado.setTitulo(comunicadoDTO.getTitulo());
        comunicado.setConteudo(comunicadoDTO.getConteudo());
        comunicado.setReceptorAlunos(comunicadoDTO.getAlunoIds());
        comunicado.setReceptorTurmas(comunicadoDTO.getTurmaIds());
        comunicado.setDataEnvio(LocalDateTime.now());

        comunicadoRepository.save(comunicado);

        return converterParaDTOCompleto(comunicado);
    }

    // ============================== DELETAR COMUNICADO ==============================

    public void deletarComunicado(Long comunicadoId) {
        Comunicado comunicado = comunicadoRepository.findById(comunicadoId)
                .orElseThrow(() -> new RuntimeException("Comunicado não encontrado"));

        comunicadoRepository.delete(comunicado);
    }

    // ============================== MÉTODOS AUXILIARES ==============================
    private ComunicadoCompletoDTO converterParaDTOCompleto(Comunicado comunicado) {
        // Mapeia os alunos e suas turmas
        List<AlunoResumidoComTurmaDTO> alunos = comunicado.getReceptorAlunos().stream()
                .map(alunoId -> alunoRepository.findById(alunoId)
                        .map(aluno -> {
                            // Obtém a primeira turma do aluno, caso exista
                            TurmaResumida2DTO turmaResumida = aluno.getTurmas().stream()
                                    .findFirst()
                                    .map(turma -> new TurmaResumida2DTO(
                                            turma.getId(),
                                            turma.getNome(),
                                            turma.getAnoEscolar(),
                                            turma.getTurno()))
                                    .orElse(null);

                            // Retorna o DTO do aluno resumido com a turma associada
                            return new AlunoResumidoComTurmaDTO(
                                    aluno.getId(),
                                    aluno.getNome(),
                                    aluno.getUltimoNome(),
                                    turmaResumida);
                        })
                        .orElseThrow(() -> new RuntimeException("Aluno com ID " + alunoId + " não encontrado")))
                .toList();

        // Mapeia as turmas diretamente do comunicado
        List<TurmaResumida2DTO> turmas = comunicado.getReceptorTurmas().stream()
                .filter(turmaId -> turmaId != null && turmaId > 0) // Filtra IDs inválidos
                .map(turmaId -> turmaRepository.findById(turmaId)
                        .map(turma -> new TurmaResumida2DTO(
                                turma.getId(),
                                turma.getNome(),
                                turma.getAnoEscolar(),
                                turma.getTurno()))
                        .orElseThrow(() -> new RuntimeException("Turma com ID " + turmaId + " não encontrada")))
                .toList();

        // Define o remetente com base no tipo (Professor ou Coordenador)
        String remetente = comunicado.getRemetenteProfessor() != null
                ? "Prof. " + comunicado.getRemetenteProfessor().getNome() + " " + comunicado.getRemetenteProfessor().getUltimoNome()
                : "Coord. " + comunicado.getRemetenteCoordenador().getNome()+ " " + comunicado.getRemetenteCoordenador().getUltimoNome();

        // Retorna o DTO completo do comunicado
        return new ComunicadoCompletoDTO(
                comunicado.getId(),
                comunicado.getTitulo(),
                comunicado.getConteudo(),
                comunicado.getDataEnvio(),
                remetente,
                alunos,
                turmas
        );
    }







}

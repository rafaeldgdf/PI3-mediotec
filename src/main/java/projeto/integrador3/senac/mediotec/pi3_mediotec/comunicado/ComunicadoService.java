package projeto.integrador3.senac.mediotec.pi3_mediotec.comunicado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoResumidoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.Coordenacao;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoResumidaDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.Coordenador;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.CoordenadorResumidoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.Professor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorResumido3DTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaResumida2DTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    // ============================== CRUD LOGIC ==============================

    public ComunicadoSimplesDTO criarComunicadoPorCoordenador(String coordenadorCpf, ComunicadoDTO comunicadoDTO) {
        Coordenacao coordenacao = coordenacaoRepository.findByCoordenadores_Cpf(coordenadorCpf)
                .orElseThrow(() -> new RuntimeException("Coordenação não encontrada"));

        Comunicado comunicado = new Comunicado(
                null,
                comunicadoDTO.getTitulo(),
                comunicadoDTO.getConteudo(),
                comunicadoDTO.getDataEnvio(),
                null,
                coordenacao,
                comunicadoDTO.getAlunoIds(),
                comunicadoDTO.getTurmaIds()
        );
        return convertToSimplesDTO(comunicadoRepository.save(comunicado));
    }

    public ComunicadoSimplesDTO criarComunicadoPorProfessor(String professorCpf, ComunicadoDTO comunicadoDTO) {
        Professor professor = professorRepository.findByCpf(professorCpf)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        Comunicado comunicado = new Comunicado(
                null,
                comunicadoDTO.getTitulo(),
                comunicadoDTO.getConteudo(),
                comunicadoDTO.getDataEnvio(),
                professor,
                null,
                comunicadoDTO.getAlunoIds(),
                comunicadoDTO.getTurmaIds()
        );
        return convertToSimplesDTO(comunicadoRepository.save(comunicado));
    }

    public List<ComunicadoSimplificado2DTO> listarComunicadosPorCoordenador(String identificador) {
        boolean isCpf = identificador.matches("\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}");
        return (isCpf
                ? comunicadoRepository.findByRemetenteCoordenacao_Coordenadores_Cpf(identificador)
                : comunicadoRepository.findByRemetenteCoordenacao_Coordenadores_Email(identificador))
                .stream()
                .map(this::convertToSimplificadoDTO)
                .collect(Collectors.toList());
    }

    public List<ComunicadoDetalhadoDTO> listarComunicadosPorTurma(Long turmaId) {
        return comunicadoRepository.findByReceptorTurmasContaining(turmaId)
                .stream()
                .map(this::convertToDetalhadoDTO)
                .collect(Collectors.toList());
    }

    public List<ComunicadoSimplificado2DTO> listarComunicadosPorProfessor(String identificador) {
        boolean isCpf = identificador.matches("\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}");
        return (isCpf
                ? comunicadoRepository.findByRemetenteProfessor_Cpf(identificador)
                : comunicadoRepository.findByRemetenteProfessor_Email(identificador))
                .stream()
                .map(this::convertToSimplificadoDTO)
                .collect(Collectors.toList());
    }

    public List<ComunicadoDetalhadoDTO> listarComunicadosPorAluno(String identificador) {
        boolean isCpf = identificador.matches("\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}");
        return (isCpf
                ? comunicadoRepository.findByReceptorAlunosCpf(identificador)
                : comunicadoRepository.findByReceptorAlunosEmail(identificador))
                .stream()
                .map(this::convertToDetalhadoDTO)
                .collect(Collectors.toList());
    }

    public ComunicadoSimplesDTO atualizarComunicado(Long comunicadoId, ComunicadoDTO comunicadoDTO) {
        Comunicado comunicado = comunicadoRepository.findById(comunicadoId)
                .orElseThrow(() -> new RuntimeException("Comunicado não encontrado"));

        comunicado.setTitulo(comunicadoDTO.getTitulo());
        comunicado.setConteudo(comunicadoDTO.getConteudo());
        comunicado.setDataEnvio(comunicadoDTO.getDataEnvio());
        comunicado.setReceptorAlunos(comunicadoDTO.getAlunoIds());
        comunicado.setReceptorTurmas(comunicadoDTO.getTurmaIds());

        return convertToSimplesDTO(comunicadoRepository.save(comunicado));
    }

    public void deletarComunicado(Long comunicadoId) {
        Comunicado comunicado = comunicadoRepository.findById(comunicadoId)
                .orElseThrow(() -> new RuntimeException("Comunicado não encontrado"));

        comunicadoRepository.delete(comunicado);
    }

    // ============================== DTO Converters ==============================

    private ComunicadoSimplesDTO convertToSimplesDTO(Comunicado comunicado) {
        return new ComunicadoSimplesDTO(
                comunicado.getId(),
                comunicado.getTitulo(),
                comunicado.getConteudo(),
                comunicado.getDataEnvio()
        );
    }

    private ComunicadoSimplificado2DTO convertToSimplificadoDTO(Comunicado comunicado) {
        RemetenteCoordenadorDTO remetente = convertToRemetente(comunicado);
        List<DestinatarioDTO> destinatarios = convertToDestinatarios(comunicado);

        return new ComunicadoSimplificado2DTO(
                comunicado.getId(),
                comunicado.getTitulo(),
                comunicado.getConteudo(),
                comunicado.getDataEnvio(),
                remetente,
                destinatarios
        );
    }

    private ComunicadoDetalhadoDTO convertToDetalhadoDTO(Comunicado comunicado) {
        ProfessorResumido3DTO professorDTO = (comunicado.getRemetenteProfessor() != null)
                ? new ProfessorResumido3DTO(
                        comunicado.getRemetenteProfessor().getNome(),
                        comunicado.getRemetenteProfessor().getEmail()
                )
                : null;

        CoordenacaoResumidaDTO coordenacaoDTO = (comunicado.getRemetenteCoordenacao() != null)
                ? new CoordenacaoResumidaDTO(
                        comunicado.getRemetenteCoordenacao().getId(),
                        comunicado.getRemetenteCoordenacao().getNome(),
                        null
                )
                : null;

        List<AlunoResumidoDTO> alunos = comunicado.getReceptorAlunos().stream()
                .map(alunoId -> alunoRepository.findById(alunoId).orElse(null))
                .filter(aluno -> aluno != null)
                .map(aluno -> new AlunoResumidoDTO(
                        aluno.getId(),
                        aluno.getNome(),
                        aluno.getEmail(),
                        aluno.getCpf(),
                        aluno.isStatus()
                ))
                .collect(Collectors.toList());

        List<TurmaResumida2DTO> turmas = comunicado.getReceptorTurmas().stream()
                .map(turmaId -> turmaRepository.findById(turmaId).orElse(null))
                .filter(turma -> turma != null)
                .map(turma -> new TurmaResumida2DTO(
                        turma.getId(),
                        turma.getNome(),
                        turma.getAnoLetivo(),
                        turma.getAnoEscolar(),
                        turma.getTurno()
                ))
                .collect(Collectors.toList());

        return new ComunicadoDetalhadoDTO(
                comunicado.getId(),
                comunicado.getTitulo(),
                comunicado.getConteudo(),
                comunicado.getDataEnvio(),
                professorDTO,
                coordenacaoDTO,
                alunos,
                turmas
        );
    }

    private RemetenteCoordenadorDTO convertToRemetente(Comunicado comunicado) {
        if (comunicado.getRemetenteCoordenacao() != null) {
            Coordenador coordenador = comunicado.getRemetenteCoordenacao().getCoordenadores()
                    .stream().findFirst()
                    .orElse(null);

            if (coordenador != null) {
                return new RemetenteCoordenadorDTO(
                        "Coord. " + coordenador.getNome() + " " + coordenador.getUltimoNome() +
                                " - " + comunicado.getRemetenteCoordenacao().getNome(),
                        comunicado.getRemetenteCoordenacao().getNome()
                );
            }

            return new RemetenteCoordenadorDTO(
                    "Coordenação sem coordenador definido - " + comunicado.getRemetenteCoordenacao().getNome(),
                    comunicado.getRemetenteCoordenacao().getNome()
            );
        }

        if (comunicado.getRemetenteProfessor() != null) {
            return new RemetenteCoordenadorDTO(
                    "Professor " + comunicado.getRemetenteProfessor().getNome() + " " +
                            comunicado.getRemetenteProfessor().getUltimoNome(),
                    "Professor"
            );
        }

        return new RemetenteCoordenadorDTO("Remetente desconhecido", "N/A");
    }

    private List<DestinatarioDTO> convertToDestinatarios(Comunicado comunicado) {
        List<DestinatarioDTO> destinatarios = new ArrayList<>();

        // Processa os alunos
        comunicado.getReceptorAlunos().forEach(alunoId -> {
            alunoRepository.findById(alunoId).ifPresent(aluno ->
                    destinatarios.add(new DestinatarioDTO("aluno", aluno.getNome() + " " + aluno.getUltimoNome())));
        });

        // Processa as turmas
        comunicado.getReceptorTurmas().forEach(turmaId -> {
            turmaRepository.findById(turmaId).ifPresent(turma ->
                    destinatarios.add(new DestinatarioDTO("turma", turma.getNome())));
        });

        return destinatarios;
    }

}

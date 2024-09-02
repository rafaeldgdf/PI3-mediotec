package projeto.integrador3.senac.mediotec.pi3_mediotec.comunicado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.Aluno;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.Coordenacao;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.Professor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.Turma;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComunicadoService {

    @Autowired
    private ComunicadoRepository comunicadoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private CoordenacaoRepository coordenacaoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Transactional
    public ComunicadoDTO salvarComunicado(ComunicadoDTO comunicadoDTO) {
        Comunicado comunicado = convertToEntity(comunicadoDTO);
        Comunicado savedComunicado = comunicadoRepository.save(comunicado);
        return convertToDTO(savedComunicado);
    }

    @Transactional
    public ComunicadoDTO atualizarComunicado(Long id, ComunicadoDTO comunicadoDTO) {
        Comunicado comunicado = comunicadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comunicado não encontrado"));

        comunicado.setConteudo(comunicadoDTO.getConteudo());
        comunicado.setData_envio(comunicadoDTO.getData_envio());
        comunicado.setCoordenacao(buscarCoordenacaoPorId(comunicadoDTO.getCoordenacao().getIdCoordenacao()));
        comunicado.setProfessor(buscarProfessorPorId(comunicadoDTO.getProfessor().getCpf()));
        comunicado.setAluno(buscarAlunoPorId(comunicadoDTO.getAluno().getId()));
        comunicado.setTurma(buscarTurmaPorId(comunicadoDTO.getTurma().getIdTurma()));

        Comunicado updatedComunicado = comunicadoRepository.save(comunicado);
        return convertToDTO(updatedComunicado);
    }

    public List<ComunicadoDTO> listarComunicados() {
        return comunicadoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ComunicadoDTO> buscarComunicadoPorId(Long id) {
        return comunicadoRepository.findById(id)
                .map(this::convertToDTO);
    }

    public void deletarComunicado(Long id) {
        Comunicado comunicado = comunicadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comunicado não encontrado"));
        comunicadoRepository.delete(comunicado);
    }

    private ComunicadoDTO convertToDTO(Comunicado comunicado) {
        return ComunicadoDTO.builder()
                .id_comunicado(comunicado.getId_comunicado())
                .conteudo(comunicado.getConteudo())
                .data_envio(new java.sql.Date(comunicado.getData_envio().getTime()))
                .coordenacao(comunicado.getCoordenacao() != null ? CoordenacaoDTO.builder()
                        .idCoordenacao(comunicado.getCoordenacao().getId_coordenacao())
                        .nome(comunicado.getCoordenacao().getNome())
                        .build() : null)
                .professor(comunicado.getProfessor() != null ? ProfessorDTO.builder()
                        .cpf(comunicado.getProfessor().getCpf())
                        .nome(comunicado.getProfessor().getNome())
                        .build() : null)
                .aluno(comunicado.getAluno() != null ? AlunoDTO.builder()
                        .id(comunicado.getAluno().getId())
                        .nome(comunicado.getAluno().getNome())
                        .build() : null)
                .turma(comunicado.getTurma() != null ? TurmaDTO.builder()
                        .idTurma(comunicado.getTurma().getId_turma())
                        .nome(comunicado.getTurma().getNome())
                        .ano(comunicado.getTurma().getAno())
                        .build() : null)
                .build();
    }

    private Comunicado convertToEntity(ComunicadoDTO comunicadoDTO) {
        return Comunicado.builder()
                .id_comunicado(comunicadoDTO.getId_comunicado())
                .conteudo(comunicadoDTO.getConteudo())
                .data_envio(comunicadoDTO.getData_envio())
                .coordenacao(buscarCoordenacaoPorId(comunicadoDTO.getCoordenacao().getIdCoordenacao()))
                .professor(buscarProfessorPorId(comunicadoDTO.getProfessor().getCpf()))
                .aluno(buscarAlunoPorId(comunicadoDTO.getAluno().getId()))
                .turma(buscarTurmaPorId(comunicadoDTO.getTurma().getIdTurma()))
                .build();
    }

    private Coordenacao buscarCoordenacaoPorId(Long idCoordenacao) {
        return coordenacaoRepository.findById(idCoordenacao)
                .orElseThrow(() -> new RuntimeException("Coordenação não encontrada"));
    }

    private Professor buscarProfessorPorId(String cpfProfessor) {
        return professorRepository.findById(cpfProfessor)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
    }

    private Aluno buscarAlunoPorId(Long idAluno) {
        return alunoRepository.findById(idAluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
    }

    private Turma buscarTurmaPorId(Long idTurma) {
        return turmaRepository.findById(idTurma)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));
    }
}

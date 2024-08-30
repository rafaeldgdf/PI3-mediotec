package projeto.integrador3.senac.mediotec.pi3_mediotec.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projeto.integrador3.senac.mediotec.pi3_mediotec.dtos.CoordenacaoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.dtos.TurmaDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.Aluno;
import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.Coordenacao;
import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.Turma;
import projeto.integrador3.senac.mediotec.pi3_mediotec.repositories.AlunoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.repositories.CoordenacaoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.repositories.TurmaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TurmaService {

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private CoordenacaoRepository coordenacaoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    // Lista todas as turmas
    public List<TurmaDTO> getAllTurmas() {
        return turmaRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Busca turma pelo id
    public Optional<TurmaDTO> getTurmaById(Long id) {
        return turmaRepository.findById(id)
                .map(this::convertToDto);
    }

    @Transactional
    public Turma saveTurma(Turma turma, List<Long> alunosIds) {
        List<Aluno> alunos = new ArrayList<>();
        for (Long alunoId : alunosIds) {
            Aluno aluno = alunoRepository.findById(alunoId)
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
            alunos.add(aluno);
        }

        // Adiciona os alunos à turma
        for (Aluno aluno : alunos) {
            turma.addAluno(aluno);
        }

        return turmaRepository.save(turma);
    }


    public TurmaDTO saveTurma(TurmaDTO turmaDTO) {
        Turma turma = new Turma();
        turma.setNome(turmaDTO.getNome());
        turma.setAno(turmaDTO.getAno());

        Coordenacao coordenacao = coordenacaoRepository.findById(turmaDTO.getCoordenacao().getIdCoordenacao())
                .orElseThrow(() -> new RuntimeException("Coordenação não encontrada"));
        turma.setCoordenacao(coordenacao);

        List<Long> alunosIds = new ArrayList<>(turmaDTO.getAlunosIds());
        for (Long alunoId : alunosIds) {
            Aluno aluno = alunoRepository.findById(alunoId)
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
            turma.addAluno(aluno);
        }

        Turma savedTurma = turmaRepository.save(turma);
        return convertToDto(savedTurma);
    }

    @Transactional
    public TurmaDTO updateTurma(Long id, TurmaDTO turmaDTO) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

        turma.setNome(turmaDTO.getNome());
        turma.setAno(turmaDTO.getAno());

        Coordenacao coordenacao = coordenacaoRepository.findById(turmaDTO.getCoordenacao().getIdCoordenacao())
                .orElseThrow(() -> new RuntimeException("Coordenação não encontrada"));
        turma.setCoordenacao(coordenacao);

        List<Long> alunosIds = new ArrayList<>(turmaDTO.getAlunosIds());
        for (Long alunoId : alunosIds) {
            Aluno aluno = alunoRepository.findById(alunoId)
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
            turma.addAluno(aluno);
        }

        Turma updatedTurma = turmaRepository.save(turma);
        return convertToDto(updatedTurma);
    }


    // Edita turma
    @Transactional
    public TurmaDTO updateTurma(Long id, Turma turmaDetails) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

        turma.setNome(turmaDetails.getNome());
        turma.setAno(turmaDetails.getAno());
        turma.setAlunos(turmaDetails.getAlunos());
        turma.setCoordenacao(turmaDetails.getCoordenacao());
        turma.setTurmaDisciplinas(turmaDetails.getTurmaDisciplinas());

        Turma updatedTurma = turmaRepository.save(turma);
        return convertToDto(updatedTurma);
    }

    // Deleta turma
    @Transactional
    public void deleteTurma(Long id) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));
        turmaRepository.delete(turma);
    }

    // Converte Turma para TurmaDTO
    private TurmaDTO convertToDto(Turma turma) {
        return TurmaDTO.builder()
                .idTurma(turma.getId_turma())
                .nome(turma.getNome())
                .ano(turma.getAno())
                .coordenacao(CoordenacaoDTO.builder()
                        .idCoordenacao(turma.getCoordenacao().getId_coordenacao())
                        .nome(turma.getCoordenacao().getNome())
                        .build())
                .alunosIds(turma.getAlunos().stream()
                        .map(Aluno::getId_aluno)
                        .collect(Collectors.toSet()))
                .build();
    }
}

package projeto.integrador3.senac.mediotec.pi3_mediotec.turma;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.Aluno;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.Coordenacao;

import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoRepository;

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

    // Criação de uma nova turma com DTO
    public TurmaDTO saveTurma(TurmaDTO turmaDTO) {
        Turma turma = new Turma();
        turma.setNome(turmaDTO.getNome());
        turma.setAno(turmaDTO.getAno());

        // Busca a coordenação pelo ID
        Coordenacao coordenacao = coordenacaoRepository.findById(turmaDTO.getCoordenacaoId())
                .orElseThrow(() -> new RuntimeException("Coordenação não encontrada"));
        turma.setCoordenacao(coordenacao);

        // Adiciona os alunos à turma
        List<Long> alunosIds = new ArrayList<>(turmaDTO.getAlunosIds());
        for (Long alunoId : alunosIds) {
            Aluno aluno = alunoRepository.findById(alunoId)
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
            turma.addAluno(aluno);
        }

        Turma savedTurma = turmaRepository.save(turma);
        return convertToDto(savedTurma);
    }

    // Atualização de uma turma existente com DTO
    @Transactional
    public TurmaDTO updateTurma(Long id, TurmaDTO turmaDTO) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

        turma.setNome(turmaDTO.getNome());
        turma.setAno(turmaDTO.getAno());

        // Busca a coordenação pelo ID
        Coordenacao coordenacao = coordenacaoRepository.findById(turmaDTO.getCoordenacaoId())
                .orElseThrow(() -> new RuntimeException("Coordenação não encontrada"));
        turma.setCoordenacao(coordenacao);

        // Atualiza os alunos da turma
        turma.getAlunos().clear(); // Limpa os alunos atuais
        List<Long> alunosIds = new ArrayList<>(turmaDTO.getAlunosIds());
        for (Long alunoId : alunosIds) {
            Aluno aluno = alunoRepository.findById(alunoId)
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
            turma.addAluno(aluno);
        }

        Turma updatedTurma = turmaRepository.save(turma);
        return convertToDto(updatedTurma);
    }
    
    
    public List<TurmaDTO> getAllTurmas() {
        return turmaRepository.findAll().stream()
                .map(this::convertToDto) // Converte cada entidade Turma para TurmaDTO
                .collect(Collectors.toList());
    }
    
    public Optional<TurmaDTO> getTurmaById(Long id) {
        return turmaRepository.findById(id)
                .map(this::convertToDto); // Converte para DTO se encontrado
    }
    
    @Transactional
    public void deleteTurma(Long id) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));
        turmaRepository.delete(turma); // Deleta a entidade
    }
    

    // Converte a entidade Turma para o DTO correspondente
    private TurmaDTO convertToDto(Turma turma) {
        return TurmaDTO.builder()
                .id(turma.getId())
                .nome(turma.getNome())
                .ano(turma.getAno())
                .coordenacaoId(turma.getCoordenacao().getId()) // Agora retorna apenas o ID da coordenação
                .alunosIds(turma.getAlunos().stream()
                        .map(Aluno::getId)
                        .collect(Collectors.toSet()))
                .build();
    }
}

package projeto.integrador3.senac.mediotec.pi3_mediotec.aluno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.EnderecoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.TelefoneDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.Turma;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;
    
    @Autowired
    private TurmaRepository turmaRepository;
   
    

    // Lista todos os alunos
    public List<AlunoDTO> getAllAlunos() {
        return alunoRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Busca aluno pelo ID
    public Optional<AlunoDTO> getAlunoById(Long idAluno) {
        return alunoRepository.findById(idAluno)
                .map(this::convertToDto);
    }

    // Cria um novo aluno
    public AlunoDTO saveAluno(Aluno aluno) {
        if (alunoRepository.existsByCpf(aluno.getCpf())) {
            throw new RuntimeException("Aluno com CPF " + aluno.getCpf() + " já existe");
        }

        // Associa os endereços e telefones ao aluno
        aluno.getEnderecos().forEach(endereco -> endereco.setAluno(aluno));
        aluno.getTelefones().forEach(telefone -> telefone.setAluno(aluno));

        // Certifique-se de que as turmas estão sendo recuperadas do banco de dados
        if (aluno.getTurmas() != null) {
            Set<Turma> turmas = new HashSet<>();
            for (Turma turma : aluno.getTurmas()) {
                Turma existingTurma = turmaRepository.findById(turma.getId())
                        .orElseThrow(() -> new RuntimeException("Turma não encontrada: " + turma.getId()));
                turmas.add(existingTurma);
                aluno.addTurma(existingTurma);
            }
            aluno.setTurmas(turmas);
        }

        // Salva o aluno com a associação das turmas
        Aluno savedAluno = alunoRepository.save(aluno);

        // Aqui, o Hibernate deve persistir as relações na tabela intermediária automaticamente
        return convertToDto(savedAluno);
    }



    // Edita um aluno existente
    public AlunoDTO updateAluno(Long idAluno, Aluno alunoDetails) {
        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        aluno.setNome(alunoDetails.getNome());
        aluno.setUltimoNome(alunoDetails.getUltimoNome());
        aluno.setGenero(alunoDetails.getGenero());
        aluno.setCpf(alunoDetails.getCpf());
        aluno.setEmail(alunoDetails.getEmail());
        aluno.setEnderecos(alunoDetails.getEnderecos());
        aluno.setTelefones(alunoDetails.getTelefones());
        aluno.setTurmas(alunoDetails.getTurmas());

        aluno.getEnderecos().forEach(endereco -> endereco.setAluno(aluno));
        aluno.getTelefones().forEach(telefone -> telefone.setAluno(aluno));
        aluno.getTurmas().forEach(turma -> turma.getAlunos().add(aluno));

        Aluno updatedAluno = alunoRepository.save(aluno);
        return convertToDto(updatedAluno);
    }


    // Deleta um aluno
    public void deleteAluno(Long idAluno) {
        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        alunoRepository.delete(aluno);
    }

    
//metodos exatras 
    // Converte Aluno para AlunoDTO	
    private AlunoDTO convertToDto(Aluno aluno) {
        return AlunoDTO.builder()
                .id(aluno.getId())
                .nome(aluno.getNome())
                .ultimoNome(aluno.getUltimoNome())
                .genero(aluno.getGenero())
                .cpf(aluno.getCpf())
                .email(aluno.getEmail())
                .enderecos(aluno.getEnderecos().stream()
                        .map(endereco -> EnderecoDTO.builder()
                                .cep(endereco.getCep())
                                .rua(endereco.getRua())
                                .numero(endereco.getNumero())
                                .bairro(endereco.getBairro())
                                .cidade(endereco.getCidade())
                                .estado(endereco.getEstado())
                                .build())
                        .collect(Collectors.toSet()))
                .telefones(aluno.getTelefones().stream()
                        .map(telefone -> TelefoneDTO.builder()
                                .ddd(telefone.getDdd())
                                .numero(telefone.getNumero())
                                .build())
                        .collect(Collectors.toSet()))
                .build();
    }
    
    
    

    
}

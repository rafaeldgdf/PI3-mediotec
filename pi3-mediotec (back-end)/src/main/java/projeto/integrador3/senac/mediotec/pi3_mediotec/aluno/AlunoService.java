package projeto.integrador3.senac.mediotec.pi3_mediotec.aluno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.Coordenacao;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.Endereco;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.EnderecoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.Telefone;
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
    
    @Autowired
    private CoordenacaoRepository coordenacaoRepository;

   
    

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
    @Transactional
    public AlunoDTO saveAluno(AlunoDTO alunoDTO) {
        Aluno aluno = new Aluno();

        aluno.setNome(alunoDTO.getNome());
        aluno.setUltimoNome(alunoDTO.getUltimoNome());
        aluno.setGenero(alunoDTO.getGenero());
        aluno.setCpf(alunoDTO.getCpf());
        aluno.setEmail(alunoDTO.getEmail());

        // Associa a coordenacao pelo ID
        Coordenacao coordenacao = coordenacaoRepository.findById(alunoDTO.getCoordenacaoId())
                .orElseThrow(() -> new RuntimeException("Coordenação não encontrada: " + alunoDTO.getCoordenacaoId()));
        aluno.setCoordenacao(coordenacao);

        // Associa as turmas pelo ID
        Set<Turma> turmas = new HashSet<>();
        for (Long turmaId : alunoDTO.getTurmasIds()) {
            Turma turma = turmaRepository.findById(turmaId)
                    .orElseThrow(() -> new RuntimeException("Turma não encontrada: " + turmaId));
            turmas.add(turma);
        }
        aluno.setTurmas(turmas);

        // Endereços e telefones são opcionais
        if (alunoDTO.getEnderecos() != null) {
            aluno.getEnderecos().forEach(endereco -> endereco.setAluno(aluno));
        }
        if (alunoDTO.getTelefones() != null) {
            aluno.getTelefones().forEach(telefone -> telefone.setAluno(aluno));
        }

        // Salva o aluno com as turmas associadas
        Aluno savedAluno = alunoRepository.save(aluno);
        return convertToDto(savedAluno);
    }



    // Edita um aluno existente
    @Transactional
    public AlunoDTO updateAluno(Long id, AlunoDTO alunoDTO) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado: " + id));

        aluno.setNome(alunoDTO.getNome());
        aluno.setUltimoNome(alunoDTO.getUltimoNome());
        aluno.setGenero(alunoDTO.getGenero());
        aluno.setCpf(alunoDTO.getCpf());
        aluno.setEmail(alunoDTO.getEmail());

        // Atualiza a coordenacao
        Coordenacao coordenacao = coordenacaoRepository.findById(alunoDTO.getCoordenacaoId())
                .orElseThrow(() -> new RuntimeException("Coordenação não encontrada: " + alunoDTO.getCoordenacaoId()));
        aluno.setCoordenacao(coordenacao);

        // Atualiza as turmas
        Set<Turma> turmas = new HashSet<>();
        for (Long turmaId : alunoDTO.getTurmasIds()) {
            Turma turma = turmaRepository.findById(turmaId)
                    .orElseThrow(() -> new RuntimeException("Turma não encontrada: " + turmaId));
            turmas.add(turma);
        }
        aluno.setTurmas(turmas);

        // Atualiza os endereços se fornecidos
        if (alunoDTO.getEnderecos() != null) {
            aluno.getEnderecos().clear();  // Remove os endereços antigos
            alunoDTO.getEnderecos().forEach(enderecoDTO -> {
                Endereco endereco = new Endereco();
                endereco.setCep(enderecoDTO.getCep());
                endereco.setRua(enderecoDTO.getRua());
                endereco.setNumero(enderecoDTO.getNumero());
                endereco.setBairro(enderecoDTO.getBairro());
                endereco.setCidade(enderecoDTO.getCidade());
                endereco.setEstado(enderecoDTO.getEstado());
                endereco.setAluno(aluno);  // Configura o aluno no endereço
                aluno.getEnderecos().add(endereco);
            });
        }

        // Atualiza os telefones se fornecidos
        if (alunoDTO.getTelefones() != null) {
            aluno.getTelefones().clear();  // Remove os telefones antigos
            alunoDTO.getTelefones().forEach(telefoneDTO -> {
                Telefone telefone = new Telefone();
                telefone.setDdd(telefoneDTO.getDdd());
                telefone.setNumero(telefoneDTO.getNumero());
                telefone.setAluno(aluno);  // Configura o aluno no telefone
                aluno.getTelefones().add(telefone);
            });
        }

        // Salva o aluno atualizado no banco de dados
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
                .coordenacaoId(aluno.getCoordenacao().getId())
                .turmasIds(aluno.getTurmas().stream()
                        .map(Turma::getId)
                        .collect(Collectors.toSet()))
                .enderecos(aluno.getEnderecos() != null ? aluno.getEnderecos().stream()
                        .map(endereco -> EnderecoDTO.builder()
                                .cep(endereco.getCep())
                                .rua(endereco.getRua())
                                .numero(endereco.getNumero())
                                .bairro(endereco.getBairro())
                                .cidade(endereco.getCidade())
                                .estado(endereco.getEstado())
                                .build())
                        .collect(Collectors.toSet()) : null)
                .telefones(aluno.getTelefones() != null ? aluno.getTelefones().stream()
                        .map(telefone -> TelefoneDTO.builder()
                                .ddd(telefone.getDdd())
                                .numero(telefone.getNumero())
                                .build())
                        .collect(Collectors.toSet()) : null)
                .build();
    }
    
    
    
    
}

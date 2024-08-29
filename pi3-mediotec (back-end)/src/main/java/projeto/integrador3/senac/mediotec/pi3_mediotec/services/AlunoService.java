package projeto.integrador3.senac.mediotec.pi3_mediotec.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projeto.integrador3.senac.mediotec.pi3_mediotec.dtos.AlunoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.dtos.CoordenacaoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.dtos.EnderecoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.dtos.TelefoneDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.Aluno;
import projeto.integrador3.senac.mediotec.pi3_mediotec.repositories.AlunoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.repositories.CoordenacaoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;
    

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
        // Verifica se o CPF já existe para evitar duplicações
        if (alunoRepository.existsByCpf(aluno.getCpf())) {
            throw new RuntimeException("Aluno com CPF " + aluno.getCpf() + " já existe");
        }

        // Configura a associação do aluno com seus endereços, telefones e turmas
        aluno.getEnderecos().forEach(endereco -> endereco.setAluno(aluno));
        aluno.getTelefones().forEach(telefone -> telefone.setAluno(aluno));
        aluno.getTurmas().forEach(turma -> turma.getAlunos().add(aluno));

        // Salva o aluno junto com seus endereços, telefones e turmas
        Aluno savedAluno = alunoRepository.save(aluno);
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
                .idAluno(aluno.getId_aluno())
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
                .coordenacao(CoordenacaoDTO.builder()
                        .idCoordenacao(aluno.getCoordenacao().getId_coordenacao())
                        .nome(aluno.getCoordenacao().getNome())
                        .descricao(aluno.getCoordenacao().getDescricao())
                        .build())
                .build();
    }

    
}

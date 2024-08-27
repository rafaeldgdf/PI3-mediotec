package projeto.integrador3.senac.mediotec.pi3_mediotec.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projeto.integrador3.senac.mediotec.pi3_mediotec.dtos.AlunoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.dtos.EnderecoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.dtos.TelefoneDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.Aluno;
import projeto.integrador3.senac.mediotec.pi3_mediotec.repositories.AlunoRepository;

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
        if (alunoRepository.existsById(aluno.getId_aluno())) {
            throw new RuntimeException("Aluno com ID " + aluno.getId_aluno() + " já existe");
        }
        Aluno savedAluno = alunoRepository.save(aluno);
        return convertToDto(savedAluno);
    }

    // Edita um aluno existente
    public AlunoDTO updateAluno(Long idAluno, Aluno alunoDetails) {
        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        aluno.setNome(alunoDetails.getNome());
        aluno.setGenero(alunoDetails.getGenero());
        aluno.setCpf(alunoDetails.getCpf());
        aluno.setEmail(alunoDetails.getEmail());
        aluno.setEnderecos(alunoDetails.getEnderecos());
        aluno.setTelefones(alunoDetails.getTelefones());

        Aluno updatedAluno = alunoRepository.save(aluno);
        return convertToDto(updatedAluno);
    }

    // Deleta um aluno
    public void deleteAluno(Long idAluno) {
        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        alunoRepository.delete(aluno);
    }

    // Converte Aluno para AlunoDTO
    private AlunoDTO convertToDto(Aluno aluno) {
        return AlunoDTO.builder()
                .idAluno(aluno.getId_aluno())
                .nome(aluno.getNome())
                .genero(aluno.getGenero())
                .cpf(aluno.getCpf())
                .email(aluno.getEmail())
                .enderecos(aluno.getEnderecos().stream()
                        .<EnderecoDTO>map(endereco -> EnderecoDTO.builder()
                                .cep(endereco.getCep())
                                .rua(endereco.getRua())
                                .numero(endereco.getNumero())
                                .bairro(endereco.getBairro())
                                .cidade(endereco.getCidade())
                                .estado(endereco.getEstado())
                                .build())
                        .collect(Collectors.toSet()))
                .telefones(aluno.getTelefones().stream()
                        .<TelefoneDTO>map(telefone -> TelefoneDTO.builder()
                                .ddd(telefone.getDdd())
                                .numero(telefone.getNumero())
                                .build())
                        .collect(Collectors.toSet()))
                .build();
    }

}

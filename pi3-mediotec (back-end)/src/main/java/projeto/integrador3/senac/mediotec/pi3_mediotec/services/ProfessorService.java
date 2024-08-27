package projeto.integrador3.senac.mediotec.pi3_mediotec.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projeto.integrador3.senac.mediotec.pi3_mediotec.dtos.CoordenacaoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.dtos.EnderecoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.dtos.ProfessorDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.dtos.TelefoneDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.Professor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.repositories.ProfessorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    // Lista todos os professores
    public List<ProfessorDTO> getAllProfessores() {
        return professorRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Busca um professor pelo ID (CPF)
    public Optional<ProfessorDTO> getProfessorById(String cpf) {
        return professorRepository.findById(cpf)
                .map(this::convertToDto);
    }

    // Cria um novo professor
    public ProfessorDTO saveProfessor(Professor professor) {
        if (professorRepository.existsById(professor.getCpf())) {
            throw new RuntimeException("Professor com CPF " + professor.getCpf() + " já existe");
        }
        Professor savedProfessor = professorRepository.save(professor);
        return convertToDto(savedProfessor);
    }

    // Atualiza um professor existente
    public ProfessorDTO updateProfessor(String cpf, Professor professorDetails) {
        Professor professor = professorRepository.findById(cpf)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        professor.setNome(professorDetails.getNome());
        professor.setGenero(professorDetails.getGenero());
        professor.setEmail(professorDetails.getEmail());
        professor.setEnderecos(professorDetails.getEnderecos());
        professor.setTelefones(professorDetails.getTelefones());
        professor.setCoordenacao(professorDetails.getCoordenacao());

        Professor updatedProfessor = professorRepository.save(professor);
        return convertToDto(updatedProfessor);
    }

    // Deleta um professor
    public void deleteProfessor(String cpf) {
        Professor professor = professorRepository.findById(cpf)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
        professorRepository.delete(professor);
    }
    
 // Converte Professor para ProfessorDTO
    private ProfessorDTO convertToDto(Professor professor) {
        return ProfessorDTO.builder()
                .cpf(professor.getCpf())
                .nome(professor.getNome())
                .genero(professor.getGenero())
                .email(professor.getEmail())
                .enderecos(professor.getEnderecos().stream()
                        .<EnderecoDTO>map(endereco -> EnderecoDTO.builder()
                                .cep(endereco.getCep())
                                .rua(endereco.getRua())
                                .numero(endereco.getNumero())
                                .bairro(endereco.getBairro())
                                .cidade(endereco.getCidade())
                                .estado(endereco.getEstado())
                                .build())
                        .collect(Collectors.toSet()))
                .telefones(professor.getTelefones().stream()
                        .<TelefoneDTO>map(telefone -> TelefoneDTO.builder()
                                .ddd(telefone.getDdd())
                                .numero(telefone.getNumero())
                                .build())
                        .collect(Collectors.toSet()))
                .coordenacao(CoordenacaoDTO.builder()
                        .nome(professor.getCoordenacao().getNome())
                        .descricao(professor.getCoordenacao().getDescricao())
                        .build())
                .build();
    }

}

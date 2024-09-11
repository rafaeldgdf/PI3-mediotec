package projeto.integrador3.senac.mediotec.pi3_mediotec.professor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.Coordenacao;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.Disciplina;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.DisciplinaRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.Endereco;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.EnderecoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.EnderecoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.Telefone;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.TelefoneDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.TelefoneRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.Turma;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private CoordenacaoRepository coordenacaoRepository;


    @Autowired
    private TurmaDisciplinaProfessorRepository turmaDisciplinaProfessorRepository;
    
    @Autowired
    private TurmaRepository turmaRepository;
    

    @Autowired
    private DisciplinaRepository disciplinaRepository;
    
    
    // Cria um novo professor
 // Cria um novo professor
    public ProfessorDTO saveProfessor(ProfessorDTO professorDTO) {
        // Verifica se o professor já existe pelo CPF
        if (professorRepository.existsByCpf(professorDTO.getCpf())) {
            throw new RuntimeException("Professor com CPF " + professorDTO.getCpf() + " já existe");
        }

        // Busca a coordenação pelo ID
        Coordenacao coordenacao = coordenacaoRepository.findById(professorDTO.getCoordenacaoId())
                .orElseThrow(() -> new RuntimeException("Coordenação não encontrada"));

        // Converte os endereços DTO para entidades, se fornecidos
        Set<Endereco> enderecos = (professorDTO.getEnderecos() != null) ? 
                professorDTO.getEnderecos().stream()
                .map(enderecoDTO -> Endereco.builder()
                        .cep(enderecoDTO.getCep())
                        .rua(enderecoDTO.getRua())
                        .numero(enderecoDTO.getNumero())
                        .bairro(enderecoDTO.getBairro())
                        .cidade(enderecoDTO.getCidade())
                        .estado(enderecoDTO.getEstado())
                        .build())
                .collect(Collectors.toSet()) : 
                Collections.emptySet();

        // Converte os telefones DTO para entidades, se fornecidos
        Set<Telefone> telefones = (professorDTO.getTelefones() != null) ?
                professorDTO.getTelefones().stream()
                .map(telefoneDTO -> Telefone.builder()
                        .ddd(telefoneDTO.getDdd())
                        .numero(telefoneDTO.getNumero())
                        .build())
                .collect(Collectors.toSet()) : 
                Collections.emptySet();

        // Cria o professor com os dados fornecidos
        Professor professor = Professor.builder()
                .cpf(professorDTO.getCpf())
                .nome(professorDTO.getNome())
                .ultimoNome(professorDTO.getUltimoNome())
                .genero(professorDTO.getGenero())
                .data_nascimento(professorDTO.getData_nascimento())  
                .email(professorDTO.getEmail())
                .coordenacao(coordenacao)
                .enderecos(enderecos)
                .telefones(telefones)
                .build();

        // Verifica e associa turmas e disciplinas, se fornecidos
        if (professorDTO.getTurmasDisciplinas() != null) {
            for (TurmaDisciplinaDTO tdDTO : professorDTO.getTurmasDisciplinas()) {
                // Verifica se a turma existe
                Turma turma = turmaRepository.findById(tdDTO.getTurmaId())
                        .orElseThrow(() -> new RuntimeException("Turma com ID " + tdDTO.getTurmaId() + " não encontrada"));

                // Verifica se a disciplina existe
                Disciplina disciplina = disciplinaRepository.findById(tdDTO.getDisciplinaId())
                        .orElseThrow(() -> new RuntimeException("Disciplina com ID " + tdDTO.getDisciplinaId() + " não encontrada"));

                // Cria a associação Turma-Disciplina-Professor
                TurmaDisciplinaProfessor turmaDisciplinaProfessor = new TurmaDisciplinaProfessor();
                turmaDisciplinaProfessor.setProfessor(professor);
                turmaDisciplinaProfessor.setTurma(turma);
                turmaDisciplinaProfessor.setDisciplina(disciplina);

                // Adiciona a associação ao professor
                professor.addTurmaDisciplinaProfessor(turmaDisciplinaProfessor);
            }
        }

        // Configura a relação bidirecional entre professor e endereços/telefones
        professor.getEnderecos().forEach(endereco -> endereco.setProfessor(professor));
        professor.getTelefones().forEach(telefone -> telefone.setProfessor(professor));

        // Salva o professor no banco de dados
        Professor savedProfessor = professorRepository.save(professor);
        return convertToDto(savedProfessor);
    }


    public ProfessorDTO updateProfessor(String cpf, ProfessorDTO professorDTO) {
        // Verifica se o professor existe
        Professor professor = professorRepository.findById(cpf)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        // Busca a coordenação pelo ID, se for atualizada
        Coordenacao coordenacao = coordenacaoRepository.findById(professorDTO.getCoordenacaoId())
                .orElseThrow(() -> new RuntimeException("Coordenação não encontrada"));

        // Atualiza os dados principais do professor
        professor.setNome(professorDTO.getNome());
        professor.setUltimoNome(professorDTO.getUltimoNome());
        professor.setGenero(professorDTO.getGenero());
        professor.setEmail(professorDTO.getEmail());
        professor.setData_nascimento(professorDTO.getData_nascimento());
        professor.setCoordenacao(coordenacao);

        // Atualiza os endereços (cria novos ou atualiza os existentes)
        for (EnderecoDTO enderecoDTO : professorDTO.getEnderecos()) {
            if (enderecoDTO.getIdEndereco() == null) {
                // Cria um novo endereço
                Endereco novoEndereco = Endereco.builder()
                        .cep(enderecoDTO.getCep())
                        .rua(enderecoDTO.getRua())
                        .numero(enderecoDTO.getNumero())
                        .bairro(enderecoDTO.getBairro())
                        .cidade(enderecoDTO.getCidade())
                        .estado(enderecoDTO.getEstado())
                        .professor(professor) // Relaciona com o professor
                        .build();
                professor.addEndereco(novoEndereco);
            } else {
                // Atualiza um endereço existente
                Endereco enderecoExistente = professor.getEnderecos().stream()
                        .filter(e -> e.getId_endereco().equals(enderecoDTO.getIdEndereco()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));

                enderecoExistente.setCep(enderecoDTO.getCep());
                enderecoExistente.setRua(enderecoDTO.getRua());
                enderecoExistente.setNumero(enderecoDTO.getNumero());
                enderecoExistente.setBairro(enderecoDTO.getBairro());
                enderecoExistente.setCidade(enderecoDTO.getCidade());
                enderecoExistente.setEstado(enderecoDTO.getEstado());
            }
        }

        // Atualiza os telefones (cria novos ou atualiza os existentes)
        for (TelefoneDTO telefoneDTO : professorDTO.getTelefones()) {
            if (telefoneDTO.getId() == null) {
                // Cria um novo telefone
                Telefone novoTelefone = Telefone.builder()
                        .ddd(telefoneDTO.getDdd())
                        .numero(telefoneDTO.getNumero())
                        .professor(professor) // Relaciona com o professor
                        .build();
                professor.addTelefone(novoTelefone);
            } else {
                // Atualiza um telefone existente
                Telefone telefoneExistente = professor.getTelefones().stream()
                        .filter(t -> t.getId().equals(telefoneDTO.getId()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Telefone não encontrado"));

                telefoneExistente.setDdd(telefoneDTO.getDdd());
                telefoneExistente.setNumero(telefoneDTO.getNumero());
            }
        }

        // Salva o professor atualizado
        Professor updatedProfessor = professorRepository.save(professor);

        // Retorna o DTO atualizado
        return convertToDto(updatedProfessor);
    }

    
    public List<ProfessorDTO> getAllProfessores() {
        return professorRepository.findAll().stream()
                .map(this::convertToDto)  // Converte cada professor para ProfessorDTO
                .collect(Collectors.toList());
    }
    
    public Optional<ProfessorDTO> getProfessorById(String cpf) {
        return professorRepository.findById(cpf)
                .map(this::convertToDto);  // Converte o professor para ProfessorDTO se encontrado
    }


    public List<Disciplina> getDisciplinasByProfessor(String professorId) {
        return turmaDisciplinaProfessorRepository.findById_ProfessorId(professorId)
                .stream()
                .map(turmaDisciplinaProfessor -> turmaDisciplinaProfessor.getDisciplina()) // Mapeia para obter as disciplinas
                .distinct()  // Remove disciplinas duplicadas
                .collect(Collectors.toList());
    }

    
    public void deleteProfessor(String cpf) {
        // Verifica se o professor existe no banco de dados
        Professor professor = professorRepository.findById(cpf)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
        
        // Deleta o professor encontrado
        professorRepository.delete(professor);
    }


 // Método para converter Professor para ProfessorDTO
    private ProfessorDTO convertToDto(Professor professor) {
        return ProfessorDTO.builder()
            .cpf(professor.getCpf())
            .nome(professor.getNome())
            .ultimoNome(professor.getUltimoNome())
            .genero(professor.getGenero())
            .data_nascimento(new java.sql.Date(professor.getData_nascimento().getTime()))  // Converte para java.sql.Date
            .email(professor.getEmail())
            .coordenacaoId(professor.getCoordenacao().getId())  // Somente o ID da coordenação
            .enderecos(professor.getEnderecos().stream()
                .map(endereco -> EnderecoDTO.builder()
                    .cep(endereco.getCep())
                    .rua(endereco.getRua())
                    .numero(endereco.getNumero())
                    .bairro(endereco.getBairro())
                    .cidade(endereco.getCidade())
                    .estado(endereco.getEstado())
                    .build())
                .collect(Collectors.toSet()))  // Certifique-se que o tipo de retorno é Set<EnderecoDTO>
            .telefones(professor.getTelefones().stream()
                .map(telefone -> TelefoneDTO.builder()
                    .ddd(telefone.getDdd())
                    .numero(telefone.getNumero())
                    .build())
                .collect(Collectors.toSet()))  // Certifique-se que o tipo de retorno é Set<TelefoneDTO>
            .build();
    }



}

package projeto.integrador3.senac.mediotec.pi3_mediotec.professor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.Coordenacao;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.CoordenadorResumidoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.Disciplina;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.DisciplinaRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.DisciplinaResumida2DTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.Endereco;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.EnderecoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.Telefone;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.TelefoneDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.Turma;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaResumidaDTO;
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
    public ProfessorResumidoDTO saveProfessor(ProfessorDTO professorDTO) {
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
        
     // Define status como true ao criar um novo professor 
        professor.setStatus(true); 
        
        
     // Verifica e associa turmas e disciplinas, se fornecidos
        if (professorDTO.getTurmasDisciplinas() != null) {
            professor.getTurmaDisciplinaProfessores().clear();

            // Use TurmaDisciplinaDTO aqui, e não TurmaDisciplinaResumidaDTO
            for (TurmaDisciplinaDTO tdDTO : professorDTO.getTurmasDisciplinas()) {
                Turma turma = turmaRepository.findById(tdDTO.getTurmaId())
                        .orElseThrow(() -> new RuntimeException("Turma com ID " + tdDTO.getTurmaId() + " não encontrada"));

                Disciplina disciplina = disciplinaRepository.findById(tdDTO.getDisciplinaId())
                        .orElseThrow(() -> new RuntimeException("Disciplina com ID " + tdDTO.getDisciplinaId() + " não encontrada"));

                TurmaDisciplinaProfessor turmaDisciplinaProfessor = new TurmaDisciplinaProfessor();
                turmaDisciplinaProfessor.setProfessor(professor);
                turmaDisciplinaProfessor.setTurma(turma);
                turmaDisciplinaProfessor.setDisciplina(disciplina);

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

    // Atualiza um professor existente
    public ProfessorResumidoDTO updateProfessor(String cpf, ProfessorDTO professorDTO) {
        // Verifica se o professor existe
        Professor professor = professorRepository.findById(cpf)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        // Atualiza os dados principais do professor
        professor.setNome(professorDTO.getNome());
        professor.setUltimoNome(professorDTO.getUltimoNome());
        professor.setGenero(professorDTO.getGenero());
        professor.setEmail(professorDTO.getEmail());
        professor.setData_nascimento(professorDTO.getData_nascimento());
        
        // Permite a alteração do status através do PUT
        professor.setStatus(professorDTO.isStatus());

        // Atualiza a coordenação, se fornecida
        if (professorDTO.getCoordenacaoId() != null) {
            Coordenacao coordenacao = coordenacaoRepository.findById(professorDTO.getCoordenacaoId())
                    .orElseThrow(() -> new RuntimeException("Coordenação não encontrada"));
            professor.setCoordenacao(coordenacao);
        }

        // Atualiza os endereços (cria novos ou atualiza os existentes)
        if (professorDTO.getEnderecos() != null) {
            for (EnderecoDTO enderecoDTO : professorDTO.getEnderecos()) {
                if (enderecoDTO.getIdEndereco() == null) {
                    Endereco novoEndereco = Endereco.builder()
                            .cep(enderecoDTO.getCep())
                            .rua(enderecoDTO.getRua())
                            .numero(enderecoDTO.getNumero())
                            .bairro(enderecoDTO.getBairro())
                            .cidade(enderecoDTO.getCidade())
                            .estado(enderecoDTO.getEstado())
                            .professor(professor)
                            .build();
                    professor.addEndereco(novoEndereco);
                } else {
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
        }

        // Atualiza os telefones (cria novos ou atualiza os existentes)
        if (professorDTO.getTelefones() != null) {
            for (TelefoneDTO telefoneDTO : professorDTO.getTelefones()) {
                if (telefoneDTO.getId() == null) {
                    Telefone novoTelefone = Telefone.builder()
                            .ddd(telefoneDTO.getDdd())
                            .numero(telefoneDTO.getNumero())
                            .professor(professor)
                            .build();
                    professor.addTelefone(novoTelefone);
                } else {
                    Telefone telefoneExistente = professor.getTelefones().stream()
                            .filter(t -> t.getId().equals(telefoneDTO.getId()))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Telefone não encontrado"));

                    telefoneExistente.setDdd(telefoneDTO.getDdd());
                    telefoneExistente.setNumero(telefoneDTO.getNumero());
                }
            }
        }

     
     // Verifica e associa turmas e disciplinas, se fornecidos
        if (professorDTO.getTurmasDisciplinas() != null) {
            professor.getTurmaDisciplinaProfessores().clear();

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


        Professor updatedProfessor = professorRepository.save(professor);

        return convertToDto(updatedProfessor);
    }

    public List<ProfessorResumidoDTO> getAllProfessores() {
        return professorRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public Optional<ProfessorResumidoDTO> getProfessorById(String cpf) {
        return professorRepository.findById(cpf)
                .map(this::convertToDto);
    }

    public List<Disciplina> getDisciplinasByProfessor(String professorId) {
        return turmaDisciplinaProfessorRepository.findById_ProfessorId(professorId)
                .stream()
                .map(turmaDisciplinaProfessor -> turmaDisciplinaProfessor.getDisciplina())
                .distinct()
                .collect(Collectors.toList());
    }

    public void deleteProfessor(String cpf) {
        Professor professor = professorRepository.findById(cpf)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
        
        professorRepository.delete(professor);
    }

    // Método para converter Professor para ProfessorResumidoDTO
    private ProfessorResumidoDTO convertToDto(Professor professor) {
        // Mapeia os coordenadores da coordenacao associada ao professor
        List<CoordenadorResumidoDTO> coordenadoresDTO = null;

        if (professor.getCoordenacao() != null) {
            // Se houver coordenadores associados à coordenacao, mapeie-os
            coordenadoresDTO = professor.getCoordenacao().getCoordenadores().stream()
                .map(coordenador -> CoordenadorResumidoDTO.builder()
                    .cpf(coordenador.getCpf())
                    .nomeCoordenador(coordenador.getNome() + " " + coordenador.getUltimoNome())
                    .email(coordenador.getEmail())
                    .build())
                .collect(Collectors.toList());
        }

        // Mapeia as turmas e disciplinas associadas ao professor
        Set<TurmaDisciplinaResumidaDTO> turmasDisciplinas = professor.getTurmaDisciplinaProfessores().stream()
            .map(tdp -> TurmaDisciplinaResumidaDTO.builder()
                .turma(TurmaResumidaDTO.builder()
                    .id(tdp.getTurma().getId())
                    .nome(tdp.getTurma().getNome())
                    .ano(tdp.getTurma().getAno())
                    .build())
                .disciplina(DisciplinaResumida2DTO.builder()
                    .id(tdp.getDisciplina().getId())
                    .nome(tdp.getDisciplina().getNome())
                    .build())
                .build())
            .collect(Collectors.toSet());

        // Retorna o ProfessorResumidoDTO preenchido com todos os dados
        return ProfessorResumidoDTO.builder()
            .cpf(professor.getCpf())
            .nome(professor.getNome())
            .ultimoNome(professor.getUltimoNome())
            .coordenadores(coordenadoresDTO) // Adiciona a lista de coordenadores
            .turmasDisciplinas(turmasDisciplinas) // Associa as turmas e disciplinas
            .build();
    }

}

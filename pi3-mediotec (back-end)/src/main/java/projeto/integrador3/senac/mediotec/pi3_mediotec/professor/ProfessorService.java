package projeto.integrador3.senac.mediotec.pi3_mediotec.professor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.Coordenacao;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoResumidaDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.CoordenadorResumidoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.Disciplina;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.DisciplinaRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.DisciplinaResumida2DTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.Endereco;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.EnderecoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.EnderecoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.Telefone;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.TelefoneDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.TelefoneRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.Turma;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaResumida2DTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaResumidaDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorId;
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
    private TurmaRepository turmaRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private TurmaDisciplinaProfessorRepository turmaDisciplinaProfessorRepository;
    
    @Autowired
    private TelefoneRepository telefoneRepository;
    
    @Autowired
    private EnderecoRepository enderecoRepository;

  
    public ProfessorResumidoDTO saveProfessor(ProfessorDTO professorDTO) {
        // Verifica se já existe um professor com o CPF fornecido
        if (professorRepository.existsByCpf(professorDTO.getCpf())) {
            throw new RuntimeException("Professor com CPF " + professorDTO.getCpf() + " já existe.");
        }

        // Associa a coordenação (opcional)
        Coordenacao coordenacao = null;
        if (professorDTO.getCoordenacaoId() != null) {
            coordenacao = coordenacaoRepository.findById(professorDTO.getCoordenacaoId())
                    .orElseThrow(() -> new RuntimeException("Coordenação não encontrada."));
        }

        // Cria o objeto professor
        Professor professor = Professor.builder()
                .cpf(professorDTO.getCpf())
                .nome(professorDTO.getNome())
                .ultimoNome(professorDTO.getUltimoNome())
                .genero(professorDTO.getGenero())
                .data_nascimento(professorDTO.getData_nascimento())
                .email(professorDTO.getEmail())
                .coordenacao(coordenacao)
                .status(true)  // Define status como true
                .build();

        // Salva o professor para garantir que o ID seja gerado
        Professor savedProfessor = professorRepository.save(professor);

        // Adiciona endereços ao professor (opcional)
        if (professorDTO.getEnderecos() != null && !professorDTO.getEnderecos().isEmpty()) {
            Set<Endereco> enderecos = professorDTO.getEnderecos().stream()
                    .map(enderecoDTO -> Endereco.builder()
                            .cep(enderecoDTO.getCep())
                            .rua(enderecoDTO.getRua())
                            .numero(enderecoDTO.getNumero())
                            .bairro(enderecoDTO.getBairro())
                            .cidade(enderecoDTO.getCidade())
                            .estado(enderecoDTO.getEstado())
                            .professor(savedProfessor)  // Associa o professor ao endereço
                            .build())
                    .collect(Collectors.toSet());

            enderecos.forEach(enderecoRepository::save);  // Persistir manualmente
            savedProfessor.setEnderecos(enderecos);
        }

        // Adiciona telefones ao professor (opcional)
        if (professorDTO.getTelefones() != null && !professorDTO.getTelefones().isEmpty()) {
            Set<Telefone> telefones = professorDTO.getTelefones().stream()
                    .map(telefoneDTO -> Telefone.builder()
                            .ddd(telefoneDTO.getDdd())
                            .numero(telefoneDTO.getNumero())
                            .professor(savedProfessor)  // Associa o professor ao telefone
                            .build())
                    .collect(Collectors.toSet());

            telefones.forEach(telefoneRepository::save);  // Persistir manualmente
            savedProfessor.setTelefones(telefones);
        }

        // Atualiza o professor no banco de dados com todos os relacionamentos
        Professor updatedProfessor = professorRepository.save(savedProfessor);

        // Retorna o ProfessorResumidoDTO
        return convertToDto(updatedProfessor);
    }



    public ProfessorResumidoDTO updateProfessor(String cpf, ProfessorDTO professorDTO) {
        // Busca o professor existente
        Professor professor = professorRepository.findById(cpf)
                .orElseThrow(() -> new RuntimeException("Professor com CPF " + cpf + " não encontrado"));

        // Atualiza os dados do professor
        professor.setNome(professorDTO.getNome());
        professor.setUltimoNome(professorDTO.getUltimoNome());
        professor.setGenero(professorDTO.getGenero());
        professor.setEmail(professorDTO.getEmail());
        professor.setData_nascimento(professorDTO.getData_nascimento());
        professor.setStatus(professorDTO.isStatus());

        // Atualiza a coordenação (opcional)
        if (professorDTO.getCoordenacaoId() != null) {
            Coordenacao coordenacao = coordenacaoRepository.findById(professorDTO.getCoordenacaoId())
                    .orElseThrow(() -> new RuntimeException("Coordenação com ID " + professorDTO.getCoordenacaoId() + " não encontrada"));
            professor.setCoordenacao(coordenacao);
        }

        // Atualiza endereços (opcional)
        if (professorDTO.getEnderecos() != null && !professorDTO.getEnderecos().isEmpty()) {
            professor.getEnderecos().clear();  // Remove os endereços antigos
            professorDTO.getEnderecos().forEach(enderecoDTO -> {
                Endereco endereco = Endereco.builder()
                        .cep(enderecoDTO.getCep())
                        .rua(enderecoDTO.getRua())
                        .numero(enderecoDTO.getNumero())
                        .bairro(enderecoDTO.getBairro())
                        .cidade(enderecoDTO.getCidade())
                        .estado(enderecoDTO.getEstado())
                        .build();
                professor.addEndereco(endereco);  // Associa o novo endereço ao professor
            });
        }

        // Atualiza telefones (opcional)
        if (professorDTO.getTelefones() != null && !professorDTO.getTelefones().isEmpty()) {
            professor.getTelefones().clear();  // Remove os telefones antigos
            professorDTO.getTelefones().forEach(telefoneDTO -> {
                Telefone telefone = Telefone.builder()
                        .ddd(telefoneDTO.getDdd())
                        .numero(telefoneDTO.getNumero())
                        .build();
                professor.addTelefone(telefone);  // Associa o novo telefone ao professor
            });
        }

        // Atualiza turmas e disciplinas (opcional)
        if (professorDTO.getTurmasDisciplinas() != null && !professorDTO.getTurmasDisciplinas().isEmpty()) {
            professor.getTurmaDisciplinaProfessores().clear();  // Remove as associações antigas
            professorDTO.getTurmasDisciplinas().forEach(turmaDisciplinaDTO -> {
                Turma turma = turmaRepository.findById(turmaDisciplinaDTO.getTurmaId())
                        .orElseThrow(() -> new RuntimeException("Turma com ID " + turmaDisciplinaDTO.getTurmaId() + " não encontrada"));
                Disciplina disciplina = disciplinaRepository.findById(turmaDisciplinaDTO.getDisciplinaId())
                        .orElseThrow(() -> new RuntimeException("Disciplina com ID " + turmaDisciplinaDTO.getDisciplinaId() + " não encontrada"));

                TurmaDisciplinaProfessor tdp = TurmaDisciplinaProfessor.builder()
                        .turma(turma)
                        .disciplina(disciplina)
                        .build();
                professor.addTurmaDisciplinaProfessor(tdp);  // Associa a nova turma e disciplina ao professor
            });
        }

        // Atualiza o professor no banco de dados com todos os relacionamentos
        Professor updatedProfessor = professorRepository.save(professor);

        // Retorna o ProfessorResumidoDTO
        return convertToDto(updatedProfessor);
    }

    // Deleta um professor pelo CPF
    public void deleteProfessor(String cpf) {
        Professor professor = professorRepository.findById(cpf)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado com o CPF: " + cpf));
        professorRepository.delete(professor);
    }

    // Busca todos os professores
    public List<ProfessorResumidoDTO> getAllProfessores() {
        return professorRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Busca um professor por CPF
    public Optional<ProfessorResumidoDTO> getProfessorById(String cpf) {
        return professorRepository.findById(cpf)
                .map(this::convertToDto);
    }

 // Método para converter Professor para ProfessorResumidoDTO
    private ProfessorResumidoDTO convertToDto(Professor professor) {
        // Mapeia os coordenadores da coordenação associada ao professor
        CoordenacaoResumidaDTO coordenacaoDTO = null;
        if (professor.getCoordenacao() != null) {
            Coordenacao coordenacao = professor.getCoordenacao();
            List<CoordenadorResumidoDTO> coordenadoresDTO = null;

            // Verifica se a coordenação tem coordenadores
            if (coordenacao.getCoordenadores() != null && !coordenacao.getCoordenadores().isEmpty()) {
                coordenadoresDTO = coordenacao.getCoordenadores().stream()
                    .map(coordenador -> CoordenadorResumidoDTO.builder()
                        .nomeCoordenador(coordenador.getNome() + " " + coordenador.getUltimoNome())
                        .email(coordenador.getEmail())
                        .build())
                    .collect(Collectors.toList());
            }

            // Constrói o DTO da coordenação
            coordenacaoDTO = CoordenacaoResumidaDTO.builder()
                .nome(coordenacao.getNome())
                .coordenadores(coordenadoresDTO != null ? coordenadoresDTO : null)  // Lista de coordenadores ou null
                .build();
        }

        // Mapeia as turmas e disciplinas associadas ao professor
        Set<TurmaDisciplinaResumidaDTO> turmasDisciplinas = professor.getTurmaDisciplinaProfessores() != null ? 
            professor.getTurmaDisciplinaProfessores().stream()
                .map(tdp -> TurmaDisciplinaResumidaDTO.builder()
                    .turma(TurmaResumida2DTO.builder()
                        .nome(tdp.getTurma().getNome())
                        .anoLetivo(tdp.getTurma().getAnoLetivo())
                        .anoEscolar(tdp.getTurma().getAnoEscolar())
                        .turno(tdp.getTurma().getTurno())
                        .build())
                    .disciplina(DisciplinaResumida2DTO.builder()
                        .nome(tdp.getDisciplina().getNome())
                        .build())
                    .build())
                .collect(Collectors.toSet()) : Collections.emptySet();

        // Mapeia os endereços do professor
        Set<EnderecoDTO> enderecosDTO = professor.getEnderecos() != null ? 
            professor.getEnderecos().stream()
                .map(endereco -> EnderecoDTO.builder()
                    .cep(endereco.getCep())
                    .rua(endereco.getRua())
                    .numero(endereco.getNumero())
                    .bairro(endereco.getBairro())
                    .cidade(endereco.getCidade())
                    .estado(endereco.getEstado())
                    .build())
                .collect(Collectors.toSet()) : Collections.emptySet();

        // Mapeia os telefones do professor
        Set<TelefoneDTO> telefonesDTO = professor.getTelefones() != null ? 
            professor.getTelefones().stream()
                .map(telefone -> TelefoneDTO.builder()
                    .ddd(telefone.getDdd())
                    .numero(telefone.getNumero())
                    .build())
                .collect(Collectors.toSet()) : Collections.emptySet();

        // Constrói o ProfessorResumidoDTO com todas as informações
        return ProfessorResumidoDTO.builder()
            .cpf(professor.getCpf())
            .nome(professor.getNome())
            .ultimoNome(professor.getUltimoNome())
            .email(professor.getEmail())
            .coordenacao(coordenacaoDTO)  // Coordenação resumida
            .turmasDisciplinas(!turmasDisciplinas.isEmpty() ? turmasDisciplinas : null)  // Turmas e disciplinas, ou null
            .enderecos(!enderecosDTO.isEmpty() ? enderecosDTO : null)  // Endereços, ou null
            .telefones(!telefonesDTO.isEmpty() ? telefonesDTO : null)  // Telefones, ou null
            .build();
    }


}

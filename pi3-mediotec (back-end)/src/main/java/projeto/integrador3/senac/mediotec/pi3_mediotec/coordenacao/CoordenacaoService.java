package projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.Coordenador;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.CoordenadorDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.CoordenadorRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.CoordenadorResumido2DTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.CoordenadorResumidoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.DisciplinaResumida2DTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.Endereco;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.EnderecoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.Professor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorResumido2DTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorResumidoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.TurmaDisciplinaResumidaDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.Telefone;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.TelefoneDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.DisciplinaProfessorDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.Turma;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaResumida2DTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaResumidaDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessor;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CoordenacaoService {

    @Autowired
    private CoordenacaoRepository coordenacaoRepository;

    @Autowired
    private CoordenadorRepository coordenadorRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    // Lista todas as coordenacoes
    public List<CoordenacaoDTO> getAllCoordenacoes() {
        return coordenacaoRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Busca coordenacao pelo id
    public Optional<CoordenacaoDTO> getCoordenacaoById(Long id) {
        return coordenacaoRepository.findById(id)
                .map(this::convertToDto);
    }

    @Transactional
    public CoordenacaoDTO saveCoordenacao(CoordenacaoCadastroDTO coordenacaoDTO) {
        Coordenacao coordenacao = new Coordenacao();
        coordenacao.setNome(coordenacaoDTO.getNome());
        coordenacao.setDescricao(coordenacaoDTO.getDescricao());

        // Adiciona e associa endereços à coordenacao
        if (coordenacaoDTO.getEnderecos() != null && !coordenacaoDTO.getEnderecos().isEmpty()) {
            coordenacao.getEnderecos().clear();
            coordenacaoDTO.getEnderecos().forEach(enderecoDTO -> {
                Endereco endereco = Endereco.builder()
                    .cep(enderecoDTO.getCep())
                    .rua(enderecoDTO.getRua())
                    .numero(enderecoDTO.getNumero())
                    .bairro(enderecoDTO.getBairro())
                    .cidade(enderecoDTO.getCidade())
                    .estado(enderecoDTO.getEstado())
                    .coordenacao(coordenacao)  // Associação bidirecional
                    .build();
                coordenacao.getEnderecos().add(endereco);
            });
        }

        // Adiciona e associa telefones à coordenacao
        if (coordenacaoDTO.getTelefones() != null && !coordenacaoDTO.getTelefones().isEmpty()) {
            coordenacao.getTelefones().clear();
            coordenacaoDTO.getTelefones().forEach(telefoneDTO -> {
                Telefone telefone = Telefone.builder()
                    .ddd(telefoneDTO.getDdd())
                    .numero(telefoneDTO.getNumero())
                    .coordenacao(coordenacao)  // Associação bidirecional
                    .build();
                coordenacao.getTelefones().add(telefone);
            });
        }

        // Associa coordenadores via IDs
        if (coordenacaoDTO.getCoordenadoresIds() != null && !coordenacaoDTO.getCoordenadoresIds().isEmpty()) {
            Set<Coordenador> coordenadores = coordenacaoDTO.getCoordenadoresIds().stream()
                .map(cpf -> coordenadorRepository.findById(cpf)
                    .orElseThrow(() -> new RuntimeException("Coordenador com CPF " + cpf + " não encontrado")))
                .collect(Collectors.toSet());

            // Faz a associação bidirecional
            coordenadores.forEach(coordenador -> coordenador.setCoordenacao(coordenacao));
            coordenacao.setCoordenadores(coordenadores);

            // Salva os coordenadores com a nova associação
            coordenadorRepository.saveAll(coordenadores);
        }

        // Associa turmas via IDs
        if (coordenacaoDTO.getTurmasIds() != null && !coordenacaoDTO.getTurmasIds().isEmpty()) {
            Set<Turma> turmas = coordenacaoDTO.getTurmasIds().stream()
                .map(id -> turmaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Turma com ID " + id + " não encontrada")))
                .collect(Collectors.toSet());

            // Faz a associação bidirecional
            turmas.forEach(turma -> turma.setCoordenacao(coordenacao));
            coordenacao.setTurmas(turmas);

            // Salva as turmas com a nova associação
            turmaRepository.saveAll(turmas);
        }

        // Associa professores via IDs
        if (coordenacaoDTO.getProfessoresIds() != null && !coordenacaoDTO.getProfessoresIds().isEmpty()) {
            Set<Professor> professores = coordenacaoDTO.getProfessoresIds().stream()
                .map(cpf -> professorRepository.findById(cpf)
                    .orElseThrow(() -> new RuntimeException("Professor com CPF " + cpf + " não encontrado")))
                .collect(Collectors.toSet());

            // Faz a associação bidirecional
            professores.forEach(professor -> professor.setCoordenacao(coordenacao));
            coordenacao.setProfessores(professores);

            // Salva os professores com a nova associação
            professorRepository.saveAll(professores);
        }

        // Salva a coordenacao e retorna o DTO
        Coordenacao savedCoordenacao = coordenacaoRepository.save(coordenacao);
        return convertToDto(savedCoordenacao);
    }

    @Transactional
    public CoordenacaoDTO updateCoordenacao(Long id, CoordenacaoCadastroDTO coordenacaoDTO) {
        Coordenacao coordenacao = coordenacaoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Coordenacao não encontrada"));

        coordenacao.setNome(coordenacaoDTO.getNome());
        coordenacao.setDescricao(coordenacaoDTO.getDescricao());

        // Atualiza endereços
        coordenacao.getEnderecos().clear();
        if (coordenacaoDTO.getEnderecos() != null && !coordenacaoDTO.getEnderecos().isEmpty()) {
            coordenacaoDTO.getEnderecos().forEach(enderecoDTO -> {
                Endereco endereco = Endereco.builder()
                    .cep(enderecoDTO.getCep())
                    .rua(enderecoDTO.getRua())
                    .numero(enderecoDTO.getNumero())
                    .bairro(enderecoDTO.getBairro())
                    .cidade(enderecoDTO.getCidade())
                    .estado(enderecoDTO.getEstado())
                    .coordenacao(coordenacao)  // Associação bidirecional
                    .build();
                coordenacao.getEnderecos().add(endereco);
            });
        }

        // Atualiza telefones
        coordenacao.getTelefones().clear();
        if (coordenacaoDTO.getTelefones() != null && !coordenacaoDTO.getTelefones().isEmpty()) {
            coordenacaoDTO.getTelefones().forEach(telefoneDTO -> {
                Telefone telefone = Telefone.builder()
                    .ddd(telefoneDTO.getDdd())
                    .numero(telefoneDTO.getNumero())
                    .coordenacao(coordenacao)  // Associação bidirecional
                    .build();
                coordenacao.getTelefones().add(telefone);
            });
        }

        // Atualiza coordenadores
        if (coordenacaoDTO.getCoordenadoresIds() != null && !coordenacaoDTO.getCoordenadoresIds().isEmpty()) {
            Set<Coordenador> coordenadores = coordenacaoDTO.getCoordenadoresIds().stream()
                .map(cpf -> coordenadorRepository.findById(cpf)
                    .orElseThrow(() -> new RuntimeException("Coordenador com CPF " + cpf + " não encontrado")))
                .collect(Collectors.toSet());

            // Faz a associação bidirecional
            coordenadores.forEach(coordenador -> coordenador.setCoordenacao(coordenacao));
            coordenacao.setCoordenadores(coordenadores);

            // Salva os coordenadores com a nova associação
            coordenadorRepository.saveAll(coordenadores);
        }

        // Atualiza turmas
        if (coordenacaoDTO.getTurmasIds() != null && !coordenacaoDTO.getTurmasIds().isEmpty()) {
            Set<Turma> turmas = coordenacaoDTO.getTurmasIds().stream()
                .map(turmaId -> turmaRepository.findById(turmaId)
                    .orElseThrow(() -> new RuntimeException("Turma com ID " + turmaId + " não encontrada")))
                .collect(Collectors.toSet());

            // Faz a associação bidirecional
            turmas.forEach(turma -> turma.setCoordenacao(coordenacao));
            coordenacao.setTurmas(turmas);

            // Salva as turmas com a nova associação
            turmaRepository.saveAll(turmas);
        }

        // Atualiza professores
        if (coordenacaoDTO.getProfessoresIds() != null && !coordenacaoDTO.getProfessoresIds().isEmpty()) {
            Set<Professor> professores = coordenacaoDTO.getProfessoresIds().stream()
                .map(cpf -> professorRepository.findById(cpf)
                    .orElseThrow(() -> new RuntimeException("Professor com CPF " + cpf + " não encontrado")))
                .collect(Collectors.toSet());

            // Faz a associação bidirecional
            professores.forEach(professor -> professor.setCoordenacao(coordenacao));
            coordenacao.setProfessores(professores);

            // Salva os professores com a nova associação
            professorRepository.saveAll(professores);
        }

        Coordenacao updatedCoordenacao = coordenacaoRepository.save(coordenacao);
        return convertToDto(updatedCoordenacao);
    }


    // Deleta coordenacao
    public void deleteCoordenacao(Long id) {
        Coordenacao coordenacao = coordenacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coordenacao não encontrada"));
        coordenacaoRepository.delete(coordenacao);
    }

    private CoordenacaoDTO convertToDto(Coordenacao coordenacao) {
        Set<EnderecoDTO> enderecosDTO = (coordenacao.getEnderecos() != null) ?
                coordenacao.getEnderecos().stream()
                        .map(endereco -> EnderecoDTO.builder()
                                .cep(endereco.getCep())
                                .rua(endereco.getRua())
                                .numero(endereco.getNumero())
                                .bairro(endereco.getBairro())
                                .cidade(endereco.getCidade())
                                .estado(endereco.getEstado())
                                .build())
                        .collect(Collectors.toSet()) : new HashSet<>();

        Set<TelefoneDTO> telefonesDTO = (coordenacao.getTelefones() != null) ?
                coordenacao.getTelefones().stream()
                        .map(telefone -> TelefoneDTO.builder()
                                .ddd(telefone.getDdd())
                                .numero(telefone.getNumero())
                                .build())
                        .collect(Collectors.toSet()) : new HashSet<>();

        List<CoordenadorResumido2DTO> coordenadoresDTO = (coordenacao.getCoordenadores() != null) ?
                coordenacao.getCoordenadores().stream()
                        .map(coordenador -> CoordenadorResumido2DTO.builder()
                                .cpf(coordenador.getCpf())
                                .nomeCoordenador(coordenador.getNome() + " " + coordenador.getUltimoNome())
                                .email(coordenador.getEmail())
                                .telefones(coordenador.getTelefones())
                                .build())
                        .collect(Collectors.toList()) : List.of();

        Set<TurmaResumida2DTO> turmasDTO = (coordenacao.getTurmas() != null) ?
                coordenacao.getTurmas().stream()
                        .map(turma -> TurmaResumida2DTO.builder()
                                .nome(turma.getNome())
                                .anoLetivo(turma.getAnoLetivo())
                                .anoEscolar(turma.getAnoEscolar())
                                .turno(turma.getTurno())
                                .build())
                        .collect(Collectors.toSet()) : new HashSet<>();

        Set<ProfessorResumido2DTO> professoresDTO = (coordenacao.getProfessores() != null) ?
                coordenacao.getProfessores().stream()
                        .map(professor -> ProfessorResumido2DTO.builder()
                                .cpf(professor.getCpf())
                                .nomeProfessor(professor.getNome() + " " + professor.getUltimoNome())
                                .email(professor.getEmail())
                                .telefones(professor.getTelefones())
                                .build())
                        .collect(Collectors.toSet()) : new HashSet<>();

        return CoordenacaoDTO.builder()
                .nome(coordenacao.getNome())
                .descricao(coordenacao.getDescricao())
                .enderecos(enderecosDTO)
                .telefones(telefonesDTO)
                .coordenadores(coordenadoresDTO)
                .turmas(turmasDTO)
                .professores(professoresDTO)
                .build();
    }
}

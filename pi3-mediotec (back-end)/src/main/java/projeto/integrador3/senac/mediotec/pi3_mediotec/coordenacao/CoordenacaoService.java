package projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    // Cria nova coordenacao
    public CoordenacaoDTO saveCoordenacao(CoordenacaoCadastroDTO coordenacaoDTO) {
        Coordenacao coordenacao = new Coordenacao();
        coordenacao.setNome(coordenacaoDTO.getNome());
        coordenacao.setDescricao(coordenacaoDTO.getDescricao());

        // Associa e cria endereços
        if (coordenacaoDTO.getEnderecos() != null) {
            Set<Endereco> enderecos = coordenacaoDTO.getEnderecos().stream()
                .map(enderecoDTO -> Endereco.builder()
                    .cep(enderecoDTO.getCep())
                    .rua(enderecoDTO.getRua())
                    .numero(enderecoDTO.getNumero())
                    .bairro(enderecoDTO.getBairro())
                    .cidade(enderecoDTO.getCidade())
                    .estado(enderecoDTO.getEstado())
                    .build())
                .collect(Collectors.toSet());
            coordenacao.setEnderecos(enderecos);
        }

        // Associa e cria telefones
        if (coordenacaoDTO.getTelefones() != null) {
            Set<Telefone> telefones = coordenacaoDTO.getTelefones().stream()
                .map(telefoneDTO -> Telefone.builder()
                    .ddd(telefoneDTO.getDdd())
                    .numero(telefoneDTO.getNumero())
                    .build())
                .collect(Collectors.toSet());
            coordenacao.setTelefones(telefones);
        }

     // Associa coordenadores via IDs
        if (coordenacaoDTO.getCoordenadoresIds() == null || coordenacaoDTO.getCoordenadoresIds().isEmpty()) {
            throw new RuntimeException("Pelo menos um coordenador deve ser informado.");
        } else {
            Set<Coordenador> coordenadores = coordenacaoDTO.getCoordenadoresIds().stream()
                .map(cpf -> coordenadorRepository.findById(cpf)
                    .orElseThrow(() -> new RuntimeException("Coordenador com CPF " + cpf + " não encontrado")))
                .collect(Collectors.toSet());
            coordenacao.setCoordenadores(coordenadores);
        }


        // Associa turmas via IDs
        if (coordenacaoDTO.getTurmasIds() != null) {
            Set<Turma> turmas = coordenacaoDTO.getTurmasIds().stream()
                .map(id -> turmaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Turma com ID " + id + " não encontrada")))
                .collect(Collectors.toSet());
            coordenacao.setTurmas(turmas);
        }

        // Associa professores via IDs
        if (coordenacaoDTO.getProfessoresIds() != null) {
            Set<Professor> professores = coordenacaoDTO.getProfessoresIds().stream()
                .map(cpf -> professorRepository.findById(cpf)
                    .orElseThrow(() -> new RuntimeException("Professor com CPF " + cpf + " não encontrado")))
                .collect(Collectors.toSet());
            coordenacao.setProfessores(professores);
        }

        // Salva a coordenacao e retorna o DTO
        Coordenacao savedCoordenacao = coordenacaoRepository.save(coordenacao);
        return convertToDto(savedCoordenacao);
    }



    public CoordenacaoDTO updateCoordenacao(Long id, CoordenacaoCadastroDTO coordenacaoDTO) {
        Coordenacao coordenacao = coordenacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coordenacao não encontrada"));

        coordenacao.setNome(coordenacaoDTO.getNome());
        coordenacao.setDescricao(coordenacaoDTO.getDescricao());

        // Atualiza os endereços e telefones completos
        if (coordenacaoDTO.getEnderecos() != null) {
            Set<Endereco> enderecos = coordenacaoDTO.getEnderecos().stream()
                .map(enderecoDTO -> Endereco.builder()
                    .cep(enderecoDTO.getCep())
                    .rua(enderecoDTO.getRua())
                    .numero(enderecoDTO.getNumero())
                    .bairro(enderecoDTO.getBairro())
                    .cidade(enderecoDTO.getCidade())
                    .estado(enderecoDTO.getEstado())
                    .build())
                .collect(Collectors.toSet());
            coordenacao.setEnderecos(enderecos);
        }

        if (coordenacaoDTO.getTelefones() != null) {
            Set<Telefone> telefones = coordenacaoDTO.getTelefones().stream()
                .map(telefoneDTO -> Telefone.builder()
                    .ddd(telefoneDTO.getDdd())
                    .numero(telefoneDTO.getNumero())
                    .build())
                .collect(Collectors.toSet());
            coordenacao.setTelefones(telefones);
        }

        // Atualiza os coordenadores via IDs
        if (coordenacaoDTO.getCoordenadoresIds() != null) {
            Set<Coordenador> coordenadores = coordenacaoDTO.getCoordenadoresIds().stream()
                .map(cpf -> coordenadorRepository.findById(cpf)
                    .orElseThrow(() -> new RuntimeException("Coordenador com CPF " + cpf + " não encontrado")))
                .collect(Collectors.toSet());
            coordenacao.setCoordenadores(coordenadores);
        }

        // Atualiza turmas via IDs
        if (coordenacaoDTO.getTurmasIds() != null) {
            Set<Turma> turmas = coordenacaoDTO.getTurmasIds().stream()
                .map(idTurma -> turmaRepository.findById(idTurma)
                    .orElseThrow(() -> new RuntimeException("Turma com ID " + idTurma + " não encontrada")))
                .collect(Collectors.toSet());
            coordenacao.setTurmas(turmas);
        }

        // Atualiza professores via IDs
        if (coordenacaoDTO.getProfessoresIds() != null) {
            Set<Professor> professores = coordenacaoDTO.getProfessoresIds().stream()
                .map(cpf -> professorRepository.findById(cpf)
                    .orElseThrow(() -> new RuntimeException("Professor com CPF " + cpf + " não encontrado")))
                .collect(Collectors.toSet());
            coordenacao.setProfessores(professores);
        }

        // Salva a coordenacao e retorna o DTO atualizado
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
        // Garante que os endereços não sejam nulos, evitando NullPointerException
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
                .collect(Collectors.toSet()) : Collections.emptySet();

        // Garante que os telefones não sejam nulos
        Set<TelefoneDTO> telefonesDTO = (coordenacao.getTelefones() != null) ?
            coordenacao.getTelefones().stream()
                .map(telefone -> TelefoneDTO.builder()
                    .ddd(telefone.getDdd())
                    .numero(telefone.getNumero())
                    .build())
                .collect(Collectors.toSet()) : Collections.emptySet();

        // Mapeia os coordenadores da coordenacao, garantindo que não sejam nulos
        List<CoordenadorResumido2DTO> coordenadoresDTO = (coordenacao.getCoordenadores() != null) ?
            coordenacao.getCoordenadores().stream()
                .map(coordenador -> CoordenadorResumido2DTO.builder()
                    .cpf(coordenador.getCpf())
                    .nomeCoordenador(coordenador.getNome() + " " + coordenador.getUltimoNome())
                    .email(coordenador.getEmail())
                    .telefones(coordenador.getTelefones())  // Assumindo que 'Coordenador' tem 'telefones'
                    .build())
                .collect(Collectors.toList()) : Collections.emptyList();

        // Garante que as turmas não sejam nulas
        Set<TurmaResumida2DTO> turmasDTO = (coordenacao.getTurmas() != null) ?
            coordenacao.getTurmas().stream()
                .map(turma -> TurmaResumida2DTO.builder()
                    .id(turma.getId())
                    .nome(turma.getNome())
                    .ano(turma.getAno())
                    .build())
                .collect(Collectors.toSet()) : Collections.emptySet();

        // Garante que os professores não sejam nulos
        Set<ProfessorResumido2DTO> professoresDTO = (coordenacao.getProfessores() != null) ?
            coordenacao.getProfessores().stream()
                .map(professor -> ProfessorResumido2DTO.builder()
                    .cpf(professor.getCpf())
                    .nomeProfessor(professor.getNome() + " " + professor.getUltimoNome())
                    .email(professor.getEmail())
                    .telefones(professor.getTelefones())  // Assumindo que 'Professor' tem 'telefones'
                    .build())
                .collect(Collectors.toSet()) : Collections.emptySet();

        // Converte a Coordenacao para CoordenacaoDTO sem redundâncias
        return CoordenacaoDTO.builder()
            .id(coordenacao.getId())
            .nome(coordenacao.getNome())
            .descricao(coordenacao.getDescricao())
            .enderecos(enderecosDTO)
            .telefones(telefonesDTO)
            .coordenadores(coordenadoresDTO)  // Usa CoordenadorResumido2DTO com telefones
            .turmas(turmasDTO)  // Adiciona turmas resumidas
            .professores(professoresDTO)  // Adiciona professores resumidos com telefones
            .build();
    }


}

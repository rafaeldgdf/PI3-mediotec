package projeto.integrador3.senac.mediotec.pi3_mediotec.aluno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.Coordenacao;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoResumidaDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.CoordenadorResumidoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.Endereco;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.EnderecoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.responsavel.Responsavel;
import projeto.integrador3.senac.mediotec.pi3_mediotec.responsavel.ResponsavelDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.Telefone;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.TelefoneDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.DisciplinaProfessorDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.Turma;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaResumidaDTO;

import java.util.Collections;
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

    // Lista todos os alunos com informações detalhadas (GET)
    public List<AlunoDTO> getAllAlunos() {
        return alunoRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Busca aluno pelo ID com informações detalhadas (GET)
    public Optional<AlunoDTO> getAlunoById(Long idAluno) {
        return alunoRepository.findById(idAluno)
                .map(this::convertToDto);
    }

    @Transactional  // Garante que o processo ocorra dentro de uma transação
    public AlunoDTO saveAluno(AlunoResumidoDTO2 alunoResumidoDTO) {

        // Verifica se já existe um aluno com o CPF fornecido
        if (alunoRepository.existsByCpf(alunoResumidoDTO.getCpf())) {
            throw new RuntimeException("Aluno com CPF " + alunoResumidoDTO.getCpf() + " já existe");
        }

        Aluno aluno = new Aluno();
        aluno.setNome(alunoResumidoDTO.getNome());
        aluno.setUltimoNome(alunoResumidoDTO.getUltimoNome());
        aluno.setGenero(alunoResumidoDTO.getGenero());
        aluno.setCpf(alunoResumidoDTO.getCpf());
        aluno.setEmail(alunoResumidoDTO.getEmail());
        aluno.setData_nascimento(alunoResumidoDTO.getData_nascimento());
        aluno.setStatus(true);

        // Associa endereços ao aluno (opcional)
        if (alunoResumidoDTO.getEnderecos() != null) {
            aluno.setEnderecos(alunoResumidoDTO.getEnderecos().stream()
                .map(enderecoDTO -> Endereco.builder()
                    .cep(enderecoDTO.getCep())
                    .rua(enderecoDTO.getRua())
                    .numero(enderecoDTO.getNumero())
                    .bairro(enderecoDTO.getBairro())
                    .cidade(enderecoDTO.getCidade())
                    .estado(enderecoDTO.getEstado())
                    .aluno(aluno)
                    .build())
                .collect(Collectors.toSet()));
        }

        // Associa telefones ao aluno (opcional)
        if (alunoResumidoDTO.getTelefones() != null) {
            aluno.setTelefones(alunoResumidoDTO.getTelefones().stream()
                .map(telefoneDTO -> Telefone.builder()
                    .ddd(telefoneDTO.getDdd())
                    .numero(telefoneDTO.getNumero())
                    .aluno(aluno)  // Associa o telefone ao aluno
                    .build())
                .collect(Collectors.toSet()));
        }

        // Associa turmas com base em IDs fornecidos (opcional)
        if (alunoResumidoDTO.getTurmasIds() != null) {
            Set<Turma> turmas = alunoResumidoDTO.getTurmasIds().stream()
                .map(turmaId -> turmaRepository.findById(turmaId)
                    .orElseThrow(() -> new RuntimeException("Turma com ID " + turmaId + " não encontrada")))
                .collect(Collectors.toSet());
            
            aluno.setTurmas(turmas);

            // Adiciona o aluno à turma (garante o mapeamento bidirecional)
            for (Turma turma : turmas) {
                turma.getAlunos().add(aluno);
            }
        }


        // **Primeiro, salva o aluno para garantir que o ID seja gerado**
        Aluno savedAluno = alunoRepository.save(aluno);

        // Verifica se pelo menos um Responsável foi fornecido
        if (alunoResumidoDTO.getResponsaveis() == null || alunoResumidoDTO.getResponsaveis().isEmpty()) {
            throw new RuntimeException("Pelo menos um responsável deve ser fornecido.");
        }

        // Associa os responsáveis e telefones ao aluno
        Set<Responsavel> responsaveis = alunoResumidoDTO.getResponsaveis().stream()
            .map(responsavelDTO -> {
                Responsavel responsavel = convertToResponsavel(responsavelDTO);
                responsavel.setAluno(savedAluno);  // Associa o aluno ao responsável

                // Associa os telefones ao responsável
                if (responsavelDTO.getTelefones() != null) {
                    Set<Telefone> telefones = responsavelDTO.getTelefones().stream()
                        .map(telefoneDTO -> Telefone.builder()
                            .ddd(telefoneDTO.getDdd())
                            .numero(telefoneDTO.getNumero())
                            .responsavel(responsavel)  // Associa o telefone ao responsável
                            .build())
                        .collect(Collectors.toSet());
                    responsavel.setTelefones(telefones);
                }

                return responsavel;
            })
            .collect(Collectors.toSet());

        // Atualiza os responsáveis para o aluno
        savedAluno.setResponsaveis(responsaveis);

        // **Salva os responsáveis e seus telefones no banco de dados**
        Aluno updatedAluno = alunoRepository.save(savedAluno);

        // Converte o aluno salvo em AlunoDTO completo e retorna
        return convertToDto(updatedAluno);
    }

    @Transactional  // Garante que o processo ocorra dentro de uma transação
    public AlunoDTO updateAluno(Long idAluno, AlunoResumidoDTO2 alunoResumidoDTO) {
        
        // Busca o aluno existente no banco de dados
        Aluno aluno = alunoRepository.findById(idAluno)
            .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        // Atualiza os dados básicos do aluno
        aluno.setNome(alunoResumidoDTO.getNome());
        aluno.setUltimoNome(alunoResumidoDTO.getUltimoNome());
        aluno.setGenero(alunoResumidoDTO.getGenero());
        aluno.setCpf(alunoResumidoDTO.getCpf());
        aluno.setEmail(alunoResumidoDTO.getEmail());
        aluno.setData_nascimento(alunoResumidoDTO.getData_nascimento());
        aluno.setStatus(true);

        // Atualiza endereços (opcional)
        if (alunoResumidoDTO.getEnderecos() != null) {
            aluno.setEnderecos(alunoResumidoDTO.getEnderecos().stream()
                .map(enderecoDTO -> Endereco.builder()
                    .cep(enderecoDTO.getCep())
                    .rua(enderecoDTO.getRua())
                    .numero(enderecoDTO.getNumero())
                    .bairro(enderecoDTO.getBairro())
                    .cidade(enderecoDTO.getCidade())
                    .estado(enderecoDTO.getEstado())
                    .aluno(aluno)  // Atualiza a referência bidirecional do aluno para o endereço
                    .build())
                .collect(Collectors.toSet()));
        }

        // Atualiza telefones (opcional)
        if (alunoResumidoDTO.getTelefones() != null) {
            aluno.setTelefones(alunoResumidoDTO.getTelefones().stream()
                .map(telefoneDTO -> Telefone.builder()
                    .ddd(telefoneDTO.getDdd())
                    .numero(telefoneDTO.getNumero())
                    .aluno(aluno)  // Atualiza a referência bidirecional do aluno para o telefone
                    .build())
                .collect(Collectors.toSet()));
        }

        // Atualiza turmas com base nos IDs fornecidos (opcional)
        if (alunoResumidoDTO.getTurmasIds() != null) {
            Set<Turma> turmas = alunoResumidoDTO.getTurmasIds().stream()
                .map(turmaId -> turmaRepository.findById(turmaId)
                    .orElseThrow(() -> new RuntimeException("Turma com ID " + turmaId + " não encontrada")))
                .collect(Collectors.toSet());

            aluno.setTurmas(turmas);

            // Adiciona o aluno à turma (garante o mapeamento bidirecional)
            for (Turma turma : turmas) {
                turma.getAlunos().add(aluno);
            }
        }

        // Atualiza responsáveis (obrigatório)
        if (alunoResumidoDTO.getResponsaveis() == null || alunoResumidoDTO.getResponsaveis().isEmpty()) {
            throw new RuntimeException("Pelo menos um responsável deve ser fornecido.");
        }

        Set<Responsavel> responsaveis = alunoResumidoDTO.getResponsaveis().stream()
            .map(responsavelDTO -> {
                Responsavel responsavel = convertToResponsavel(responsavelDTO);
                responsavel.setAluno(aluno);  // Atualiza a referência bidirecional do aluno para o responsável

                // Atualiza os telefones do responsável
                if (responsavelDTO.getTelefones() != null) {
                    Set<Telefone> telefones = responsavelDTO.getTelefones().stream()
                        .map(telefoneDTO -> Telefone.builder()
                            .ddd(telefoneDTO.getDdd())
                            .numero(telefoneDTO.getNumero())
                            .responsavel(responsavel)  // Atualiza a referência bidirecional do responsável para o telefone
                            .build())
                        .collect(Collectors.toSet());
                    responsavel.setTelefones(telefones);
                }

                return responsavel;
            })
            .collect(Collectors.toSet());

        // Atualiza os responsáveis para o aluno
        aluno.setResponsaveis(responsaveis);

        // Salva o aluno atualizado
        Aluno updatedAluno = alunoRepository.save(aluno);

        // Converte o aluno atualizado em AlunoDTO completo e retorna
        return convertToDto(updatedAluno);
    }


    // Converte de ResponsavelDTO para Responsavel
    private Responsavel convertToResponsavel(ResponsavelDTO responsavelDTO) {
        if (responsavelDTO.getNome() == null || responsavelDTO.getUltimoNome() == null ||
            responsavelDTO.getCpfResponsavel() == null || responsavelDTO.getGrauParentesco() == null) {
            throw new RuntimeException("Dados do responsável estão incompletos. Nome, sobrenome, CPF e grau de parentesco são obrigatórios.");
        }

        Responsavel responsavel = new Responsavel();
        responsavel.setNome(responsavelDTO.getNome());
        responsavel.setUltimoNome(responsavelDTO.getUltimoNome());
        responsavel.setCpfResponsavel(responsavelDTO.getCpfResponsavel());
        responsavel.setGrauParentesco(responsavelDTO.getGrauParentesco());

        // Telefones opcionais
        if (responsavelDTO.getTelefones() != null) {
            Set<Telefone> telefones = responsavelDTO.getTelefones().stream()
                .map(telefoneDTO -> Telefone.builder()
                    .ddd(telefoneDTO.getDdd())
                    .numero(telefoneDTO.getNumero())
                    .responsavel(responsavel)
                    .build())
                .collect(Collectors.toSet());
            responsavel.setTelefones(telefones);
        }

        return responsavel;
    }

    // Deleta um aluno pelo ID
    public void deleteAluno(Long idAluno) {
        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com o ID: " + idAluno));
        alunoRepository.delete(aluno);
    }

    // Converte de Responsavel para ResponsavelDTO
    private ResponsavelDTO convertToResponsavelDTO(Responsavel responsavel) {
        Set<TelefoneDTO> telefonesDTO = responsavel.getTelefones() != null ?
                responsavel.getTelefones().stream()
                .map(telefone -> TelefoneDTO.builder()
                    .ddd(telefone.getDdd())
                    .numero(telefone.getNumero())
                    .build())
                .collect(Collectors.toSet()) : Collections.emptySet();

        return ResponsavelDTO.builder()
            .nome(responsavel.getNome())
            .ultimoNome(responsavel.getUltimoNome())
            .cpfResponsavel(responsavel.getCpfResponsavel())
            .telefones(telefonesDTO)
            .grauParentesco(responsavel.getGrauParentesco())
            .build();
    }

 // Conversão de Aluno para AlunoDTO (completo para GET)
    private AlunoDTO convertToDto(Aluno aluno) {
        // Mapeia os endereços do aluno
        Set<EnderecoDTO> enderecosDTO = aluno.getEnderecos() != null ? aluno.getEnderecos().stream()
            .map(endereco -> EnderecoDTO.builder()
                .cep(endereco.getCep())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .bairro(endereco.getBairro())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .build())
            .collect(Collectors.toSet()) : Collections.emptySet();

        // Mapeia os telefones do aluno
        Set<TelefoneDTO> telefonesDTO = aluno.getTelefones() != null ? aluno.getTelefones().stream()
            .map(telefone -> TelefoneDTO.builder()
                .ddd(telefone.getDdd())
                .numero(telefone.getNumero())
                .build())
            .collect(Collectors.toSet()) : Collections.emptySet();

        // Mapeia os responsáveis do aluno
        Set<ResponsavelDTO> responsaveisDTO = aluno.getResponsaveis() != null ? aluno.getResponsaveis().stream()
            .map(responsavel -> ResponsavelDTO.builder()
                .nome(responsavel.getNome())
                .ultimoNome(responsavel.getUltimoNome())
                .cpfResponsavel(responsavel.getCpfResponsavel())
                .grauParentesco(responsavel.getGrauParentesco())
                .telefones(responsavel.getTelefones() != null ? responsavel.getTelefones().stream()
                    .map(telefone -> TelefoneDTO.builder()
                        .ddd(telefone.getDdd())
                        .numero(telefone.getNumero())
                        .build())
                    .collect(Collectors.toSet()) : Collections.emptySet())
                .build())
            .collect(Collectors.toSet()) : Collections.emptySet();

        // Mapeia as turmas associadas ao aluno, incluindo coordenação e disciplinas
        Set<TurmaResumidaDTO> turmasDTO = aluno.getTurmas() != null ? aluno.getTurmas().stream()
            .map(turma -> {
                CoordenacaoResumidaDTO coordenacaoDTO = null;
                Coordenacao coordenacao = turma.getCoordenacao();

                // Verifica se a turma tem coordenação e coordenadores
                List<CoordenadorResumidoDTO> coordenadoresDTO = null;
                if (coordenacao != null && coordenacao.getCoordenadores() != null) {
                    coordenadoresDTO = coordenacao.getCoordenadores().stream()
                        .map(coordenador -> CoordenadorResumidoDTO.builder()
                            .nomeCoordenador(coordenador.getNome() + " " + coordenador.getUltimoNome())
                            .email(coordenador.getEmail())
                            .build())
                        .collect(Collectors.toList());

                    coordenacaoDTO = CoordenacaoResumidaDTO.builder()
                        .nome(coordenacao.getNome())
                        .coordenadores(!coordenadoresDTO.isEmpty() ? coordenadoresDTO : null)
                        .build();
                }

                // Mapeia as disciplinas e professores da turma
                Set<DisciplinaProfessorDTO> disciplinaProfessorDTO = turma.getTurmaDisciplinaProfessores() != null ?
                    turma.getTurmaDisciplinaProfessores().stream()
                        .map(turmaDisciplinaProfessor -> DisciplinaProfessorDTO.builder()
                            .professorId(turmaDisciplinaProfessor.getProfessor().getCpf())
                            .nomeProfessor(turmaDisciplinaProfessor.getProfessor().getNome() + " " +
                                           turmaDisciplinaProfessor.getProfessor().getUltimoNome())
                            .email(turmaDisciplinaProfessor.getProfessor().getEmail())
                            .nomesDisciplinas(Set.of(turmaDisciplinaProfessor.getDisciplina().getNome()))
                            .build())
                        .collect(Collectors.toSet()) : Collections.emptySet();

                return TurmaResumidaDTO.builder()
                    .nome(turma.getNome())
                    .anoLetivo(turma.getAnoLetivo())
                    .anoEscolar(turma.getAnoEscolar())
                    .turno(turma.getTurno())
                    .coordenacao(coordenacaoDTO)
                    .disciplinaProfessores(!disciplinaProfessorDTO.isEmpty() ? disciplinaProfessorDTO : null)
                    .build();
            })
            .collect(Collectors.toSet()) : Collections.emptySet();

        // Converte o Aluno para AlunoDTO com todos os campos
        return AlunoDTO.builder()
            .id(aluno.getId())
            .nome(aluno.getNome())
            .ultimoNome(aluno.getUltimoNome())
            .genero(aluno.getGenero())
            .cpf(aluno.getCpf())
            .email(aluno.getEmail())
            .data_nascimento(aluno.getData_nascimento())
            .status(aluno.isStatus())
            .enderecos(!enderecosDTO.isEmpty() ? enderecosDTO : null)
            .telefones(!telefonesDTO.isEmpty() ? telefonesDTO : null)
            .responsaveis(!responsaveisDTO.isEmpty() ? responsaveisDTO : null)
            .turmas(!turmasDTO.isEmpty() ? turmasDTO : null)
            .build();
    }

}
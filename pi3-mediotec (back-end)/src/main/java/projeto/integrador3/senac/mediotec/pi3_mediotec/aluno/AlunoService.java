package projeto.integrador3.senac.mediotec.pi3_mediotec.aluno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.Coordenacao;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoResumidaDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.CoordenadorResumidoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.Endereco;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.Telefone;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.DisciplinaProfessorDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.Turma;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaResumidaDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessor;

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
                .map(this::convertToDto)  // Converte para AlunoDTO completo
                .collect(Collectors.toList());
    }

    // Busca aluno pelo ID com informações detalhadas (GET)
    public Optional<AlunoDTO> getAlunoById(Long idAluno) {
        return alunoRepository.findById(idAluno)
                .map(this::convertToDto);  // Converte para AlunoDTO completo
    }

 // Cria um novo aluno (POST) usando AlunoResumidoDTO2
    public AlunoDTO saveAluno(AlunoResumidoDTO2 alunoResumidoDTO) {
        // Verifica se já existe um aluno com o CPF fornecido
        if (alunoRepository.existsByCpf(alunoResumidoDTO.getCpf())) {
            throw new RuntimeException("Aluno com CPF " + alunoResumidoDTO.getCpf() + " já existe");
        }

        // Criação de novo Aluno
        Aluno aluno = new Aluno();
        aluno.setNome(alunoResumidoDTO.getNome());
        aluno.setUltimoNome(alunoResumidoDTO.getUltimoNome());
        aluno.setGenero(alunoResumidoDTO.getGenero());
        aluno.setCpf(alunoResumidoDTO.getCpf());
        aluno.setEmail(alunoResumidoDTO.getEmail());
        
        
        // Define status como true ao criar um novo professor 
        aluno.setStatus(true); 
        

        // Associa endereços ao aluno
        aluno.setEnderecos(alunoResumidoDTO.getEnderecos().stream()
            .map(enderecoDTO -> Endereco.builder()
                    .cep(enderecoDTO.getCep())
                    .rua(enderecoDTO.getRua())
                    .numero(enderecoDTO.getNumero())
                    .bairro(enderecoDTO.getBairro())
                    .cidade(enderecoDTO.getCidade())
                    .estado(enderecoDTO.getEstado())
                    .build())
            .collect(Collectors.toSet()));

        // Associa telefones ao aluno
        aluno.setTelefones(alunoResumidoDTO.getTelefones().stream()
            .map(telefoneDTO -> Telefone.builder()
                    .ddd(telefoneDTO.getDdd())
                    .numero(telefoneDTO.getNumero())
                    .build())
            .collect(Collectors.toSet()));

        // Associa turmas com base em IDs fornecidos
        if (alunoResumidoDTO.getTurmasIds() != null) {
            Set<Turma> turmas = alunoResumidoDTO.getTurmasIds().stream()
                .map(turmaId -> turmaRepository.findById(turmaId)
                        .orElseThrow(() -> new RuntimeException("Turma com ID " + turmaId + " não encontrada")))
                .collect(Collectors.toSet());
            aluno.setTurmas(turmas);
        }

        // Salva o aluno no repositório
        Aluno savedAluno = alunoRepository.save(aluno);

        // Converte o aluno salvo em AlunoDTO completo e retorna
        return convertToDto(savedAluno);  
    }


    // Atualiza um aluno existente (PUT) usando AlunoResumidoDTO2
    public AlunoDTO updateAluno(Long idAluno, AlunoDTO alunoDTO) {
        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        aluno.setNome(alunoDTO.getNome());
        aluno.setUltimoNome(alunoDTO.getUltimoNome());
        aluno.setGenero(alunoDTO.getGenero());
        aluno.setCpf(alunoDTO.getCpf());
        aluno.setEmail(alunoDTO.getEmail());
        
        // Permite a alteração do status através do PUT
        aluno.setStatus(alunoDTO.isStatus());

        // Atualizar endereços
        aluno.setEnderecos(alunoDTO.getEnderecos().stream()
            .map(enderecoDTO -> Endereco.builder()
                .cep(enderecoDTO.getCep())
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .bairro(enderecoDTO.getBairro())
                .cidade(enderecoDTO.getCidade())
                .estado(enderecoDTO.getEstado())
                .build())
            .collect(Collectors.toSet()));

        // Atualizar telefones
        aluno.setTelefones(alunoDTO.getTelefones().stream()
            .map(telefoneDTO -> Telefone.builder()
                .ddd(telefoneDTO.getDdd())
                .numero(telefoneDTO.getNumero())
                .build())
            .collect(Collectors.toSet()));

        // Atualizar turmas
        Set<Turma> turmas = alunoDTO.getTurmas().stream()
            .map(turmaDTO -> turmaRepository.findById(turmaDTO.getId())
                    .orElseThrow(() -> new RuntimeException("Turma com ID " + turmaDTO.getId() + " não encontrada.")))
            .collect(Collectors.toSet());
        aluno.setTurmas(turmas);

        Aluno updatedAluno = alunoRepository.save(aluno);
        return convertToDto(updatedAluno);
    }
    
    
 // Deleta um aluno
    public void deleteAluno(Long idAluno) {
        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        alunoRepository.delete(aluno);
    }



 // Conversão de Aluno para AlunoDTO (completo para GET)
    private AlunoDTO convertToDto(Aluno aluno) {
        // Mapeia as turmas do aluno para TurmaResumidaDTO
        Set<TurmaResumidaDTO> turmasDTO = aluno.getTurmas() != null ? aluno.getTurmas().stream()
            .map((Turma turma) -> {
                CoordenacaoResumidaDTO coordenacaoDTO = null;
                Coordenacao coordenacao = turma.getCoordenacao();
                
                // Verifica se a turma tem uma coordenação e coordenadores
                List<CoordenadorResumidoDTO> coordenadoresDTO = null;
                if (coordenacao != null && coordenacao.getCoordenadores() != null) {
                    // Mapeia a lista de coordenadores, se houver
                    coordenadoresDTO = coordenacao.getCoordenadores().stream()
                        .map(coordenador -> CoordenadorResumidoDTO.builder()
                            .cpf(coordenador.getCpf())
                            .nomeCoordenador(coordenador.getNome() + " " + coordenador.getUltimoNome())
                            .email(coordenador.getEmail())
                            .build())
                        .collect(Collectors.toList());

                    // Constroi o CoordenacaoResumidaDTO
                    coordenacaoDTO = CoordenacaoResumidaDTO.builder()
                        .id(coordenacao.getId())
                        .nome(coordenacao.getNome())
                        .coordenadores(!coordenadoresDTO.isEmpty() ? coordenadoresDTO : null) // Lista de coordenadores ou nulo se vazia
                        .build();
                }

                // Mapeia as disciplinas e professores associados à turma
                Set<DisciplinaProfessorDTO> disciplinaProfessorDTO = turma.getTurmaDisciplinaProfessores() != null ?
                    turma.getTurmaDisciplinaProfessores().stream()
                        .map((TurmaDisciplinaProfessor turmaDisciplinaProfessor) -> DisciplinaProfessorDTO.builder()
                            .professorId(turmaDisciplinaProfessor.getProfessor().getCpf())
                            .nomeProfessor(turmaDisciplinaProfessor.getProfessor().getNome() + " " +
                                           turmaDisciplinaProfessor.getProfessor().getUltimoNome())
                            .email(turmaDisciplinaProfessor.getProfessor().getEmail())
                            .nomesDisciplinas(Set.of(turmaDisciplinaProfessor.getDisciplina().getNome())) // Considera uma disciplina por professor
                            .build())
                        .collect(Collectors.toSet()) : Collections.emptySet();  // Se não houver disciplinas, retorna um conjunto vazio

                return TurmaResumidaDTO.builder()
                    .id(turma.getId())
                    .nome(turma.getNome())
                    .ano(turma.getAno())
                    .coordenacao(coordenacaoDTO)
                    .disciplinaProfessores(!disciplinaProfessorDTO.isEmpty() ? disciplinaProfessorDTO : null)  // Se vazio, retorna null
                    .build();
            })
            .collect(Collectors.toSet()) : Collections.emptySet();  // Se o aluno não tiver turmas, retorna um conjunto vazio

        // Converte o Aluno para AlunoDTO com todos os campos
        return AlunoDTO.builder()
            .id(aluno.getId())
            .nome(aluno.getNome())
            .ultimoNome(aluno.getUltimoNome())
            .genero(aluno.getGenero())
            .cpf(aluno.getCpf())
            .email(aluno.getEmail())
            .turmas(!turmasDTO.isEmpty() ? turmasDTO : null)  // Se não houver turmas, retorna null
            .build();
    }



}

package projeto.integrador3.senac.mediotec.pi3_mediotec.professor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import projeto.integrador3.senac.mediotec.pi3_mediotec.conceito.ConceitoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.conceito.ConceitoInputDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.conceito.ConceitoResumidoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.conceito.ConceitoService;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.Disciplina;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorResumidoDTO;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/professores")
@Tag(name = "Professor", description = "Operações relacionadas aos professores")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private ConceitoService conceitoService; // Serviço de Conceito

    // Método GET para listar todos os professores
    @Operation(summary = "Listar todos os professores", description = "Retorna uma lista de todos os professores cadastrados")
    @GetMapping
    public ResponseEntity<List<ProfessorResumidoDTO>> getAllProfessores() {
        try {
            List<ProfessorResumidoDTO> professores = professorService.getAllProfessores();
            if (professores.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum professor encontrado.");
            }
            return new ResponseEntity<>(professores, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar professores: " + e.getMessage(), e);
        }
    }

    // Método GET para buscar professor por CPF
    @Operation(summary = "Buscar professor por CPF", description = "Retorna um professor com base no seu CPF")
    @GetMapping("/{cpf}")
    public ResponseEntity<ProfessorResumidoDTO> getProfessorById(@PathVariable String cpf) {
        try {
            Optional<ProfessorResumidoDTO> professor = professorService.getProfessorById(cpf);
            return professor.map(ResponseEntity::ok)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado com o CPF: " + cpf));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar professor com CPF: " + cpf, e);
        }
    }

    // Método POST para criar um novo professor
    @Operation(summary = "Criar um novo professor", description = "Cria um novo professor com base nos dados fornecidos")
    @PostMapping
    public ResponseEntity<ProfessorResumidoDTO> createProfessor(@RequestBody ProfessorDTO professorDTO) {
        try {
            ProfessorResumidoDTO savedProfessor = professorService.saveProfessor(professorDTO);
            return new ResponseEntity<>(savedProfessor, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao criar professor: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao processar a criação do professor", e);
        }
    }

    // Método PUT para atualizar um professor existente
    @Operation(summary = "Atualizar professor", description = "Atualiza um professor existente com base no CPF fornecido")
    @PutMapping("/{cpf}")
    public ResponseEntity<ProfessorResumidoDTO> updateProfessor(@PathVariable String cpf, @RequestBody ProfessorDTO professorDTO) {
        try {
            ProfessorResumidoDTO updatedProfessor = professorService.updateProfessor(cpf, professorDTO);
            return new ResponseEntity<>(updatedProfessor, HttpStatus.OK);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado com o CPF: " + cpf, e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao atualizar o professor com CPF: " + cpf, e);
        }
    }

    // Método DELETE para excluir um professor por CPF
    @Operation(summary = "Deletar professor", description = "Deleta um professor com base no CPF fornecido")
    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deleteProfessor(@PathVariable String cpf) {
        try {
            professorService.deleteProfessor(cpf);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Sucesso - 204
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado com o CPF: " + cpf, e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao deletar professor com CPF: " + cpf, e);
        }
    }

//Adicionar conceito a um aluno em uma disciplina com o ID da turma (POST)
    @Operation(summary = "Adicionar conceito a um aluno em uma disciplina e turma específica", 
               description = "Adiciona um conceito para um aluno em uma disciplina e turma específicas. Essa é a forma recomendada.")
    @PostMapping("/{idProfessor}/aluno/{idAluno}/disciplina/{idDisciplina}/turma/{idTurma}/conceitos")
    public ResponseEntity<ConceitoDTO> adicionarConceitoParaAlunoComTurma(
            @PathVariable String idProfessor,
            @PathVariable Long idAluno,
            @PathVariable Long idDisciplina,
            @PathVariable Long idTurma,
            @RequestBody ConceitoInputDTO conceitoInputDTO) {
        try {
            ConceitoDTO novoConceito = conceitoService.salvarConceitoParaAlunoComTurma(idProfessor, idAluno, idDisciplina, idTurma, conceitoInputDTO);
            return new ResponseEntity<>(novoConceito, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao adicionar conceito: " + e.getMessage(), e);
        }
    }


 //Atualizar conceito de um aluno em uma disciplina com o ID da turma (PUT)
    @Operation(summary = "Atualizar conceito de um aluno em uma disciplina e turma específica", 
               description = "Atualiza um conceito existente para um aluno em uma disciplina e turma específicas. Essa é a forma recomendada.")
    @PutMapping("/{idProfessor}/aluno/{idAluno}/disciplina/{idDisciplina}/turma/{idTurma}/conceitos/{idConceito}")
    public ResponseEntity<ConceitoDTO> atualizarConceitoParaAlunoComTurma(
            @PathVariable String idProfessor,
            @PathVariable Long idAluno,
            @PathVariable Long idDisciplina,
            @PathVariable Long idTurma,
            @PathVariable Long idConceito,
            @RequestBody ConceitoInputDTO conceitoInputDTO) {
        try {
            ConceitoDTO conceitoAtualizado = conceitoService.atualizarConceitoParaAlunoComTurma(idProfessor, idAluno, idDisciplina, idTurma, idConceito, conceitoInputDTO);
            return new ResponseEntity<>(conceitoAtualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao atualizar conceito: " + e.getMessage(), e);
        }
    }

    
 // Rota para visualizar as notas de todos os alunos em uma turma específica
    @Operation(summary = "Visualizar notas de todos os alunos de uma turma", 
               description = "Retorna as notas de todos os alunos de uma turma específica em uma disciplina.")
    @GetMapping("/{idProfessor}/disciplina/{idDisciplina}/turma/{idTurma}/conceitos")
    public ResponseEntity<List<ConceitoDTO>> getConceitosPorTurma(
            @PathVariable String idProfessor,
            @PathVariable Long idDisciplina,
            @PathVariable Long idTurma) {
        try {
            List<ConceitoDTO> conceitos = conceitoService.getConceitosPorTurma(idProfessor, idDisciplina, idTurma);
            if (conceitos.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum conceito encontrado para essa turma.");
            }
            return new ResponseEntity<>(conceitos, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar conceitos", e);
        }
    }
    
    
 // Rota para visualizar as notas de um aluno em uma disciplina e turma específicas
    @Operation(summary = "Visualizar nota de um aluno", 
               description = "Retorna as notas de um aluno específico em uma disciplina e turma.")
    @GetMapping("/{idProfessor}/aluno/{idAluno}/disciplina/{idDisciplina}/turma/{idTurma}/conceitos")
    public ResponseEntity<ConceitoDTO> getConceitoPorAluno(
            @PathVariable String idProfessor,
            @PathVariable Long idAluno,
            @PathVariable Long idDisciplina,
            @PathVariable Long idTurma) {
        try {
            ConceitoDTO conceito = conceitoService.getConceitoPorAluno(idProfessor, idAluno, idDisciplina, idTurma);
            return ResponseEntity.ok(conceito);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao buscar conceito para o aluno: " + e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar conceito", e);
        }
    }


}


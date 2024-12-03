package projeto.integrador3.senac.mediotec.pi3_mediotec.turma;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;

import projeto.integrador3.senac.mediotec.pi3_mediotec.arquivo.Arquivo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turmas")
@Tag(name = "Turma", description = "Operações relacionadas às turmas")
public class TurmaController {

    @Autowired
    private TurmaService turmaService;

    // ============================= GET METHODS =============================

    /**
     * Lista todas as turmas cadastradas.
     *
     * @return Lista de TurmaDTO contendo os detalhes de todas as turmas.
     */
    @Operation(summary = "Listar todas as turmas", description = "Retorna uma lista de todas as turmas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de turmas retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro ao buscar turmas")
    })
    @GetMapping
    public List<TurmaDTO> getAllTurmas() {
        try {
            return turmaService.getAllTurmas();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar turmas", e);
        }
    }

    /**
     * Busca uma turma específica pelo seu ID.
     *
     * @param id ID da turma a ser buscada.
     * @return ResponseEntity contendo os detalhes da turma.
     */
    @Operation(summary = "Buscar turma por ID", description = "Retorna os detalhes de uma turma específica pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Turma retornada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Turma não encontrada"),
        @ApiResponse(responseCode = "500", description = "Erro ao buscar turma")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TurmaDTO> getTurmaById(@PathVariable Long id) {
        try {
            Optional<TurmaDTO> turma = turmaService.getTurmaById(id);
            return turma.map(ResponseEntity::ok)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma com ID " + id + " não encontrada."));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar turma com ID " + id, e);
        }
    }

    // ============================= POST METHODS =============================

    /**
     * Cria uma nova turma com base nos dados fornecidos.
     *
     * @param turmaDTO Dados resumidos da nova turma a ser criada.
     * @return ResponseEntity contendo os detalhes da turma criada.
     */
    @Operation(summary = "Criar nova turma", description = "Cria uma nova turma")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Turma criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro ao criar turma")
    })
    @PostMapping
    public ResponseEntity<TurmaDTO> createTurma(@RequestBody TurmaInputDTO turmaDTO) {
        try {
            TurmaDTO savedTurma = turmaService.saveTurma(turmaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTurma);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao criar turma: " + e.getMessage(), e);
        }
    }

    // ============================= PUT METHODS =============================

    /**
     * Atualiza uma turma existente com base no ID fornecido.
     *
     * @param id ID da turma a ser atualizada.
     * @param turmaDTO Dados atualizados da turma.
     * @return ResponseEntity contendo os detalhes da turma atualizada.
     */
    @Operation(summary = "Atualizar turma", description = "Atualiza uma turma existente com base no ID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Turma atualizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro ao atualizar turma"),
        @ApiResponse(responseCode = "404", description = "Turma não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TurmaDTO> updateTurma(@PathVariable Long id, @RequestBody TurmaInputDTO turmaDTO) {
        try {
            TurmaDTO updatedTurma = turmaService.updateTurma(id, turmaDTO);
            return ResponseEntity.ok(updatedTurma);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao atualizar turma com ID " + id + ": " + e.getMessage(), e);
        }
    }
    
    
    // ============================= PATCH METHODS =============================
    
    /**
     * Atualiza o status de uma turma específica pelo seu ID.
     *
     * @param id ID da turma.
     * @param status Novo status da turma.
     * @return ResponseEntity confirmando a atualização do status.
     */
    @Operation(summary = "Atualizar status da turma", description = "Atualiza o status (ativo/inativo) de uma turma pelo ID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Turma não encontrada"),
        @ApiResponse(responseCode = "400", description = "Erro na atualização do status")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateTurmaStatus(@PathVariable Long id, @RequestBody boolean status) {
        try {
            turmaService.updateStatus(id, status);
            return ResponseEntity.ok("Status da turma atualizado com sucesso.");
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao atualizar status da turma: " + e.getMessage(), e);
        }
    }


    // ============================= DELETE METHODS =============================

    /**
     * Deleta uma turma com base no seu ID.
     *
     * @param id ID da turma a ser deletada.
     * @return ResponseEntity sem conteúdo.
     */
    @Operation(summary = "Deletar turma", description = "Deleta uma turma existente com base no ID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Turma deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Turma não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTurma(@PathVariable Long id) {
        try {
            turmaService.deleteTurma(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma com ID " + id + " não encontrada.", e);
        }
    }
    
               // --------------------- HORARIO --------------------------- //
    // Endpoint para upload do arquivo de horário
    @PostMapping(value = "/turmas/{id}/horario", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadHorario(
            @PathVariable Long id,
            @RequestParam("arquivo") MultipartFile arquivo) {
        if (arquivo.isEmpty()) {
            return ResponseEntity.badRequest().body("Arquivo não enviado.");
        }
        // Processar o arquivo
        return ResponseEntity.ok("Arquivo enviado com sucesso para a turma ID " + id);
    }




    // Endpoint para download do arquivo de horário
    @GetMapping("/{id}/horario")
    public ResponseEntity<byte[]> baixarHorario(@PathVariable Long id) {
        Arquivo horario = turmaService.obterHorario(id);

        if (horario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        return ResponseEntity.ok()
                .contentType(org.springframework.http.MediaType.parseMediaType(horario.getTipo())) // Corrigido o import de MediaType
                .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, 
                        "attachment; filename=\"" + horario.getNome() + "\"")
                .body(horario.getDados());
    }

    
    
 // Endpoint para deletar o arquivo de horário
    @DeleteMapping("/{id}/horario")
    public ResponseEntity<String> deletarHorario(@PathVariable Long id) {
        try {
            turmaService.deletarHorario(id); // Chama o método no serviço para realizar a exclusão
            return ResponseEntity.ok("Arquivo de horário deletado com sucesso para a turma ID " + id);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao deletar o arquivo de horário: " + e.getMessage(), e);
        }
    }

    // --------------------- turmas de professores e alunos --------------------------- //
    @GetMapping("/professores/{cpf}/turmas")
    public ResponseEntity<List<TurmaResumida2DTO>> getTurmasByProfessor(@PathVariable String cpf) {
        try {
            List<TurmaResumida2DTO> turmas = turmaService.getTurmasByProfessor(cpf);
            return ResponseEntity.ok(turmas);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar turmas do professor", e);
        }
    }

    @GetMapping("/alunos/{id}/turmas")
    public ResponseEntity<List<TurmaResumida2DTO>> getTurmasByAluno(@PathVariable Long id) {
        try {
            List<TurmaResumida2DTO> turmas = turmaService.getTurmasByAluno(id);
            return ResponseEntity.ok(turmas);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar turmas do aluno", e);
        }
    }

    
    
    
}

package projeto.integrador3.senac.mediotec.pi3_mediotec.turma;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turmas")
@Tag(name = "Turma", description = "Operações relacionadas às turmas")
public class TurmaController {

    @Autowired
    private TurmaService turmaService;

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
}

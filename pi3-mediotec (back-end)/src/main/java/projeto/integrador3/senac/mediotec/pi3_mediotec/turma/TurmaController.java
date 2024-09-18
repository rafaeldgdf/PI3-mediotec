package projeto.integrador3.senac.mediotec.pi3_mediotec.turma;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/turmas")
@Tag(name = "Turma", description = "Operações relacionadas às turmas")  // Nomeando o controller para o Swagger
public class TurmaController {

    @Autowired
    private TurmaService turmaService;

    // Endpoint para listar todas as turmas
    @GetMapping
    public List<TurmaDTO> getAllTurmas() {
        return turmaService.getAllTurmas();
    }

    // Endpoint para buscar uma turma por ID
    @GetMapping("/{id}")
    public ResponseEntity<TurmaDTO> getTurmaById(@PathVariable Long id) {
        Optional<TurmaDTO> turma = turmaService.getTurmaById(id);
        return turma.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma com ID " + id + " não encontrada."));
    }

    // Endpoint para criar uma nova turma
    @PostMapping
    public ResponseEntity<TurmaDTO> createTurma(@RequestBody TurmaInputDTO turmaDTO) {
        try {
            TurmaDTO savedTurma = turmaService.saveTurma(turmaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTurma); // Retorna a turma detalhada com nomes de professores e disciplinas
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao criar turma: " + e.getMessage(), e);
        }
    }

    // Endpoint para atualizar uma turma existente
    @PutMapping("/{id}")
    public ResponseEntity<TurmaDTO> updateTurma(@PathVariable Long id, @RequestBody TurmaInputDTO turmaDTO) {
        try {
            TurmaDTO updatedTurma = turmaService.updateTurma(id, turmaDTO);
            return ResponseEntity.ok(updatedTurma); // Retorna a turma detalhada com nomes de professores e disciplinas
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao atualizar turma com ID " + id + ": " + e.getMessage(), e);
        }
    }

    // Endpoint para deletar uma turma por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTurma(@PathVariable Long id) {
        try {
            turmaService.deleteTurma(id);
            return ResponseEntity.noContent().build(); // Retorna 204 No Content se a exclusão for bem-sucedida
        } catch (RuntimeException e) {
            // Retorna um erro 404 Not Found se a turma não for encontrada
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma com ID " + id + " não encontrada.", e);
        }
    }
}


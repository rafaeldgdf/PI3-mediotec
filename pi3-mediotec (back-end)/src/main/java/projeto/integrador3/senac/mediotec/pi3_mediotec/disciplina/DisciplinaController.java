package projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/disciplinas")
@Tag(name = "Disciplina", description = "Operações relacionadas às disciplinas")
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;

    // GET all disciplines (detalhada com objetos completos)
    @GetMapping
    public List<DisciplinaGetDTO> getAllDisciplinas() {
        try {
            return disciplinaService.getAllDisciplinas();
        } catch (Exception e) {
            // Log de erro
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar disciplinas: " + e.getMessage(), e);
        }
    }

    // GET discipline by ID (detalhada com objetos completos)
    @GetMapping("/{id}")
    public ResponseEntity<DisciplinaGetDTO> getDisciplinaById(@PathVariable Long id) {
        try {
            Optional<DisciplinaGetDTO> disciplina = disciplinaService.getDisciplinaById(id);
            return disciplina.map(ResponseEntity::ok)
                    .orElseGet(() -> {
                        // Log quando não encontra a disciplina
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada com o ID: " + id);
                    });
        } catch (Exception e) {
            // Log de erro
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar disciplina com ID: " + id, e);
        }
    }

    // POST para criar uma nova disciplina
    @PostMapping
    public ResponseEntity<DisciplinaResumidaDTO> createDisciplina(@RequestBody DisciplinaDTO disciplinaDTO) {
        try {
            DisciplinaResumidaDTO savedDisciplina = disciplinaService.saveDisciplina(disciplinaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDisciplina);
        } catch (Exception e) {
            // Log de erro
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao criar disciplina: " + e.getMessage(), e);
        }
    }

    // PUT para atualizar uma disciplina existente
    @PutMapping("/{id}")
    public ResponseEntity<DisciplinaResumidaDTO> updateDisciplina(@PathVariable Long id, @RequestBody DisciplinaDTO disciplinaDTO) {
        try {
            DisciplinaResumidaDTO updatedDisciplina = disciplinaService.updateDisciplina(id, disciplinaDTO);
            return ResponseEntity.ok(updatedDisciplina);
        } catch (RuntimeException e) {
            // Log de erro quando a disciplina não é encontrada
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada com o ID: " + id);
        } catch (Exception e) {
            // Log de erro geral
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao atualizar disciplina com o ID: " + id, e);
        }
    }

    // DELETE uma disciplina
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDisciplina(@PathVariable Long id) {
        try {
            disciplinaService.deleteDisciplina(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            // Log de erro quando a disciplina não é encontrada
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada com o ID: " + id);
        } catch (Exception e) {
            // Log de erro geral
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao deletar disciplina com o ID: " + id, e);
        }
    }
}

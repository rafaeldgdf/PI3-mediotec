package projeto.integrador3.senac.mediotec.pi3_mediotec.turma;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.DisciplinaDTO;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turmas")
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
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    //Listar turma as disciplinasde uma turma
    @GetMapping("/{turmaId}/disciplinas")
    public ResponseEntity<List<DisciplinaDTO>> getDisciplinasByTurma(@PathVariable Long turmaId) {
        List<DisciplinaDTO> disciplinas = turmaService.getDisciplinasByTurma(turmaId);
        return ResponseEntity.ok(disciplinas);  // Retorna a lista de disciplinas da turma
    }

    // Endpoint para criar uma nova turma
    @PostMapping
    public ResponseEntity<TurmaDTO> createTurma(@RequestBody TurmaInputDTO turmaDTO) {
        try {
            TurmaDTO savedTurma = turmaService.saveTurma(turmaDTO);
            return ResponseEntity.status(201).body(savedTurma); // Retorna a turma detalhada com nomes de professores e disciplinas
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Endpoint para atualizar uma turma existente
    @PutMapping("/{id}")
    public ResponseEntity<TurmaDTO> updateTurma(@PathVariable Long id, @RequestBody TurmaInputDTO turmaDTO) {
        try {
            TurmaDTO updatedTurma = turmaService.updateTurma(id, turmaDTO);
            return ResponseEntity.ok(updatedTurma); // Retorna a turma detalhada com nomes de professores e disciplinas
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
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
            return ResponseEntity.notFound().build();
        }
    }
}

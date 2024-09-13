package projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;

    // GET all disciplines (more detailed with full objects)
    @GetMapping
    public List<DisciplinaGetDTO> getAllDisciplinas() {
        return disciplinaService.getAllDisciplinas();
    }

    // GET discipline by ID (more detailed with full objects)
    @GetMapping("/{id}")
    public ResponseEntity<DisciplinaGetDTO> getDisciplinaById(@PathVariable Long id) {
        Optional<DisciplinaGetDTO> disciplina = disciplinaService.getDisciplinaById(id);
        return disciplina.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST for creating a new discipline with turma and professors
    @PostMapping
    public DisciplinaResumidaDTO createDisciplina(@RequestBody DisciplinaDTO disciplinaDTO) {
        return disciplinaService.saveDisciplina(disciplinaDTO);
    }

    // PUT for updating an existing discipline
    @PutMapping("/{id}")
    public ResponseEntity<DisciplinaResumidaDTO> updateDisciplina(@PathVariable Long id, @RequestBody DisciplinaDTO disciplinaDTO) {
        return ResponseEntity.ok(disciplinaService.updateDisciplina(id, disciplinaDTO));
    }

    // DELETE a discipline
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDisciplina(@PathVariable Long id) {
        disciplinaService.deleteDisciplina(id);
        return ResponseEntity.noContent().build();
    }
}

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

    @GetMapping
    public List<DisciplinaDTO> getAllDisciplinas() {
        return disciplinaService.getAllDisciplinas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisciplinaDTO> getDisciplinaById(@PathVariable Long id) {
        Optional<DisciplinaDTO> disciplina = disciplinaService.getDisciplinaById(id);
        return disciplina.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public DisciplinaDTO createDisciplina(@RequestBody Disciplina disciplina) {
        return disciplinaService.saveDisciplina(disciplina);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisciplinaDTO> updateDisciplina(@PathVariable Long id, @RequestBody Disciplina disciplina) {
        return ResponseEntity.ok(disciplinaService.updateDisciplina(id, disciplina));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDisciplina(@PathVariable Long id) {
        disciplinaService.deleteDisciplina(id);
        return ResponseEntity.noContent().build();
    }
}

package projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turmaDisciplinasProfessores")
public class TurmaDisciplinaProfessorController {

    @Autowired
    private TurmaDisciplinaProfessorService turmaDisciplinaProfessorService;

    @GetMapping
    public List<TurmaDisciplinaProfessor> getAllTurmaDisciplinaProfessores() {
        return turmaDisciplinaProfessorService.getAllTurmaDisciplinaProfessores();
    }

    @GetMapping("/{turmaId}/{disciplinaId}/{professorId}")
    public ResponseEntity<TurmaDisciplinaProfessor> getTurmaDisciplinaProfessorById(
            @PathVariable Long turmaId, 
            @PathVariable Long disciplinaId,
            @PathVariable String professorId) {

        TurmaDisciplinaProfessorId id = new TurmaDisciplinaProfessorId(turmaId, disciplinaId, professorId);
        Optional<TurmaDisciplinaProfessor> turmaDisciplinaProfessor = turmaDisciplinaProfessorService.getTurmaDisciplinaProfessorById(id);
        return turmaDisciplinaProfessor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public TurmaDisciplinaProfessor createTurmaDisciplinaProfessor(@RequestBody TurmaDisciplinaProfessor turmaDisciplinaProfessor) {
        return turmaDisciplinaProfessorService.saveTurmaDisciplinaProfessor(turmaDisciplinaProfessor);
    }

    @PutMapping("/{turmaId}/{disciplinaId}/{professorId}")
    public ResponseEntity<TurmaDisciplinaProfessor> updateTurmaDisciplinaProfessor(
            @PathVariable Long turmaId, 
            @PathVariable Long disciplinaId,
            @PathVariable String professorId,
            @RequestBody TurmaDisciplinaProfessor turmaDisciplinaProfessorDetails) {

        TurmaDisciplinaProfessorId id = new TurmaDisciplinaProfessorId(turmaId, disciplinaId, professorId);
        return ResponseEntity.ok(turmaDisciplinaProfessorService.updateTurmaDisciplinaProfessor(id, turmaDisciplinaProfessorDetails));
    }

    @DeleteMapping("/{turmaId}/{disciplinaId}/{professorId}")
    public ResponseEntity<Void> deleteTurmaDisciplinaProfessor(
            @PathVariable Long turmaId, 
            @PathVariable Long disciplinaId,
            @PathVariable String professorId) {

        TurmaDisciplinaProfessorId id = new TurmaDisciplinaProfessorId(turmaId, disciplinaId, professorId);
        turmaDisciplinaProfessorService.deleteTurmaDisciplinaProfessor(id);
        return ResponseEntity.noContent().build();
    }
}

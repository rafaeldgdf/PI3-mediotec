package projeto.integrador3.senac.mediotec.pi3_mediotec.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.TurmaDisciplina;
import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.TurmaDisciplinaId;
import projeto.integrador3.senac.mediotec.pi3_mediotec.services.TurmaDisciplinaService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turmaDisciplinas")
public class TurmaDisciplinaController {

    @Autowired
    private TurmaDisciplinaService turmaDisciplinaService;

    @GetMapping
    public List<TurmaDisciplina> getAllTurmaDisciplinas() {
        return turmaDisciplinaService.getAllTurmaDisciplinas();
    }

    @GetMapping("/{turmaId}/{disciplinaId}")
    public ResponseEntity<TurmaDisciplina> getTurmaDisciplinaById(@PathVariable Long turmaId, @PathVariable Long disciplinaId) {
        TurmaDisciplinaId id = new TurmaDisciplinaId(turmaId, disciplinaId);
        Optional<TurmaDisciplina> turmaDisciplina = turmaDisciplinaService.getTurmaDisciplinaById(id);
        return turmaDisciplina.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public TurmaDisciplina createTurmaDisciplina(@RequestBody TurmaDisciplina turmaDisciplina) {
        return turmaDisciplinaService.saveTurmaDisciplina(turmaDisciplina);
    }

    @PutMapping("/{turmaId}/{disciplinaId}")
    public ResponseEntity<TurmaDisciplina> updateTurmaDisciplina(@PathVariable Long turmaId, @PathVariable Long disciplinaId, @RequestBody TurmaDisciplina turmaDisciplinaDetails) {
        TurmaDisciplinaId id = new TurmaDisciplinaId(turmaId, disciplinaId);
        return ResponseEntity.ok(turmaDisciplinaService.updateTurmaDisciplina(id, turmaDisciplinaDetails));
    }

    @DeleteMapping("/{turmaId}/{disciplinaId}")
    public ResponseEntity<Void> deleteTurmaDisciplina(@PathVariable Long turmaId, @PathVariable Long disciplinaId) {
        TurmaDisciplinaId id = new TurmaDisciplinaId(turmaId, disciplinaId);
        turmaDisciplinaService.deleteTurmaDisciplina(id);
        return ResponseEntity.noContent().build();
    }
}

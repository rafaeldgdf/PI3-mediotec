package projeto.integrador3.senac.mediotec.pi3_mediotec.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.integrador3.senac.mediotec.pi3_mediotec.dtos.TurmaDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.services.TurmaService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turmas")
public class TurmaController {

    @Autowired
    private TurmaService turmaService;

    @GetMapping
    public List<TurmaDTO> getAllTurmas() {
        return turmaService.getAllTurmas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurmaDTO> getTurmaById(@PathVariable Long id) {
        Optional<TurmaDTO> turma = turmaService.getTurmaById(id);
        return turma.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TurmaDTO> createTurma(@RequestBody TurmaDTO turmaDTO) {
        TurmaDTO savedTurma = turmaService.saveTurma(turmaDTO);
        return ResponseEntity.status(201).body(savedTurma); // Retorna o status 201 Created
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurmaDTO> updateTurma(@PathVariable Long id, @RequestBody TurmaDTO turmaDTO) {
        TurmaDTO updatedTurma = turmaService.updateTurma(id, turmaDTO);
        return ResponseEntity.ok(updatedTurma); // Retorna o status 200 OK
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTurma(@PathVariable Long id) {
        turmaService.deleteTurma(id);
        return ResponseEntity.noContent().build();
    }
}

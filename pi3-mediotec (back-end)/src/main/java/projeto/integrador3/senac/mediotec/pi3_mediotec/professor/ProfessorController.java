package projeto.integrador3.senac.mediotec.pi3_mediotec.professor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @GetMapping
    public ResponseEntity<List<ProfessorDTO>> getAllProfessores() {
        List<ProfessorDTO> professores = professorService.getAllProfessores();
        return new ResponseEntity<>(professores, HttpStatus.OK);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<ProfessorDTO> getProfessorById(@PathVariable String cpf) {
        Optional<ProfessorDTO> professor = professorService.getProfessorById(cpf);
        return professor.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ProfessorDTO> createProfessor(@RequestBody Professor professor) {
        ProfessorDTO savedProfessor = professorService.saveProfessor(professor);
        return new ResponseEntity<>(savedProfessor, HttpStatus.CREATED);
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<ProfessorDTO> updateProfessor(@PathVariable String cpf, @RequestBody Professor professorDetails) {
        try {
            ProfessorDTO updatedProfessor = professorService.updateProfessor(cpf, professorDetails);
            return new ResponseEntity<>(updatedProfessor, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deleteProfessor(@PathVariable String cpf) {
        try {
            professorService.deleteProfessor(cpf);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

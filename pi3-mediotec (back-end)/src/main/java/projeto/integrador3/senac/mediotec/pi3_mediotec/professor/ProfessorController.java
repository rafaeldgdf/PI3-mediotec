package projeto.integrador3.senac.mediotec.pi3_mediotec.professor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.Disciplina;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    // Método GET para listar todos os professores
    @GetMapping
    public ResponseEntity<List<ProfessorDTO>> getAllProfessores() {
        List<ProfessorDTO> professores = professorService.getAllProfessores();
        return new ResponseEntity<>(professores, HttpStatus.OK);
    }

    // Método GET para buscar professor por CPF
    @GetMapping("/{cpf}")
    public ResponseEntity<ProfessorDTO> getProfessorById(@PathVariable String cpf) {
        Optional<ProfessorDTO> professor = professorService.getProfessorById(cpf);
        return professor.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Método GET para listar disciplinas lecionadas por um professor específico
    @GetMapping("/{professorId}/disciplinas")
    public ResponseEntity<List<Disciplina>> getDisciplinasByProfessor(@PathVariable String professorId) {
        List<Disciplina> disciplinas = professorService.getDisciplinasByProfessor(professorId);
        return new ResponseEntity<>(disciplinas, HttpStatus.OK);
    }

    // Método POST para criar um novo professor
    @PostMapping
    public ResponseEntity<ProfessorDTO> createProfessor(@RequestBody ProfessorDTO professorDTO) {
        try {
            ProfessorDTO savedProfessor = professorService.saveProfessor(professorDTO);
            return new ResponseEntity<>(savedProfessor, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Método PUT para atualizar um professor existente
    @PutMapping("/{cpf}")
    public ResponseEntity<ProfessorDTO> updateProfessor(@PathVariable String cpf, @RequestBody ProfessorDTO professorDTO) {
        try {
            // Atualize o professor usando o DTO
            ProfessorDTO updatedProfessor = professorService.updateProfessor(cpf, professorDTO);
            return new ResponseEntity<>(updatedProfessor, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // Método DELETE para excluir um professor por CPF
    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deleteProfessor(@PathVariable String cpf) {
        try {
            professorService.deleteProfessor(cpf);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Sucesso - 204
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Caso o professor não exista
        }
    }
}

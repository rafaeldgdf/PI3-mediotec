package projeto.integrador3.senac.mediotec.pi3_mediotec.professor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.Disciplina;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/professores")
@Tag(name = "Professor", description = "Operações relacionadas aos professores")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    // Método GET para listar todos os professores
    @GetMapping
    public ResponseEntity<List<ProfessorResumidoDTO>> getAllProfessores() {
        try {
            List<ProfessorResumidoDTO> professores = professorService.getAllProfessores();
            return new ResponseEntity<>(professores, HttpStatus.OK);
        } catch (Exception e) {
            // Log de erro detalhado
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar professores: " + e.getMessage(), e);
        }
    }

    // Método GET para buscar professor por CPF
    @GetMapping("/{cpf}")
    public ResponseEntity<ProfessorResumidoDTO> getProfessorById(@PathVariable String cpf) {
        try {
            Optional<ProfessorResumidoDTO> professor = professorService.getProfessorById(cpf);
            return professor.map(ResponseEntity::ok)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado com o CPF: " + cpf));
        } catch (Exception e) {
            // Log de erro ao buscar professor
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar professor com CPF: " + cpf, e);
        }
    }

    // Método POST para criar um novo professor
    @PostMapping
    public ResponseEntity<ProfessorResumidoDTO> createProfessor(@RequestBody ProfessorDTO professorDTO) {
        try {
            ProfessorResumidoDTO savedProfessor = professorService.saveProfessor(professorDTO);
            return new ResponseEntity<>(savedProfessor, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Log de erro ao criar professor
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao criar professor: " + e.getMessage(), e);
        } catch (Exception e) {
            // Log de erro geral
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao processar a criação do professor", e);
        }
    }

    // Método PUT para atualizar um professor existente
    @PutMapping("/{cpf}")
    public ResponseEntity<ProfessorResumidoDTO> updateProfessor(@PathVariable String cpf, @RequestBody ProfessorDTO professorDTO) {
        try {
            ProfessorResumidoDTO updatedProfessor = professorService.updateProfessor(cpf, professorDTO);
            return new ResponseEntity<>(updatedProfessor, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Log de erro quando o professor não é encontrado
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado com o CPF: " + cpf, e);
        } catch (Exception e) {
            // Log de erro geral ao atualizar
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao atualizar o professor com CPF: " + cpf, e);
        }
    }

    // Método DELETE para excluir um professor por CPF
    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deleteProfessor(@PathVariable String cpf) {
        try {
            professorService.deleteProfessor(cpf);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Sucesso - 204
        } catch (RuntimeException e) {
            // Log de erro ao tentar excluir um professor não encontrado
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado com o CPF: " + cpf, e);
        } catch (Exception e) {
            // Log de erro geral ao excluir
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao deletar professor com CPF: " + cpf, e);
        }
    }
}

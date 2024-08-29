package projeto.integrador3.senac.mediotec.pi3_mediotec.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import projeto.integrador3.senac.mediotec.pi3_mediotec.dtos.AlunoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.Aluno;
import projeto.integrador3.senac.mediotec.pi3_mediotec.services.AlunoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    // Lista todos os alunos
    @GetMapping
    public ResponseEntity<List<AlunoDTO>> getAllAlunos() {
        List<AlunoDTO> alunos = alunoService.getAllAlunos();
        return new ResponseEntity<>(alunos, HttpStatus.OK);
    }

    // Busca aluno pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<AlunoDTO> getAlunoById(@PathVariable Long id) {
        Optional<AlunoDTO> aluno = alunoService.getAlunoById(id);
        return aluno.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Cria um novo aluno
    @PostMapping("/incluir")
    public ResponseEntity<AlunoDTO> createAluno(@RequestBody Aluno aluno) {
        AlunoDTO savedAluno = alunoService.saveAluno(aluno);
        return new ResponseEntity<>(savedAluno, HttpStatus.CREATED);
    }

    // Atualiza um aluno existente
    @PutMapping("/{id}")
    public ResponseEntity<AlunoDTO> updateAluno(@PathVariable Long id, @RequestBody Aluno alunoDetails) {
        try {
            AlunoDTO updatedAluno = alunoService.updateAluno(id, alunoDetails);
            return new ResponseEntity<>(updatedAluno, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Deleta um aluno
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAluno(@PathVariable Long id) {
        try {
            alunoService.deleteAluno(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

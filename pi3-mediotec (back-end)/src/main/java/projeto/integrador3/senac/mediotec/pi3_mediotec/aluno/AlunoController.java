package projeto.integrador3.senac.mediotec.pi3_mediotec.aluno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import projeto.integrador3.senac.mediotec.pi3_mediotec.conceito.ConceitoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.conceito.ConceitoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;
    
    @Autowired
    private ConceitoService conceitoService;

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
    public ResponseEntity<AlunoDTO> createAluno(@RequestBody AlunoResumidoDTO2 alunoResumidoDTO) { // Altere para AlunoResumidoDTO2
        try {
            AlunoDTO savedAluno = alunoService.saveAluno(alunoResumidoDTO); // Chama saveAluno com AlunoResumidoDTO2
            return new ResponseEntity<>(savedAluno, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Atualiza um aluno existente
    @PutMapping("/{id}")
    public ResponseEntity<AlunoDTO> updateAluno(@PathVariable Long id, @RequestBody AlunoDTO alunoDetails) {
        try {
            AlunoDTO updatedAluno = alunoService.updateAluno(id, alunoDetails); // Passa AlunoDTO
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
    
    // Rota para visualizar todos os conceitos de um aluno
    @GetMapping("/{alunoId}/conceito")
    public ResponseEntity<List<ConceitoDTO>> listarConceitosPorAluno(@PathVariable Long alunoId) {
        List<ConceitoDTO> conceitos = conceitoService.buscarConceitosPorAluno(alunoId);
        return ResponseEntity.ok(conceitos);
    }

    // Rota para visualizar conceitos de um aluno por uma disciplina específica
    @GetMapping("/{alunoId}/disciplina/{disciplinaId}")
    public ResponseEntity<List<ConceitoDTO>> listarConceitosPorAlunoEDisciplina(
            @PathVariable Long alunoId, @PathVariable Long disciplinaId) {
        List<ConceitoDTO> conceitos = conceitoService.buscarConceitosPorAlunoEDisciplina(alunoId, disciplinaId);
        return ResponseEntity.ok(conceitos);
    }
}

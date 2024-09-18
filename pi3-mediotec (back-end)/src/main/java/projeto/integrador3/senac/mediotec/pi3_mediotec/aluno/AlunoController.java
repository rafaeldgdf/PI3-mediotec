package projeto.integrador3.senac.mediotec.pi3_mediotec.aluno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import io.swagger.v3.oas.annotations.tags.Tag;
import projeto.integrador3.senac.mediotec.pi3_mediotec.conceito.ConceitoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.conceito.ConceitoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alunos")
@Tag(name = "Aluno", description = "Operações relacionadas a Alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;
    
    @Autowired
    private ConceitoService conceitoService;

    // Lista todos os alunos
    @GetMapping
    public ResponseEntity<List<AlunoDTO>> getAllAlunos() {
        List<AlunoDTO> alunos = alunoService.getAllAlunos();
        if (alunos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum aluno encontrado.");
        }
        return new ResponseEntity<>(alunos, HttpStatus.OK);
    }

    // Busca aluno pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<AlunoDTO> getAlunoById(@PathVariable Long id) {
        Optional<AlunoDTO> aluno = alunoService.getAlunoById(id);
        return aluno.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno com ID " + id + " não encontrado."));
    }

    // Cria um novo aluno
    @PostMapping("/incluir")
    public ResponseEntity<AlunoDTO> createAluno(@RequestBody AlunoResumidoDTO2 alunoResumidoDTO) {
        try {
            AlunoDTO savedAluno = alunoService.saveAluno(alunoResumidoDTO);
            return new ResponseEntity<>(savedAluno, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao criar aluno: " + e.getMessage());
        }
    }

    // Atualiza um aluno existente
    @PutMapping("/{id}")
    public ResponseEntity<AlunoDTO> updateAluno(@PathVariable Long id, @RequestBody AlunoResumidoDTO2 alunoResumido2DTO) {
        try {
            AlunoDTO updatedAluno = alunoService.updateAluno(id, alunoResumido2DTO);
            return new ResponseEntity<>(updatedAluno, HttpStatus.OK);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao atualizar aluno com ID " + id + ": " + e.getMessage());
        }
    }

    // Deleta um aluno
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAluno(@PathVariable Long id) {
        try {
            alunoService.deleteAluno(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao deletar aluno com ID " + id + ": " + e.getMessage());
        }
    }

    // Rota para visualizar todos os conceitos de um aluno
    @GetMapping("/{alunoId}/conceito")
    public ResponseEntity<List<ConceitoDTO>> listarConceitosPorAluno(@PathVariable Long alunoId) {
        List<ConceitoDTO> conceitos = conceitoService.buscarConceitosPorAluno(alunoId);
        if (conceitos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum conceito encontrado para o aluno com ID " + alunoId);
        }
        return ResponseEntity.ok(conceitos);
    }

    // Rota para visualizar conceitos de um aluno por uma disciplina específica
    @GetMapping("/{alunoId}/disciplina/{disciplinaId}")
    public ResponseEntity<List<ConceitoDTO>> listarConceitosPorAlunoEDisciplina(
            @PathVariable Long alunoId, @PathVariable Long disciplinaId) {
        List<ConceitoDTO> conceitos = conceitoService.buscarConceitosPorAlunoEDisciplina(alunoId, disciplinaId);
        if (conceitos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum conceito encontrado para o aluno com ID " + alunoId + " na disciplina com ID " + disciplinaId);
        }
        return ResponseEntity.ok(conceitos);
    }
}

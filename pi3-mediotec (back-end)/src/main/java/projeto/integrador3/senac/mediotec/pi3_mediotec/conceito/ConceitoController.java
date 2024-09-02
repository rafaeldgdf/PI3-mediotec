package projeto.integrador3.senac.mediotec.pi3_mediotec.conceito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/conceitos")
public class ConceitoController {

    @Autowired
    private ConceitoService conceitoService;

    @GetMapping
    public ResponseEntity<List<ConceitoDTO>> listarConceitos() {
        List<ConceitoDTO> conceitos = conceitoService.listarConceitos();
        return ResponseEntity.ok(conceitos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConceitoDTO> buscarConceitoPorId(@PathVariable Long id) {
        Optional<ConceitoDTO> conceito = conceitoService.buscarConceitoPorId(id);
        return conceito.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ConceitoDTO> criarConceito(@RequestBody ConceitoDTO conceitoDTO) {
        ConceitoDTO novoConceito = conceitoService.salvarConceito(conceitoDTO);
        return ResponseEntity.ok(novoConceito);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConceitoDTO> atualizarConceito(@PathVariable Long id, @RequestBody ConceitoDTO conceitoDTO) {
        ConceitoDTO conceitoAtualizado = conceitoService.atualizarConceito(id, conceitoDTO);
        return ResponseEntity.ok(conceitoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarConceito(@PathVariable Long id) {
        conceitoService.deletarConceito(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<List<ConceitoDTO>> buscarConceitosPorAluno(@PathVariable Long alunoId) {
        List<ConceitoDTO> conceitos = conceitoService.buscarConceitosPorAluno(alunoId);
        return ResponseEntity.ok(conceitos);
    }
}

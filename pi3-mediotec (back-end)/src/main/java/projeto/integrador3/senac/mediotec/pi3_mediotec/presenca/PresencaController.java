package projeto.integrador3.senac.mediotec.pi3_mediotec.presenca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alunos/{idAluno}/presencas")
public class PresencaController {

    @Autowired
    private PresencaService presencaService;

    @PostMapping
    public ResponseEntity<PresencaDTO> criarPresenca(@PathVariable Long idAluno, @RequestBody PresencaDTO presencaDTO) {
        PresencaDTO novaPresenca = presencaService.salvarPresenca(idAluno, presencaDTO);
        return ResponseEntity.ok(novaPresenca);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PresencaDTO> atualizarPresenca(@PathVariable Long idAluno, @PathVariable Long id, @RequestBody PresencaDTO presencaDTO) {
        PresencaDTO presencaAtualizada = presencaService.atualizarPresenca(idAluno, id, presencaDTO);
        return ResponseEntity.ok(presencaAtualizada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PresencaDTO> buscarPresencaPorId(@PathVariable Long idAluno, @PathVariable Long id) {
        Optional<PresencaDTO> presencaDTO = presencaService.buscarPresencaPorId(idAluno, id);
        return presencaDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<PresencaDTO>> listarPresencasPorAluno(@PathVariable Long idAluno) {
        List<PresencaDTO> presencas = presencaService.listarPresencasPorAluno(idAluno);
        return ResponseEntity.ok(presencas);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPresenca(@PathVariable Long idAluno, @PathVariable Long id) {
        presencaService.deletarPresenca(idAluno, id);
        return ResponseEntity.noContent().build();
    }
}

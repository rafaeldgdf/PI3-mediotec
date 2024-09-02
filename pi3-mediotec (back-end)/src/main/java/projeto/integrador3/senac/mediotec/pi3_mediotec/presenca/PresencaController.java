package projeto.integrador3.senac.mediotec.pi3_mediotec.presenca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/presencas")
public class PresencaController {

    @Autowired
    private PresencaService presencaService;

    @PostMapping
    public ResponseEntity<PresencaDTO> criarPresenca(@RequestBody PresencaDTO presencaDTO) {
        PresencaDTO novaPresenca = presencaService.salvarPresenca(presencaDTO);
        return ResponseEntity.ok(novaPresenca);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PresencaDTO> atualizarPresenca(@PathVariable Long id, @RequestBody PresencaDTO presencaDTO) {
        PresencaDTO presencaAtualizada = presencaService.atualizarPresenca(id, presencaDTO);
        return ResponseEntity.ok(presencaAtualizada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PresencaDTO> buscarPresencaPorId(@PathVariable Long id) {
        Optional<PresencaDTO> presencaDTO = presencaService.buscarPresencaPorId(id);
        return presencaDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<PresencaDTO>> listarPresencas() {
        List<PresencaDTO> presencas = presencaService.listarPresencas();
        return ResponseEntity.ok(presencas);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPresenca(@PathVariable Long id) {
        presencaService.deletarPresenca(id);
        return ResponseEntity.noContent().build();
    }
}

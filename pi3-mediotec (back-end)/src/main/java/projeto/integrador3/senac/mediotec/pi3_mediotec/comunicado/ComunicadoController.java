package projeto.integrador3.senac.mediotec.pi3_mediotec.comunicado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comunicados")
public class ComunicadoController {

    @Autowired
    private ComunicadoService comunicadoService;

    @PostMapping
    public ResponseEntity<ComunicadoDTO> criarComunicado(@RequestBody ComunicadoDTO comunicadoDTO) {
        ComunicadoDTO novoComunicado = comunicadoService.salvarComunicado(comunicadoDTO);
        return ResponseEntity.ok(novoComunicado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComunicadoDTO> buscarComunicadoPorId(@PathVariable Long id) {
        Optional<ComunicadoDTO> comunicadoDTO = comunicadoService.buscarComunicadoPorId(id);
        return comunicadoDTO.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ComunicadoDTO>> listarTodosOsComunicados() {
        List<ComunicadoDTO> comunicados = comunicadoService.listarComunicados();
        return ResponseEntity.ok(comunicados);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComunicadoDTO> atualizarComunicado(@PathVariable Long id, @RequestBody ComunicadoDTO comunicadoDTO) {
        ComunicadoDTO comunicadoAtualizado = comunicadoService.atualizarComunicado(id, comunicadoDTO);
        return ResponseEntity.ok(comunicadoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarComunicado(@PathVariable Long id) {
        comunicadoService.deletarComunicado(id);
        return ResponseEntity.noContent().build();
    }
}

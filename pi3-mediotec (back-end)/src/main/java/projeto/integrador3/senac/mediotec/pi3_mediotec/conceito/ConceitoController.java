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

    // Rota para listar todos os conceitos (GET) - retorna o DTO completo
    @GetMapping
    public ResponseEntity<List<ConceitoDTO>> listarConceitos() {
        List<ConceitoDTO> conceitos = conceitoService.listarConceitos();
        return ResponseEntity.ok(conceitos);
    }

    // Rota para buscar conceito por ID (GET) - retorna o DTO completo
    @GetMapping("/{id}")
    public ResponseEntity<ConceitoDTO> buscarConceitoPorId(@PathVariable Long id) {
        Optional<ConceitoDTO> conceito = conceitoService.buscarConceitoPorId(id);
        return conceito.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Rota para criar um novo conceito (POST) - usa ConceitoResumidoDTO
    @PostMapping
    public ResponseEntity<ConceitoDTO> criarConceito(@RequestBody ConceitoResumidoDTO conceitoResumidoDTO) {
        try {
            ConceitoDTO novoConceito = conceitoService.salvarConceito(conceitoResumidoDTO);
            return ResponseEntity.ok(novoConceito);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Rota para atualizar um conceito existente (PUT) - usa ConceitoResumidoDTO
    @PutMapping("/{id}")
    public ResponseEntity<ConceitoDTO> atualizarConceito(@PathVariable Long id, @RequestBody ConceitoResumidoDTO conceitoResumidoDTO) {
        try {
            ConceitoDTO conceitoAtualizado = conceitoService.atualizarConceito(id, conceitoResumidoDTO);
            return ResponseEntity.ok(conceitoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Rota para deletar um conceito (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarConceito(@PathVariable Long id) {
        try {
            conceitoService.deletarConceito(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


}


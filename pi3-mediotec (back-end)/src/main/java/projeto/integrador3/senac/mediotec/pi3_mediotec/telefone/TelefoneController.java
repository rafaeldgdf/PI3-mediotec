package projeto.integrador3.senac.mediotec.pi3_mediotec.telefone;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/telefones")
public class TelefoneController {

    @Autowired
    private TelefoneService telefoneService;

    @PostMapping
    public ResponseEntity<TelefoneDTO> criarTelefone(@RequestBody TelefoneDTO telefoneDTO) {
        TelefoneDTO novoTelefone = telefoneService.salvarTelefone(telefoneDTO);
        return ResponseEntity.ok(novoTelefone);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TelefoneDTO> buscarTelefonePorId(@PathVariable Long id) {
        Optional<TelefoneDTO> telefoneDTO = telefoneService.buscarTelefonePorId(id);
        return telefoneDTO.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<TelefoneDTO>> listarTodosOsTelefones() {
        List<TelefoneDTO> telefones = telefoneService.listarTelefones();
        return ResponseEntity.ok(telefones);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TelefoneDTO> atualizarTelefone(@PathVariable Long id, @RequestBody TelefoneDTO telefoneDTO) {
        TelefoneDTO telefoneAtualizado = telefoneService.atualizarTelefone(id, telefoneDTO);
        return ResponseEntity.ok(telefoneAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTelefone(@PathVariable Long id) {
        telefoneService.deletarTelefone(id);
        return ResponseEntity.noContent().build();
    }
}


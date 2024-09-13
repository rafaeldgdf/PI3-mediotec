package projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/coordenadores")
public class CoordenadorController {

    @Autowired
    private CoordenadorService coordenadorService;

    // Rota para listar todos os coordenadores
    @GetMapping
    public ResponseEntity<List<CoordenadorDTO>> getAllCoordenadores() {
        List<CoordenadorDTO> coordenadores = coordenadorService.getAllCoordenadores();
        return new ResponseEntity<>(coordenadores, HttpStatus.OK);
    }

    // Rota para obter um coordenador por CPF (ID)
    @GetMapping("/{id}")
    public ResponseEntity<CoordenadorDTO> getCoordenadorById(@PathVariable String id) {
        Optional<CoordenadorDTO> coordenador = coordenadorService.getCoordenadorById(id);
        return coordenador.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                          .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Rota para criar um novo coordenador (POST), agora usando CoordenadorDTO
    @PostMapping
    public ResponseEntity<CoordenadorDTO> createCoordenador(@RequestBody CoordenadorDTO coordenadorDTO) {
        try {
            CoordenadorDTO savedCoordenador = coordenadorService.saveCoordenador(coordenadorDTO);
            return new ResponseEntity<>(savedCoordenador, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Rota para atualizar um coordenador existente (PUT), agora usando CoordenadorDTO
    @PutMapping("/{id}")
    public ResponseEntity<CoordenadorDTO> updateCoordenador(@PathVariable String id, @RequestBody CoordenadorDTO coordenadorDTO) {
        try {
            CoordenadorDTO updatedCoordenador = coordenadorService.updateCoordenador(id, coordenadorDTO);
            return new ResponseEntity<>(updatedCoordenador, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Rota para deletar um coordenador por CPF (ID)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoordenador(@PathVariable String id) {
        try {
            coordenadorService.deleteCoordenador(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

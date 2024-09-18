package projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import org.springframework.web.server.ResponseStatusException;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/coordenadores")
@Tag(name = "Coordenador", description = "Gerenciamento dos Coordenadores")
public class CoordenadorController {

    @Autowired
    private CoordenadorService coordenadorService;

    @GetMapping
    public ResponseEntity<List<CoordenadorDTO>> getAllCoordenadores() {
        try {
            List<CoordenadorDTO> coordenadores = coordenadorService.getAllCoordenadores();
            return new ResponseEntity<>(coordenadores, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar coordenadores", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoordenadorDTO> getCoordenadorById(@PathVariable String id) {
        try {
            Optional<CoordenadorDTO> coordenador = coordenadorService.getCoordenadorById(id);
            return coordenador.map(ResponseEntity::ok)
                              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coordenador não encontrado"));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar coordenador", e);
        }
    }

    @PostMapping
    public ResponseEntity<CoordenadorDTO> createCoordenador(@RequestBody CoordenadorDTO coordenadorDTO) {
        try {
            CoordenadorDTO savedCoordenador = coordenadorService.saveCoordenador(coordenadorDTO);
            return new ResponseEntity<>(savedCoordenador, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao criar coordenador", e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CoordenadorDTO> updateCoordenador(@PathVariable String id, @RequestBody CoordenadorDTO coordenadorDTO) {
        try {
            CoordenadorDTO updatedCoordenador = coordenadorService.updateCoordenador(id, coordenadorDTO);
            return new ResponseEntity<>(updatedCoordenador, HttpStatus.OK);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Coordenador não encontrado", e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoordenador(@PathVariable String id) {
        try {
            coordenadorService.deleteCoordenador(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Coordenador não encontrado", e);
        }
    }
}

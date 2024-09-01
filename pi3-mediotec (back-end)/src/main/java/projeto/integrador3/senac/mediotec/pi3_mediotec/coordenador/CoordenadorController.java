package projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/coordenadores")
public class CoordenadorController {

    @Autowired
    private CoordenadorService coordenadorService;
  
//rotas crud 
    @GetMapping
    public ResponseEntity<List<CoordenadorDTO>> getAllCoordenadores() {
        List<CoordenadorDTO> coordenadores = coordenadorService.getAllCoordenadores();
        return new ResponseEntity<>(coordenadores, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoordenadorDTO> getCoordenadorById(@PathVariable String id) {
        Optional<CoordenadorDTO> coordenador = coordenadorService.getCoordenadorById(id);
        return coordenador.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                          .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<CoordenadorDTO> createCoordenador(@RequestBody Coordenador coordenador) {
        CoordenadorDTO savedCoordenador = coordenadorService.saveCoordenador(coordenador);
        return new ResponseEntity<>(savedCoordenador, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CoordenadorDTO> updateCoordenador(@PathVariable String id, @RequestBody Coordenador coordenadorDetails) {
        try {
            CoordenadorDTO updatedCoordenador = coordenadorService.updateCoordenador(id, coordenadorDetails);
            return new ResponseEntity<>(updatedCoordenador, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

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


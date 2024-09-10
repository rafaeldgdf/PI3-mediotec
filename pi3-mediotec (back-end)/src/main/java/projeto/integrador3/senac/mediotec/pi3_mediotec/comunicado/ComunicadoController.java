package projeto.integrador3.senac.mediotec.pi3_mediotec.comunicado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comunicados")
public class ComunicadoController {

    @Autowired
    private ComunicadoService comunicadoService;

    @PostMapping
    public ResponseEntity<ComunicadoDTO> criarComunicado(@RequestBody ComunicadoDTO comunicadoDTO) {
        ComunicadoDTO novoComunicado = comunicadoService.criarComunicado(comunicadoDTO);
        return ResponseEntity.ok(novoComunicado);
    }

    @GetMapping("/professor/{id}")
    public ResponseEntity<List<ComunicadoDTO>> listarComunicadosPorProfessor(@PathVariable String cpf) {
        List<ComunicadoDTO> comunicados = comunicadoService.listarComunicadosPorProfessor(cpf);
        return ResponseEntity.ok(comunicados);
    }

    @GetMapping("/aluno/{id}")
    public ResponseEntity<List<ComunicadoDTO>> listarComunicadosPorAluno(@PathVariable Long id) {
        List<ComunicadoDTO> comunicados = comunicadoService.listarComunicadosPorAluno(id);
        return ResponseEntity.ok(comunicados);
    }
}

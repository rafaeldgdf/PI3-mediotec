package projeto.integrador3.senac.mediotec.pi3_mediotec.comunicado;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comunicados")
@Tag(name = "Comunicados", description = "Operações relacionadas a comunicados")
public class ComunicadoController {

    @Autowired
    private ComunicadoService comunicadoService;

    // ============================== CRIAR COMUNICADO ==============================

    @PostMapping("/coordenador/{coordenadorCpf}")
    @Operation(summary = "Criar comunicado por coordenador")
    public ResponseEntity<ComunicadoCompletoDTO> criarComunicadoPorCoordenador(
            @PathVariable String coordenadorCpf,
            @RequestBody ComunicadoDTO comunicadoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(comunicadoService.criarComunicadoPorCoordenador(coordenadorCpf, comunicadoDTO));
    }


    @PostMapping("/professor/{professorCpf}")
    @Operation(summary = "Criar comunicado por professor")
    public ResponseEntity<ComunicadoCompletoDTO> criarComunicadoPorProfessor(
            @PathVariable String professorCpf,
            @RequestBody ComunicadoDTO comunicadoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(comunicadoService.criarComunicadoPorProfessor(professorCpf, comunicadoDTO));
    }

    // ============================== LISTAR COMUNICADOS ==============================

    @GetMapping("/coordenador/{coordenadorCpf}")
    @Operation(summary = "Listar comunicados de um coordenador")
    public ResponseEntity<List<ComunicadoCompletoDTO>> listarComunicadosPorCoordenador(
            @PathVariable String coordenadorCpf) {
        return ResponseEntity.ok(comunicadoService.listarComunicadosPorCoordenador(coordenadorCpf));
    }

    @GetMapping("/professor/{professorCpf}")
    @Operation(summary = "Listar comunicados de um professor")
    public ResponseEntity<List<ComunicadoCompletoDTO>> listarComunicadosPorProfessor(
            @PathVariable String professorCpf) {
        return ResponseEntity.ok(comunicadoService.listarComunicadosPorProfessor(professorCpf));
    }

    @GetMapping("/aluno/{identificador}")
    @Operation(summary = "Listar comunicados de um aluno")
    public ResponseEntity<List<ComunicadoCompletoDTO>> listarComunicadosPorAluno(
            @PathVariable String identificador) {
        return ResponseEntity.ok(comunicadoService.listarComunicadosPorAluno(identificador));
    }

    @GetMapping("/turma/{turmaId}")
    @Operation(summary = "Listar comunicados de uma turma")
    public ResponseEntity<List<ComunicadoCompletoDTO>> listarComunicadosPorTurma(
            @PathVariable Long turmaId) {
        return ResponseEntity.ok(comunicadoService.listarComunicadosPorTurma(turmaId));
    }

    // ============================== ATUALIZAR COMUNICADO ==============================

    @PutMapping("/{comunicadoId}")
    @Operation(summary = "Atualizar comunicado")
    public ResponseEntity<ComunicadoCompletoDTO> atualizarComunicado(
            @PathVariable Long comunicadoId,
            @RequestBody ComunicadoDTO comunicadoDTO) {
        return ResponseEntity.ok(comunicadoService.atualizarComunicado(comunicadoId, comunicadoDTO));
    }

    // ============================== DELETAR COMUNICADO ==============================

    @DeleteMapping("/{comunicadoId}")
    @Operation(summary = "Deletar comunicado")
    public ResponseEntity<Void> deletarComunicado(@PathVariable Long comunicadoId) {
        comunicadoService.deletarComunicado(comunicadoId);
        return ResponseEntity.noContent().build();
    }
}

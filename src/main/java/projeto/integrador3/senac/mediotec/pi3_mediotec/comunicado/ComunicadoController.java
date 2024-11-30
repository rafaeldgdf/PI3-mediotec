package projeto.integrador3.senac.mediotec.pi3_mediotec.comunicado;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/comunicados")
@Tag(name = "Comunicados", description = "Operações relacionadas aos comunicados")
public class ComunicadoController {

    @Autowired
    private ComunicadoService comunicadoService;

    // **Criação de comunicado por coordenador**
    @Operation(summary = "Criar comunicado por coordenador", description = "Cria um comunicado emitido por um coordenador para um ou mais alunos ou turmas.")
    @PostMapping("/coordenador/{coordenadorId}")
    public ResponseEntity<ComunicadoSimplesDTO> criarComunicadoPorCoordenador(
            @PathVariable String coordenadorId, @RequestBody ComunicadoDTO comunicadoDTO) {
        try {
            ComunicadoSimplesDTO comunicado = comunicadoService.criarComunicadoPorCoordenador(coordenadorId, comunicadoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(comunicado);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao criar comunicado: " + e.getMessage(), e);
        }
    }

    // **Criação de comunicado por professor**
    @Operation(summary = "Criar comunicado por professor", description = "Cria um comunicado emitido por um professor para um ou mais alunos ou turmas.")
    @PostMapping("/professor/{professorId}")
    public ResponseEntity<ComunicadoSimplesDTO> criarComunicadoPorProfessor(
            @PathVariable String professorId, @RequestBody ComunicadoDTO comunicadoDTO) {
        try {
            ComunicadoSimplesDTO comunicado = comunicadoService.criarComunicadoPorProfessor(professorId, comunicadoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(comunicado);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao criar comunicado: " + e.getMessage(), e);
        }
    }

    // **Listar comunicados de um aluno específico (por CPF ou e-mail)**
    @Operation(summary = "Listar comunicados de aluno", description = "Lista todos os comunicados enviados para um aluno específico.")
    @GetMapping("/aluno/{identificador}")
    public ResponseEntity<List<ComunicadoDetalhadoDTO>> listarComunicadosPorAluno(@PathVariable String identificador) {
        try {
            List<ComunicadoDetalhadoDTO> comunicados = comunicadoService.listarComunicadosPorAluno(identificador);
            return ResponseEntity.ok(comunicados);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao listar comunicados: " + e.getMessage(), e);
        }
    }

    // **Listar comunicados de uma turma específica**
    @Operation(summary = "Listar comunicados de turma", description = "Lista todos os comunicados enviados para uma turma específica.")
    @GetMapping("/turma/{turmaId}")
    public ResponseEntity<List<ComunicadoDetalhadoDTO>> listarComunicadosPorTurma(@PathVariable Long turmaId) {
        try {
            List<ComunicadoDetalhadoDTO> comunicados = comunicadoService.listarComunicadosPorTurma(turmaId);
            return ResponseEntity.ok(comunicados);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao listar comunicados: " + e.getMessage(), e);
        }
    }

    // **Listar comunicados de um coordenador específico (por CPF ou e-mail)**
    @GetMapping("/coordenador/{identificador}")
    public ResponseEntity<List<ComunicadoSimplificado2DTO>> listarComunicadosPorCoordenador(
            @PathVariable String identificador) {
        try {
            List<ComunicadoSimplificado2DTO> comunicados = comunicadoService.listarComunicadosPorCoordenador(identificador);
            return ResponseEntity.ok(comunicados);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao listar comunicados: " + e.getMessage(), e);
        }
    }

    // **Listar comunicados de um professor específico (por CPF ou e-mail)**
    @Operation(summary = "Listar comunicados enviados por professor", description = "Lista os comunicados enviados por um professor específico.")
    @GetMapping("/professor/{identificador}")
    public ResponseEntity<List<ComunicadoSimplificado2DTO>> listarComunicadosPorProfessor(@PathVariable String identificador) {
        try {
            List<ComunicadoSimplificado2DTO> comunicados = comunicadoService.listarComunicadosPorProfessor(identificador);
            return ResponseEntity.ok(comunicados);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao listar comunicados: " + e.getMessage(), e);
        }
    }


    // **Atualizar comunicado**
    @Operation(summary = "Atualizar comunicado", description = "Atualiza um comunicado já existente.")
    @PutMapping("/{comunicadoId}")
    public ResponseEntity<ComunicadoSimplesDTO> atualizarComunicado(
            @PathVariable Long comunicadoId, @RequestBody ComunicadoDTO comunicadoDTO) {
        try {
            ComunicadoSimplesDTO comunicadoAtualizado = comunicadoService.atualizarComunicado(comunicadoId, comunicadoDTO);
            return ResponseEntity.ok(comunicadoAtualizado);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao atualizar comunicado: " + e.getMessage(), e);
        }
    }

    // **Deletar comunicado**
    @Operation(summary = "Deletar comunicado", description = "Deleta um comunicado existente.")
    @DeleteMapping("/{comunicadoId}")
    public ResponseEntity<Void> deletarComunicado(@PathVariable Long comunicadoId) {
        try {
            comunicadoService.deletarComunicado(comunicadoId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao deletar comunicado: " + e.getMessage(), e);
        }
    }
}

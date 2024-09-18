package projeto.integrador3.senac.mediotec.pi3_mediotec.conceito;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/conceitos")
@Tag(name = "Conceitos", description = "Operações relacionadas aos conceitos dos alunos")  // Nome para o Swagger
public class ConceitoController {

    private static final Logger logger = LoggerFactory.getLogger(ConceitoController.class);

    @Autowired
    private ConceitoService conceitoService;

    // Rota para listar todos os conceitos (GET) - retorna o DTO completo
    @Operation(summary = "Listar todos os conceitos", description = "Retorna uma lista de todos os conceitos cadastrados")
    @GetMapping
    public ResponseEntity<List<ConceitoDTO>> listarConceitos() {
        logger.info("Listando todos os conceitos.");
        List<ConceitoDTO> conceitos = conceitoService.listarConceitos();
        return ResponseEntity.ok(conceitos);
    }

    // Rota para buscar conceito por ID (GET) - retorna o DTO completo
    @Operation(summary = "Buscar conceito por ID", description = "Retorna um conceito com base no seu ID")
    @GetMapping("/{id}")
    public ResponseEntity<ConceitoDTO> buscarConceitoPorId(@PathVariable Long id) {
        logger.info("Buscando conceito com ID: {}", id);
        Optional<ConceitoDTO> conceito = conceitoService.buscarConceitoPorId(id);
        return conceito.map(ResponseEntity::ok)
                       .orElseGet(() -> {
                           logger.error("Conceito com ID {} não encontrado.", id);
                           return ResponseEntity.notFound().build();
                       });
    }

    // Rota para criar um novo conceito (POST) - usa ConceitoResumidoDTO
    @Operation(summary = "Criar um novo conceito", description = "Cria um novo conceito com base nos dados fornecidos")
    @PostMapping
    public ResponseEntity<ConceitoDTO> criarConceito(@RequestBody ConceitoResumidoDTO conceitoResumidoDTO) {
        try {
            logger.info("Criando um novo conceito para o aluno com ID: {}", conceitoResumidoDTO.getAlunoId());
            ConceitoDTO novoConceito = conceitoService.salvarConceito(conceitoResumidoDTO);
            return ResponseEntity.ok(novoConceito);
        } catch (RuntimeException e) {
            logger.error("Erro ao criar conceito: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Rota para atualizar um conceito existente (PUT) - usa ConceitoResumidoDTO
    @Operation(summary = "Atualizar conceito", description = "Atualiza um conceito existente com base no seu ID")
    @PutMapping("/{id}")
    public ResponseEntity<ConceitoDTO> atualizarConceito(@PathVariable Long id, @RequestBody ConceitoResumidoDTO conceitoResumidoDTO) {
        try {
            logger.info("Atualizando conceito com ID: {}", id);
            ConceitoDTO conceitoAtualizado = conceitoService.atualizarConceito(id, conceitoResumidoDTO);
            return ResponseEntity.ok(conceitoAtualizado);
        } catch (RuntimeException e) {
            logger.error("Erro ao atualizar conceito com ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    // Rota para deletar um conceito (DELETE)
    @Operation(summary = "Deletar conceito", description = "Deleta um conceito com base no seu ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarConceito(@PathVariable Long id) {
        try {
            logger.info("Deletando conceito com ID: {}", id);
            conceitoService.deletarConceito(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            logger.error("Erro ao deletar conceito com ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}

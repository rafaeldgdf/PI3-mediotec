package projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

import org.springframework.web.server.ResponseStatusException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/coordenadores")
@Tag(name = "Coordenador", description = "Gerenciamento dos Coordenadores")
public class CoordenadorController {

    @Autowired
    private CoordenadorService coordenadorService;

    // Lista todos os coordenadores
    @Operation(summary = "Listar todos os coordenadores", description = "Retorna uma lista de todos os coordenadores cadastrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de coordenadores retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor ao listar coordenadores")
    })
    @GetMapping
    public ResponseEntity<List<CoordenadorDTO>> getAllCoordenadores() {
        try {
            List<CoordenadorDTO> coordenadores = coordenadorService.getAllCoordenadores();
            return new ResponseEntity<>(coordenadores, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar coordenadores", e);
        }
    }

    // Busca um coordenador por ID
    @Operation(summary = "Buscar coordenador por ID", description = "Retorna os detalhes de um coordenador específico com base no ID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Coordenador retornado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Coordenador não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor ao buscar coordenador")
    })
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

    // Cria um novo coordenador
    @Operation(summary = "Criar novo coordenador", description = "Cria um novo coordenador com os dados fornecidos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Coordenador criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro de validação ao criar o coordenador"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor ao criar coordenador")
    })
    @PostMapping
    public ResponseEntity<CoordenadorDTO> createCoordenador(@RequestBody CoordenadorDTO coordenadorDTO) {
        try {
            CoordenadorDTO savedCoordenador = coordenadorService.saveCoordenador(coordenadorDTO);
            return new ResponseEntity<>(savedCoordenador, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao criar coordenador", e);
        }
    }

    // Atualiza um coordenador existente
    @Operation(summary = "Atualizar coordenador", description = "Atualiza os dados de um coordenador existente com base no ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Coordenador atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Coordenador não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor ao atualizar coordenador")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CoordenadorDTO> updateCoordenador(@PathVariable String id, @RequestBody CoordenadorDTO coordenadorDTO) {
        try {
            CoordenadorDTO updatedCoordenador = coordenadorService.updateCoordenador(id, coordenadorDTO);
            return new ResponseEntity<>(updatedCoordenador, HttpStatus.OK);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Coordenador não encontrado", e);
        }
    }

    // Deleta um coordenador por ID
    @Operation(summary = "Deletar coordenador", description = "Deleta um coordenador existente com base no ID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Coordenador deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Coordenador não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor ao deletar coordenador")
    })
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

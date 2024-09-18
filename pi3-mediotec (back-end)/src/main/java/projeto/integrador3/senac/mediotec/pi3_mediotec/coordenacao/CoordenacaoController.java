package projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/coordenacoes")
@Tag(name = "Coordenação", description = "Gerenciamento das Coordenações")
public class CoordenacaoController {

    @Autowired
    private CoordenacaoService coordenacaoService;

    @GetMapping
    public ResponseEntity<List<CoordenacaoDTO>> getAllCoordenacoes() {
        try {
            List<CoordenacaoDTO> coordenacoes = coordenacaoService.getAllCoordenacoes();
            return new ResponseEntity<>(coordenacoes, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar coordenações", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoordenacaoDTO> getCoordenacaoById(@PathVariable Long id) {
        try {
            Optional<CoordenacaoDTO> coordenacao = coordenacaoService.getCoordenacaoById(id);
            return coordenacao.map(ResponseEntity::ok)
                              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coordenação não encontrada"));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar coordenação", e);
        }
    }

    @PostMapping
    public ResponseEntity<CoordenacaoDTO> createCoordenacao(@RequestBody CoordenacaoCadastroDTO coordenacaoCadastroDTO) {
        try {
            CoordenacaoDTO savedCoordenacao = coordenacaoService.saveCoordenacao(coordenacaoCadastroDTO);
            return new ResponseEntity<>(savedCoordenacao, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao criar coordenação", e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CoordenacaoDTO> updateCoordenacao(@PathVariable Long id, @RequestBody CoordenacaoCadastroDTO coordenacaoCadastroDTO) {
        try {
            CoordenacaoDTO updatedCoordenacao = coordenacaoService.updateCoordenacao(id, coordenacaoCadastroDTO);
            return new ResponseEntity<>(updatedCoordenacao, HttpStatus.OK);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Coordenação não encontrada", e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoordenacao(@PathVariable Long id) {
        try {
            coordenacaoService.deleteCoordenacao(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Coordenação não encontrada", e);
        }
    }
}

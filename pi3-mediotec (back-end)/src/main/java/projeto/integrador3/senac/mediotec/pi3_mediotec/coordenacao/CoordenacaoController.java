package projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/coordenacoes")
public class CoordenacaoController {

    @Autowired
    private CoordenacaoService coordenacaoService;

    // Retorna todas as coordenacoes
    @GetMapping
    public ResponseEntity<List<CoordenacaoDTO>> getAllCoordenacoes() {
        List<CoordenacaoDTO> coordenacoes = coordenacaoService.getAllCoordenacoes();
        return new ResponseEntity<>(coordenacoes, HttpStatus.OK);
    }

    // Retorna uma coordenacao específica por ID
    @GetMapping("/{id}")
    public ResponseEntity<CoordenacaoDTO> getCoordenacaoById(@PathVariable Long id) {
        Optional<CoordenacaoDTO> coordenacao = coordenacaoService.getCoordenacaoById(id);
        return coordenacao.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                          .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Cria uma nova coordenacao (utilizando o DTO CoordenacaoCadastroDTO para envio de IDs e dados completos de endereços/telefones)
    @PostMapping
    public ResponseEntity<CoordenacaoDTO> createCoordenacao(@RequestBody CoordenacaoCadastroDTO coordenacaoCadastroDTO) {
        CoordenacaoDTO savedCoordenacao = coordenacaoService.saveCoordenacao(coordenacaoCadastroDTO);
        return new ResponseEntity<>(savedCoordenacao, HttpStatus.CREATED);
    }

    // Atualiza uma coordenacao existente (utilizando o CoordenacaoCadastroDTO)
    @PutMapping("/{id}")
    public ResponseEntity<CoordenacaoDTO> updateCoordenacao(@PathVariable Long id, @RequestBody CoordenacaoCadastroDTO coordenacaoCadastroDTO) {
        try {
            CoordenacaoDTO updatedCoordenacao = coordenacaoService.updateCoordenacao(id, coordenacaoCadastroDTO);
            return new ResponseEntity<>(updatedCoordenacao, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Deleta uma coordenacao existente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoordenacao(@PathVariable Long id) {
        try {
            coordenacaoService.deleteCoordenacao(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

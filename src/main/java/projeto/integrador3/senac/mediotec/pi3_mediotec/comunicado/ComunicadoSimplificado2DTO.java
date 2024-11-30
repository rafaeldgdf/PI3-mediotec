package projeto.integrador3.senac.mediotec.pi3_mediotec.comunicado;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComunicadoSimplificado2DTO {
    private Long id;
    private String titulo;
    private String conteudo;
    private LocalDateTime dataEnvio;
    private RemetenteCoordenadorDTO remetente;
    private List<DestinatarioDTO> destinatarios;
}

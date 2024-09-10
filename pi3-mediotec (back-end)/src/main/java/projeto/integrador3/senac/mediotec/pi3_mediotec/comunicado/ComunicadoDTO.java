package projeto.integrador3.senac.mediotec.pi3_mediotec.comunicado;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComunicadoDTO {

    private Long idComunicado;
    private String conteudo;
    private Date dataEnvio;
    private Long emitenteId;
    private Long receptorId;
}

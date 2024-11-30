package projeto.integrador3.senac.mediotec.pi3_mediotec.comunicado;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DestinatarioDTO {
    private String tipo; // "aluno" ou "turma"
    private String nome;
}

package projeto.integrador3.senac.mediotec.pi3_mediotec.login;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String tipoUsuario;  // Coordenador, Professor, ou Aluno
    private Object usuario;      // Objeto correspondente ao tipo
}

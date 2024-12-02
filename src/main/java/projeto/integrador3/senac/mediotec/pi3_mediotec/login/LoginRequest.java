package projeto.integrador3.senac.mediotec.pi3_mediotec.login;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String identificador; // Email ou identificador do usuário
    private String senha;         // Senha do usuário

    @Override
    public String toString() {
        return "LoginRequest{" +
                "identificador='" + identificador + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }
}

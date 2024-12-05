package projeto.integrador3.senac.mediotec.pi3_mediotec.usuario;

import java.util.Optional;

public interface UsuarioCustomRepository {
    Optional<Object[]> findUserByEmailAndPassword(String email, String senha);
}

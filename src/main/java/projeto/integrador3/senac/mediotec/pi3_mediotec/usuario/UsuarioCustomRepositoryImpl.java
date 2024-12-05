package projeto.integrador3.senac.mediotec.pi3_mediotec.usuario;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UsuarioCustomRepositoryImpl implements UsuarioCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Object[]> findUserByEmailAndPassword(String email, String senha) {
        try {
            // Consulta por Coordenador
            Object coordenador = entityManager.createQuery(
                    "SELECT c FROM Coordenador c WHERE c.email = :email AND c.senha = :senha"
            )
            .setParameter("email", email)
            .setParameter("senha", senha)
            .getSingleResult();

            return Optional.of(new Object[]{"coordenador", coordenador});
        } catch (Exception e1) {
            try {
                // Consulta por Professor
                Object professor = entityManager.createQuery(
                        "SELECT p FROM Professor p WHERE p.email = :email AND p.senha = :senha"
                )
                .setParameter("email", email)
                .setParameter("senha", senha)
                .getSingleResult();

                return Optional.of(new Object[]{"professor", professor});
            } catch (Exception e2) {
                try {
                    // Consulta por Aluno
                    Object aluno = entityManager.createQuery(
                            "SELECT a FROM Aluno a WHERE a.email = :email AND a.senha = :senha"
                    )
                    .setParameter("email", email)
                    .setParameter("senha", senha)
                    .getSingleResult();

                    return Optional.of(new Object[]{"aluno", aluno});
                } catch (Exception e3) {
                    // Nenhum usuário encontrado
                    return Optional.empty();
                }
            }
        }
    }
}

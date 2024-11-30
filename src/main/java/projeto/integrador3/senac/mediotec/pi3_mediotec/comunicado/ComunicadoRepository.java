package projeto.integrador3.senac.mediotec.pi3_mediotec.comunicado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComunicadoRepository extends JpaRepository<Comunicado, Long> {

    // Buscar comunicados enviados por um professor específico (usando CPF)
    List<Comunicado> findByRemetenteProfessor_Cpf(String cpf);

    // Buscar comunicados enviados por um professor específico (usando e-mail)
    List<Comunicado> findByRemetenteProfessor_Email(String email);

    // Buscar comunicados enviados por um coordenador específico (usando CPF)
    List<Comunicado> findByRemetenteCoordenacao_Coordenadores_Cpf(String cpf);

    // Buscar comunicados enviados por um coordenador específico (usando e-mail)
    List<Comunicado> findByRemetenteCoordenacao_Coordenadores_Email(String email);

    // Buscar comunicados recebidos por um aluno específico (usando ID do aluno)
    List<Comunicado> findByReceptorAlunosContaining(Long alunoId);

    // Buscar comunicados recebidos por uma turma específica (referenciada por ID)
    List<Comunicado> findByReceptorTurmasContaining(Long turmaId);

    // Buscar comunicados recebidos por alunos utilizando CPF
    @Query("SELECT c FROM Comunicado c " +
           "JOIN Aluno a ON a.id IN elements(c.receptorAlunos) " +
           "WHERE a.cpf = :cpf")
    List<Comunicado> findByReceptorAlunosCpf(@Param("cpf") String cpf);

    // Buscar comunicados recebidos por alunos utilizando e-mail
    @Query("SELECT c FROM Comunicado c " +
           "JOIN Aluno a ON a.id IN elements(c.receptorAlunos) " +
           "WHERE a.email = :email")
    List<Comunicado> findByReceptorAlunosEmail(@Param("email") String email);
}

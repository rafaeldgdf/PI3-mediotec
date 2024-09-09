package projeto.integrador3.senac.mediotec.pi3_mediotec.presenca;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.Aluno;

import java.util.Date;
import java.util.List;

@Repository
public interface PresencaRepository extends JpaRepository<Presenca, Long> {
	List<Presenca> findByAluno(Aluno aluno);
}

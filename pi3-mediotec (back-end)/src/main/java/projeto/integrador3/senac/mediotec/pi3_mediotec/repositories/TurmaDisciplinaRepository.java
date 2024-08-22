package projeto.integrador3.senac.mediotec.pi3_mediotec.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.TurmaDisciplina;
import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.TurmaDisciplinaId;

public interface TurmaDisciplinaRepository extends JpaRepository<TurmaDisciplina, TurmaDisciplinaId> {
}


package projeto.integrador3.senac.mediotec.pi3_mediotec.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.Presenca;

import java.util.Date;
import java.util.List;

@Repository
public interface PresencaRepository extends JpaRepository<Presenca, Long> {

}

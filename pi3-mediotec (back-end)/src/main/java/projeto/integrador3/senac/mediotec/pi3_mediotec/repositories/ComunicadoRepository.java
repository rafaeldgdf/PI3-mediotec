package projeto.integrador3.senac.mediotec.pi3_mediotec.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.integrador3.senac.mediotec.pi3_mediotec.entities.Comunicado;

public interface ComunicadoRepository extends JpaRepository<Comunicado, Long> {
}

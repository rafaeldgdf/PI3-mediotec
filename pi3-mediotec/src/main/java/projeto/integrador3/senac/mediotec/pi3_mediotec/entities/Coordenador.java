package projeto.integrador3.senac.mediotec.pi3_mediotec.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Table(name = "coordenador")
public class Coordenador extends Usuario{
	
   	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   	@Column(name = "id_coordenador")
    private Long id;
   	

}

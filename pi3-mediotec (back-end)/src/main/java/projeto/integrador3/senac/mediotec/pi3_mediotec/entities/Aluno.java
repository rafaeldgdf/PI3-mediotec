package projeto.integrador3.senac.mediotec.pi3_mediotec.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Table(name = "aluno")
public class Aluno extends Usuario {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matricula_aluno")
    private Long id_aluno;
    
    @Size(min = 11, max = 11, message = "{usuario.cpf.size}")
    @Column(nullable = true, unique = true)
    private String cpf;
    
    @ManyToOne 
   	private Coordenacao coordenacao;
    
    

}

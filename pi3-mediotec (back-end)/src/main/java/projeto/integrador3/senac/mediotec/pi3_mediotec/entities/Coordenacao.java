package projeto.integrador3.senac.mediotec.pi3_mediotec.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
@Table(name = "coordenacao")
public class Coordenacao {
	
    private static final long serialVersionUID = 1L;
    
    @Id
    private Long id_coordenacao;
    
    
    @NotNull(message = "{coordenacao.nome.notnull}")
    @Size(min = 3, max = 50, message = "{coordenacao.nome.size}")
    @Column(nullable = false)
    private String nome;
    
    @Size(max = 50)
    @Column
    private String descricao;
    
    @OneToOne 
    private Coordenador coordenador;

}

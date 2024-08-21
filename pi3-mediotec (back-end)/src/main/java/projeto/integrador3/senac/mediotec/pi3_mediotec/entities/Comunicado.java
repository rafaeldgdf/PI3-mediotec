package projeto.integrador3.senac.mediotec.pi3_mediotec.entities;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comunicado")
public class Comunicado implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;
    
    @NotNull(message = "{coordenacao.nome.notnull}")
    @Size(min = 3, max = 50, message = "{coordenacao.nome.size}")
    @Column(nullable = false)
    private String conteudo;
    
    
	

}

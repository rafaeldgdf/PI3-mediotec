package projeto.integrador3.senac.mediotec.pi3_mediotec.entities;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Getter
@Setter
@Table(name = "coordenador")
public class Coordenador extends Usuario{
	
	private static final long serialVersionUID = 1L;
	
   	@Id
    @NotNull(message = "{usuario.cpf.notnull}")
    @Size(min = 11, max = 11, message = "{usuario.cpf.size}")
    @Column(nullable = false, unique = true)
    private String cpf;
   	
    @OneToMany(mappedBy = "coordenador", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Endereco> enderecos;

    @OneToMany(mappedBy = "coordenador", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Telefone> telefones;
   	
    @OneToOne
    @JoinColumn(name = "id_coordenacao", referencedColumnName = "id_coordenacao") 
    private Coordenacao coordenacao;
}

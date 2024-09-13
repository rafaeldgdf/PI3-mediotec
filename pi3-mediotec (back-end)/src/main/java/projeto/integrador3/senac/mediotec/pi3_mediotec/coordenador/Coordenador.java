package projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.Coordenacao;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.Endereco;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.Telefone;
import projeto.integrador3.senac.mediotec.pi3_mediotec.usuario.Usuario;

@Entity
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "coordenador")
public class Coordenador extends Usuario{
	
	private static final long serialVersionUID = 1L;
	
   	@Id
    @NotNull(message = "{usuario.cpf.notnull}")
    @Size(min = 1, max = 50, message = "{usuario.cpf.size}")
    @Column(nullable = false, unique = true)
    private String cpf;
   	
    @OneToMany(mappedBy = "coordenador", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(nullable = true)
    private Set<Endereco> enderecos;

    @OneToMany(mappedBy = "coordenador", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(nullable = true)
    private Set<Telefone> telefones;
    
    @ManyToOne
    @JoinColumn(name = "id_coordenacao") // Chave estrangeira para a tabela coordenacao
    private Coordenacao coordenacao;
   	
    
    //funcoes para configurar as bilateridades	 
    public void addEndereco(Endereco endereco) {
        endereco.setCoordenador(this); 
        this.enderecos.add(endereco);
    }

    public void addTelefone(Telefone telefone) {
        telefone.setCoordenador(this); 
        this.telefones.add(telefone);
    }
    
    
}

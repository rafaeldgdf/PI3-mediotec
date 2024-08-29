package projeto.integrador3.senac.mediotec.pi3_mediotec.entities;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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

@Entity
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor 
@Table(name = "professor")
public class Professor extends Usuario {
	
	private static final long serialVersionUID = 1L;

    @Id
    @NotNull(message = "{usuario.cpf.notnull}")
    @Size(min = 11, max = 11, message = "{usuario.cpf.size}")
    @Column(nullable = false, unique = true)
    private String cpf;
    

    @ManyToOne
    private Coordenacao coordenacao;
    
    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(nullable = false)
    private Set<Endereco> enderecos;
    
    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(nullable = false)
    private Set<Telefone> telefones;
    
    @JsonIgnore
    @OneToMany(mappedBy = "professor")
    private Set<TurmaDisciplina> turmaDisciplinas;
    
    //funcoes para configurar as bilateridades 
    public void addEndereco(Endereco endereco) {
        endereco.setProfessor(this); 
        this.enderecos.add(endereco);
    }

    public void addTelefone(Telefone telefone) {
        telefone.setProfessor(this); 
        this.telefones.add(telefone);
    }
}

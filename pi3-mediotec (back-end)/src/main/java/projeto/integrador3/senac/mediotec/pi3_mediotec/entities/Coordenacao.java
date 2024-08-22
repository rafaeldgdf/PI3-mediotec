package projeto.integrador3.senac.mediotec.pi3_mediotec.entities;

import java.io.Serializable;
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
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
@Table(name = "coordenacao")
public class Coordenacao implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_coordenacao;
    
    
    @NotNull(message = "{coordenacao.nome.notnull}")
    @Size(min = 3, max = 50, message = "{coordenacao.nome.size}")
    @Column(nullable = false)
    private String nome;
    
    @Size(max = 50)
    @Column
    private String descricao;
    
    @OneToMany(mappedBy = "coordenacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Endereco> enderecos;

    @OneToMany(mappedBy = "coordenacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Telefone> telefones;
    
    @OneToOne(mappedBy = "coordenacao") 
    private Coordenador coordenador;
}

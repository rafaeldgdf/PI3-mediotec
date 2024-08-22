package projeto.integrador3.senac.mediotec.pi3_mediotec.entities;

import java.util.Set;

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
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Table(name = "professor")
public class Professor extends Usuario {

    @Id
    @NotNull(message = "{usuario.cpf.notnull}")
    @Size(min = 11, max = 11, message = "{usuario.cpf.size}")
    @Column(nullable = false, unique = true)
    private String cpf;

    @ManyToOne
    private Coordenacao coordenacao;
    
    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Endereco> enderecos;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Telefone> telefones;

    @OneToMany(mappedBy = "professor")
    private Set<TurmaDisciplina> turmaDisciplinas;
}

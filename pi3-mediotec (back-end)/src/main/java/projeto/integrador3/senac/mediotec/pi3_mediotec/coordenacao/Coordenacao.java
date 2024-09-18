package projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.Coordenador;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.Endereco;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.Professor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.Telefone;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.Turma;


@Data
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "coordenacao")
public class Coordenacao implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_coordenacao")
    private Long id;
    
    @NotNull(message = "{coordenacao.nome.notnull}")
    @Size(min = 3, max = 50, message = "{coordenacao.nome.size}")
    @Column(nullable = false)
    private String nome;
    
    @Size(max = 50)
    @Column
    private String descricao;
    
    @Builder.Default
    @OneToMany(mappedBy = "coordenacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Endereco> enderecos = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "coordenacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Telefone> telefones = new HashSet<>();
    
    @OneToMany(mappedBy = "coordenacao", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(nullable = false)
    private Set<Coordenador> coordenadores;
    
    @JsonIgnore
    @OneToMany(mappedBy = "coordenacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Turma> turmas;

    @JsonIgnore
    @OneToMany(mappedBy = "coordenacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Professor> professores;
    
//funcoes para configurar as bilateridades 
    public void addEndereco(Endereco endereco) {
        endereco.setCoordenacao(this); 
        this.enderecos.add(endereco);
    }

    public void addTelefone(Telefone telefone) {
        telefone.setCoordenacao(this); 
        this.telefones.add(telefone);
    }
    
    public void addTurma(Turma turma) {
        turma.setCoordenacao(this);
        this.turmas.add(turma);
    }

    public void addProfessor(Professor professor) {
        professor.setCoordenacao(this);
        this.professores.add(professor);
    }
    

    public void addCoordenador(Coordenador coordenador) {
        coordenador.setCoordenacao(this); // Configura a relação inversa
        this.coordenadores.add(coordenador);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id); // Baseado apenas no ID para evitar ciclos
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordenacao that = (Coordenacao) o;
        return Objects.equals(id, that.id); // Comparando pelo ID
    }
}

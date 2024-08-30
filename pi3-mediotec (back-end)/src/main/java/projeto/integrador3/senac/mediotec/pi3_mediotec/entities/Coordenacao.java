package projeto.integrador3.senac.mediotec.pi3_mediotec.entities;

import java.io.Serializable;
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
    private Long id_coordenacao;
    
    @NotNull(message = "{coordenacao.nome.notnull}")
    @Size(min = 3, max = 50, message = "{coordenacao.nome.size}")
    @Column(nullable = false)
    private String nome;
    
    @Size(max = 50)
    @Column
    private String descricao;
    
    @OneToMany(mappedBy = "coordenacao", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(nullable = false)
    private Set<Endereco> enderecos;

    @OneToMany(mappedBy = "coordenacao", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(nullable = false)
    private Set<Telefone> telefones;
    
    @OneToOne
    private Coordenador coordenador;
    
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
    
    @Override
    public int hashCode() {
        return Objects.hash(id_coordenacao); // Baseado apenas no ID para evitar ciclos
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordenacao that = (Coordenacao) o;
        return Objects.equals(id_coordenacao, that.id_coordenacao); // Comparando pelo ID
    }
}

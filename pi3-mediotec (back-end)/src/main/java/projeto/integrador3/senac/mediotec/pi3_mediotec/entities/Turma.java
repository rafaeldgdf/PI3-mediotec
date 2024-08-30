package projeto.integrador3.senac.mediotec.pi3_mediotec.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter 
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "turma")
public class Turma implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_turma;

    @NotNull(message = "{turma.nome.notnull}")
    @Size(min = 3, max = 100, message = "{turma.nome.size}")
    @Column(nullable = false)
    private String nome;

    @NotNull(message = "{turma.ano.notnull}")
    @Column(nullable = false)
    private int ano;
    
    @JsonIgnore
    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
        name = "aluno_turma",
        joinColumns = @JoinColumn(name = "turma_id"),
        inverseJoinColumns = @JoinColumn(name = "aluno_id")
    )
    private List<Aluno> alunos = new ArrayList<>();
    
    @ManyToOne
    @JoinColumn(name = "id_coordenacao")
    private Coordenacao coordenacao;

    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TurmaDisciplina> turmaDisciplinas = new HashSet<>();
    
    // Método extra para configurar a bilateralidade
    public void addAluno(Aluno aluno) {
        this.alunos.add(aluno);
        aluno.getTurmas().add(this);  
    }

    // Método extra para remover a associação bilateralmente
    public void removeAluno(Aluno aluno) {
        this.alunos.remove(aluno);
        aluno.getTurmas().remove(this);
    }
}

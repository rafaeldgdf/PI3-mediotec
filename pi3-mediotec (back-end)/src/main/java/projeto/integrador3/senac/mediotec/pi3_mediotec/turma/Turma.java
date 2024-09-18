package projeto.integrador3.senac.mediotec.pi3_mediotec.turma;

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
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.Aluno;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.Coordenacao;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessor;

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
    @Column(name = "id_turma")
    private Long id;

   
    private String nome;

    @NotNull(message = "{turma.ano.notnull}")
    @Column(nullable = false)
    private int anoLetivo;
    
    @Column
    private String anoEscolar;
    
    @Column
    private String turno;
    
    @Column
    private boolean status;
    
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

    // Relacionamento com TurmaDisciplinaProfessor
    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<TurmaDisciplinaProfessor> turmaDisciplinaProfessores = new HashSet<>();

    // Método extra para adicionar uma relação entre a turma e TurmaDisciplinaProfessor
    public void addTurmaDisciplinaProfessor(TurmaDisciplinaProfessor turmaDisciplinaProfessor) {
        turmaDisciplinaProfessores.add(turmaDisciplinaProfessor);
        turmaDisciplinaProfessor.setTurma(this);
    }

    // Método extra para remover uma relação entre a turma e TurmaDisciplinaProfessor
    public void removeTurmaDisciplinaProfessor(TurmaDisciplinaProfessor turmaDisciplinaProfessor) {
        turmaDisciplinaProfessores.remove(turmaDisciplinaProfessor);
        turmaDisciplinaProfessor.setTurma(null);
    }

    // Método extra para configurar a bilateralidade
    public void addAluno(Aluno aluno) {
        this.alunos.add(aluno);
        aluno.getTurmas().add(this);
    }

    public void removeAluno(Aluno aluno) {
        this.alunos.remove(aluno);
        aluno.getTurmas().remove(this);
    }
}

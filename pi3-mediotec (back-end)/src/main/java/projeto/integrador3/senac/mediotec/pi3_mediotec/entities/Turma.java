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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
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
    
    
    @ManyToOne
    @JoinColumn(name = "id_coordenacao")
   	private Coordenacao coordenacao;

    @OneToMany(mappedBy = "turma")
    private Set<TurmaDisciplina> turmaDisciplinas;
}

package projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.Coordenacao;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessor;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "disciplina")
public class Disciplina implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_disciplina;

    @NotNull(message = "{disciplina.nome.notnull}")
    @Size(min = 3, max = 100, message = "{disciplina.nome.size}")
    @Column(nullable = false)
    private String nome;

    @NotNull(message = "{disciplina.ch.notnull}")
    @Column(nullable = false)
    private int carga_horaria;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_coordenacao", nullable = true)
    private Coordenacao coordenacao;
    
    
    @JsonIgnore
    @OneToMany(mappedBy = "disciplina")
    private Set<TurmaDisciplinaProfessor> turmaDisciplinaProfessores; // Atualizado para refletir a nova entidade
}


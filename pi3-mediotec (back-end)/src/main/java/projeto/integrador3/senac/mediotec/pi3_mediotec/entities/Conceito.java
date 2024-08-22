package projeto.integrador3.senac.mediotec.pi3_mediotec.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "conceito")
public class Conceito implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_conceito;

    @NotNull(message = "{conceito.nota.notnull}")
    @Column(nullable = false)
    private Float nota;

    @Column
    private String conceito;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "id_turma", referencedColumnName = "id_turma"),
        @JoinColumn(name = "id_disciplina", referencedColumnName = "id_disciplina")
    })
    private TurmaDisciplina turmaDisciplina;
    
    @ManyToOne
    @JoinColumn(name = "id_aluno", nullable = false)
    private Aluno aluno;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_coordenacao", nullable = true)
    private Coordenacao coordenacao;

}

	package projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.Disciplina;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.Professor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.Turma;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "turma_disciplina_professor")
public class TurmaDisciplinaProfessor {

    @EmbeddedId
    private TurmaDisciplinaProfessorId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("turmaId")
    @JoinColumn(name = "id_turma", nullable = false)
    private Turma turma;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("disciplinaId")
    @JoinColumn(name = "id_disciplina", nullable = false)
    private Disciplina disciplina;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("professorId")
    @JoinColumn(name = "id_professor", nullable = false)
    private Professor professor;

    // getters e setters
}

package projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class TurmaDisciplinaProfessorId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "id_turma")
    private Long turmaId;

    @Column(name = "id_disciplina")
    private Long disciplinaId;

    @Column(name = "id_professor")
    private String professorId;

    // Equals e hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TurmaDisciplinaProfessorId that = (TurmaDisciplinaProfessorId) o;
        return Objects.equals(turmaId, that.turmaId) &&
               Objects.equals(disciplinaId, that.disciplinaId) &&
               Objects.equals(professorId, that.professorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(turmaId, disciplinaId, professorId);
    }
}


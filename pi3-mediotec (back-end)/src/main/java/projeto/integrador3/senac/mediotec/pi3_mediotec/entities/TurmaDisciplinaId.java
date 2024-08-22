package projeto.integrador3.senac.mediotec.pi3_mediotec.entities;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class TurmaDisciplinaId implements Serializable {

    @Column(name = "id_turma")
    private Long turmaId;

    @Column(name = "id_disciplina")
    private Long disciplinaId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TurmaDisciplinaId that = (TurmaDisciplinaId) o;
        return Objects.equals(turmaId, that.turmaId) && 
               Objects.equals(disciplinaId, that.disciplinaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(turmaId, disciplinaId);
    }
}

package projeto.integrador3.senac.mediotec.pi3_mediotec.entities;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
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
public class TurmaDisciplinaId implements Serializable {
	
	private static final long serialVersionUID = 1L;

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

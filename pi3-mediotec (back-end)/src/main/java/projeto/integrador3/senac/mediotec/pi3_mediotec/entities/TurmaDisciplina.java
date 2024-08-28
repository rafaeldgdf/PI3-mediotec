package projeto.integrador3.senac.mediotec.pi3_mediotec.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "turma_disciplina")
public class TurmaDisciplina implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private TurmaDisciplinaId id;

    @MapsId("turmaId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_turma")
    private Turma turma;

    @MapsId("disciplinaId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_disciplina")
    private Disciplina disciplina;

  
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_professor", nullable = false)
    private Professor professor;

}

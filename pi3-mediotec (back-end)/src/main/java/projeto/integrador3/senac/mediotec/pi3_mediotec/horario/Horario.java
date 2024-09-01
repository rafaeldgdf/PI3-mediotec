package projeto.integrador3.senac.mediotec.pi3_mediotec.horario;

import java.io.Serializable;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "horario")
public class Horario implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_horario;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private DiaSemana diaSemana;
    
    @NotNull
    private LocalTime horaInicio;
    
    @NotNull
    private LocalTime horaFim;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_coordenacao", nullable = true)
    private Coordenacao coordenacao;
    
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "id_turma", referencedColumnName = "id_turma"),
        @JoinColumn(name = "id_disciplina", referencedColumnName = "id_disciplina"),
        @JoinColumn(name = "id_professor", referencedColumnName = "id_professor")
    })
    private TurmaDisciplinaProfessor turmaDisciplinaProfessor;
}

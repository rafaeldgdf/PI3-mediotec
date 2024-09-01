package projeto.integrador3.senac.mediotec.pi3_mediotec.comunicado;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.Aluno;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.Coordenacao;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.Professor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.Turma;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comunicado")
public class Comunicado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_comunicado;

    @NotNull(message = "{comunicado.conteudo.notnull}")
    @Size(min = 10, max = 100, message = "{comunicado.conteudo.size}")
    @Column(nullable = false)
    private String conteudo;

    @NotNull(message = "{coordenacao.data.notnull}")
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date data_envio;

    @ManyToOne
    @JoinColumn(name = "id_coordenacao") 
    private Coordenacao coordenacao;

    @ManyToOne
    @JoinColumn(name = "id_professor") 
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "id_aluno") 
    private Aluno aluno;
    
    @ManyToOne
    @JoinColumn(name = "id_turma") 
    private Turma turma;
}

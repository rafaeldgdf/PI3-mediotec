package projeto.integrador3.senac.mediotec.pi3_mediotec.comunicado;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.Professor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.Coordenacao;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.Coordenador;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comunicado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String conteudo;

    @Column(nullable = false)
    private LocalDateTime dataEnvio;

    // Relação com o Professor que enviou o comunicado
    @ManyToOne
    @JoinColumn(name = "remetente_professor_id")
    private Professor remetenteProfessor;

    // Relação com a Coordenação que enviou o comunicado
    @ManyToOne
    @JoinColumn(name = "remetente_coordenacao_id")
    private Coordenacao remetenteCoordenacao;
    

    // Alunos que receberam o comunicado
    @ElementCollection
    @CollectionTable(name = "comunicado_receptor_alunos", joinColumns = @JoinColumn(name = "comunicado_id"))
    @Column(name = "aluno_id")
    private List<Long> receptorAlunos;

    // Turmas que receberam o comunicado
    @ElementCollection
    @CollectionTable(name = "comunicado_receptor_turmas", joinColumns = @JoinColumn(name = "comunicado_id"))
    @Column(name = "turma_id")
    private List<Long> receptorTurmas;
}

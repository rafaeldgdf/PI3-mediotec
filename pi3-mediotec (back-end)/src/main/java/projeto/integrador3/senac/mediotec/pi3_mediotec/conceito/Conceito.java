package projeto.integrador3.senac.mediotec.pi3_mediotec.conceito;

import jakarta.persistence.*;
import lombok.*;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.Aluno;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessor;

import java.io.Serializable;

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

    @Column
    private Float notaUnidade1;

    @Column
    private Float notaUnidade2;

    @Column
    private Float notaUnidade3;

    @Column
    private Float notaUnidade4;

    @Column
    private Float noa1; // Nova Oportunidade de Aprendizado (NOA) - 1º semestre

    @Column
    private Float noa2; // Nova Oportunidade de Aprendizado (NOA) - 2º semestre

    @Column
    private Float noaFinal; // Nota da recuperação final

    @Column
    private Float mediaFinal;

    @Column
    private Boolean aprovado;

    // Conceitos (não numéricos, usados para exibir ao aluno)
    private String conceitoNota1;
    private String conceitoNota2;
    private String conceitoNota3;
    private String conceitoNota4;
    private String conceitoNoa1;
    private String conceitoNoa2;
    private String conceitoNoaFinal;
    private String conceitoFinal;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "id_turma", referencedColumnName = "id_turma"),
            @JoinColumn(name = "id_disciplina", referencedColumnName = "id_disciplina"),
            @JoinColumn(name = "id_professor", referencedColumnName = "id_professor")
    })
    private TurmaDisciplinaProfessor turmaDisciplinaProfessor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aluno", nullable = false)
    private Aluno aluno;
}

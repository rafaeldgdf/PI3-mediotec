
package projeto.integrador3.senac.mediotec.pi3_mediotec.conceito;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.*;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.Aluno;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessor;

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

    // Notas das quatro unidades
    @Column(nullable = true)
    private Float notaUnidade1;
    
    @Column(nullable = true)
    private Float notaUnidade2;
    
    @Column(nullable = true)
    private Float notaUnidade3;
    
    @Column(nullable = true)
    private Float notaUnidade4;

    // Nota da recuperação (caso necessário)
    @Column(nullable = true)
    private Float recuperacaoNota;

    // Média final calculada
    @Column(nullable = true)
    private Float mediaFinal;

    @Column(nullable = false)
    private Boolean aprovado; // Aprovação será calculada automaticamente

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

    // Calcula e atribui o conceito automaticamente com base na nota
    public String calcularConceito(Float nota) {
        if (nota == null || nota < 0 || nota > 10) {
            throw new RuntimeException("Nota inválida: a nota deve estar entre 0 e 10.");
        }
        if (nota >= 9.50) {
            return "Excelente (E)";
        } else if (nota >= 8.50) {
            return "Ótimo (O)";
        } else if (nota >= 7.00) {
            return "Bom (B)";
        } else if (nota >= 5.00) {
            return "Ainda Não Suficiente (ANS)";
        } else {
            return "Insuficiente (I)";
        }
    }

    // Calcula a média e atualiza o status de aprovação
    public void calcularMediaEStatus() {
        // Somente calcular se as 4 unidades estiverem preenchidas
        if (notaUnidade1 != null && notaUnidade2 != null && notaUnidade3 != null && notaUnidade4 != null) {
            float media = (notaUnidade1 + notaUnidade2 + notaUnidade3 + notaUnidade4) / 4;

            // Se média >= 7.0, está aprovado
            if (media >= 7.00) {
                this.aprovado = true;
                this.mediaFinal = media;
            } 
            // Caso contrário, pode ter direito à recuperação
            else if (media >= 5.00 && recuperacaoNota != null) {
                float mediaComRecuperacao = (media + recuperacaoNota) / 2;
                this.aprovado = mediaComRecuperacao >= 7.00;
                this.mediaFinal = mediaComRecuperacao;
            } 
            // Se a média final for abaixo de 5.0 ou a recuperação não ajudar, reprovado
            else {
                this.aprovado = false;
                this.mediaFinal = media;
            }
        }
    }

    // Atualiza a nota e recalcula o conceito, média e status
    public void atualizarNota(int unidade, Float nota) {
        switch (unidade) {
            case 1: this.notaUnidade1 = nota; break;
            case 2: this.notaUnidade2 = nota; break;
            case 3: this.notaUnidade3 = nota; break;
            case 4: this.notaUnidade4 = nota; break;
            default: throw new RuntimeException("Unidade inválida.");
        }

        // Recalcula tudo após inserir a nota
        this.calcularMediaEStatus();
    }

    // Atualiza a nota de recuperação, se aplicável
    public void atualizarRecuperacao(Float notaRecuperacao) {
        this.recuperacaoNota = notaRecuperacao;
        this.calcularMediaEStatus();
    }
}

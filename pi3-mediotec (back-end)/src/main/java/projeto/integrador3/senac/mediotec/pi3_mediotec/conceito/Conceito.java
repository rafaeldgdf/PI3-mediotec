package projeto.integrador3.senac.mediotec.pi3_mediotec.conceito;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import lombok.Data;
import lombok.NoArgsConstructor;
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

    @NotNull(message = "A nota é obrigatória.")
    @Column(nullable = false)
    private Float nota;

    @Column(nullable = false)
    private String conceito;

    @Column(nullable = false)
    private String unidade;

    @Column(nullable = false)
    private Boolean aprovado;

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

    // Método para definir o conceito automaticamente com base na nota
    public void calcularConceito() {
        if (this.nota == null) {
            throw new RuntimeException("A nota não pode ser nula.");
        }

        if (this.nota >= 9.50 && this.nota <= 10.00) {
            this.conceito = "Excelente (E)";
        } else if (this.nota >= 9.00 && this.nota <= 9.49) {
            this.conceito = "Ótimo (O)";
        } else if (this.nota >= 7.00 && this.nota <= 8.99) {
            this.conceito = "Bom (B)";
        } else if (this.nota >= 6.00 && this.nota <= 6.99) {
            this.conceito = "Suficiente (S)";
        } else if (this.nota >= 0 && this.nota <= 5.99) {
            this.conceito = "Insuficiente (I)";
        } else {
            throw new RuntimeException("Nota inválida: a nota deve estar entre 0 e 10.");
        }
    }

    // Valida se a nota é válida (entre 0 e 10)
    public void validarNota() {
        if (this.nota == null || this.nota < 0 || this.nota > 10) {
            throw new RuntimeException("Nota inválida: a nota deve estar entre 0 e 10.");
        }
    }
}


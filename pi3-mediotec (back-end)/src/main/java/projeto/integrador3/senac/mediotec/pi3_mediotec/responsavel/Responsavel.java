package projeto.integrador3.senac.mediotec.pi3_mediotec.responsavel;

import java.io.Serializable;
import java.util.Set;
import jakarta.persistence.*;
import lombok.*;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.Aluno;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.Telefone;
import java.util.HashSet;


@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "responsavel")
public class Responsavel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   
    @Column(name = "cpf", nullable = false, unique = true)
    private String cpfResponsavel;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "ultimo_nome", nullable = false)
    private String ultimoNome;

    @Column(name = "grau_parentesco", nullable = false)
    private String grauParentesco;

    @ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    @Builder.Default
    @OneToMany(mappedBy = "responsavel", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Telefone> telefones = new HashSet<>();

    // Métodos auxiliares para configurar as relações
    public void addTelefone(Telefone telefone) {
        telefone.setResponsavel(this);
        this.telefones.add(telefone);
    }
}

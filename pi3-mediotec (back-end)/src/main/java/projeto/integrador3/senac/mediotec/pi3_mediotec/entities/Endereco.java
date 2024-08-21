package projeto.integrador3.senac.mediotec.pi3_mediotec.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "{usuario.endereco.cep.notnull}")
    @Size(min = 8, max = 9, message = "{usuario.endereco.cep.size}")
    @Column(name = "cep", nullable = false)
    private String cep;

    
    @NotNull(message = "{usuario.endereco.rua.notnull}")
    @Size(min = 3, max = 100, message = "{usuario.endereco.rua.size}")
    @Column(name = "rua", nullable = false)
    private String rua;

    @NotNull(message = "{usuario.endereco.numero.notnull}")
    @Size(min = 1, max = 10, message = "{usuario.endereco.numero.size}")
    @Column(name = "numero", nullable = false)
    private String numero;
    
    @NotNull(message = "{usuario.endereco.bairro.notnull}")
    @Size(min = 3, max = 50, message = "{usuario.endereco.bairro.size}")
    @Column(name = "bairro", nullable = false)
    private String bairro;
    

    @NotNull(message = "{usuario.endereco.cidade.notnull}")
    @Size(min = 3, max = 50, message = "{usuario.endereco.cidade.size}")
    @Column(name = "cidade", nullable = false)
    private String cidade;

    @NotNull(message = "{usuario.endereco.estado.notnull}")
    @Size(min = 2, max = 20, message = "{usuario.endereco.estado.size}")
    @Column(name = "estado", nullable = false)
    private String estado;


    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "coordenador_id")
    private Coordenador coordenador;
    
    @ManyToOne
    @JoinColumn(name = "coordenacao_id")
    private Coordenacao coordenacao;
}

package projeto.integrador3.senac.mediotec.pi3_mediotec.entities;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_endereco;

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
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "coordenador_id")
    private Coordenador coordenador;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "coordenacao_id")
    private Coordenacao coordenacao;
    
    @Override
    public int hashCode() {
        return Objects.hash(id_endereco); // Baseado apenas no ID para evitar ciclos
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endereco endereco = (Endereco) o;
        return Objects.equals(id_endereco, endereco.id_endereco); // Comparando pelo ID
    }
}

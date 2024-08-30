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
@Table(name = "telefone")
public class Telefone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "{usuario.telefone.ddd.notnull}")
    @Size(min = 2, max = 2, message = "{usuario.telefone.ddd.size}")
    @Column(name = "ddd", nullable = false)
    private String ddd;

    @NotNull(message = "{usuario.telefone.numero.notnull}")
    @Size(min = 8, max = 9, message = "{usuario.telefone.numero.size}")
    @Column(name = "numero", nullable = false)
    private String numero;

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
        return Objects.hash(id); // Baseado apenas no ID para evitar ciclos
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Telefone telefone = (Telefone) o;
        return Objects.equals(id, telefone.id); // Comparando pelo ID
    }
}

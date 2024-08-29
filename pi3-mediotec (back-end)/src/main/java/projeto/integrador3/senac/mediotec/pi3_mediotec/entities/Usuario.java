package projeto.integrador3.senac.mediotec.pi3_mediotec.entities;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@MappedSuperclass // Indica que esta classe é uma superclasse mapeada para herança JPA
public abstract class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "{usuario.nome.notnull}")
    @Size(min = 3, max = 50, message = "{usuario.nome.size}")
    @Column(nullable = false)
    private String nome;
    
    
    @NotNull(message = "{usuario.nome.notnull}")
    @Size(min = 3, max = 50, message = "{usuario.nome.size}")
    @Column(nullable = false)
    private String ultimoNome;

    @NotNull(message = "{usuario.genero.notnull}")
    @Column(nullable = false)
    private String genero;

    @NotNull(message = "{usuario.data_nascimento.notnull}")
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date data_nascimento;

    @NotNull(message = "{usuario.email.notnull}")
    @Email(message = "{usuario.email.email}")
    @Column(nullable = false)
    private String email;
    
    
}

package projeto.integrador3.senac.mediotec.pi3_mediotec.comunicado;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.emitente.Emitente;
import projeto.integrador3.senac.mediotec.pi3_mediotec.receptor.Receptor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comunicado")
public class Comunicado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_comunicado;

    @NotNull(message = "{comunicado.conteudo.notnull}")
    @Size(min = 10, max = 100, message = "{comunicado.conteudo.size}")
    @Column(nullable = false)
    private String conteudo;

    @NotNull(message = "{comunicado.data.notnull}")
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dataEnvio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_emitente", nullable = false)
    private Emitente emitente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_receptor", nullable = false)
    private Receptor receptor;
}

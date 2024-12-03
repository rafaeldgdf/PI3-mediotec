package projeto.integrador3.senac.mediotec.pi3_mediotec.arquivo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "arquivo")
public class Arquivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String tipo;

    @Lob
    private byte[] dados; // Conteúdo do arquivo em bytes
}

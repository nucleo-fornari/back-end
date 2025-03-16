package fornari.nucleo.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class AvaliacaoDimensoes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String tituloPreset;

    @Column
    private String tipoDimensao;

    @Column
    private String textoDimensao;

    @ManyToOne(targetEntity = Usuario.class)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario criador;
}

package fornari.nucleo.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String motivo;
    private String descricao;
    private LocalDateTime data;
    private Boolean aceito;

    @ManyToOne(targetEntity = Usuario.class)
    private Usuario responsavel;

    @ManyToOne(targetEntity = Sala.class)
    private Sala sala;
}

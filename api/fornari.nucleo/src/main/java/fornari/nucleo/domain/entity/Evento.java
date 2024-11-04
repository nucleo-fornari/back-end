package fornari.nucleo.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String titulo;

    private String descricao;

    private LocalDateTime data;

    private String local;

    private String tipo;

    @ManyToOne(targetEntity = Usuario.class)
    @JoinColumn(referencedColumnName = "id", name = "id_usuario")
    private Usuario usuario;

    private Boolean encerrado;
}

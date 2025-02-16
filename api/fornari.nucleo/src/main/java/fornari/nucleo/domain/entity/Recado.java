package fornari.nucleo.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Recado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titulo;
    private String conteudo;
    private LocalDateTime dtCriacao;

    @ManyToOne(targetEntity = Aluno.class)
    @JoinColumn(referencedColumnName = "id", name = "id_aluno")
    private Aluno aluno;

    @ManyToOne(targetEntity = Usuario.class)
    @JoinColumn(referencedColumnName = "id", name = "id_responsavel")
    private Usuario responsavel;


}

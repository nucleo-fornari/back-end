package fornari.nucleo.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class AlunoAvaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String periodo;

    private String bimestre;

    private String ano;

    private String textoSocioAfetivaEmocional;

    private String textoFisicoMotora;

    private String textoCognitiva;

    private LocalDate dtCriacao;

    @ManyToOne(targetEntity = Aluno.class)
    @JoinColumn(referencedColumnName = "id", name = "aluno_id")
    private Aluno aluno;
}

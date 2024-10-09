package fornari.nucleo.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "filiacao")
@Getter
@Setter
@AllArgsConstructor
public class Filiacao {

    @Id
    @ManyToOne(targetEntity = Aluno.class)
    @JoinColumn(name = "aluno_id", referencedColumnName = "id")
    private Aluno afiliado;

    @Id
    @ManyToOne(targetEntity = Usuario.class)
    @JoinColumn(name = "responsavel_id", referencedColumnName = "id")
    private Usuario responsavel;

    private String parentesco;

    public Filiacao () {

    }
}

package fornari.nucleo.domain.entity;

import fornari.nucleo.helper.messages.ConstMessages;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "filiacao")
@Getter
@Setter
@Builder
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

    @Column(nullable = false)
    @Pattern(regexp = "GENITOR|IRMÃO|AVÔ|TIO|PRIMO", message = ConstMessages.INVALID_KINSHIP)
    private String parentesco;

    public Filiacao () {}

    public void setResponsavel(Usuario responsavel) {
        if (!this.responsavel.equals(responsavel)) {
            this.responsavel = responsavel;
            responsavel.addFiliacao(this);
        }
    }
}

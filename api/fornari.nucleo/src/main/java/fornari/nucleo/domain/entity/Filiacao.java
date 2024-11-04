package fornari.nucleo.domain.entity;

import fornari.nucleo.helper.messages.ConstMessages;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity(name = "filiacao")
@Getter
@Setter
@Builder
public class Filiacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(targetEntity = Aluno.class)
    @JoinColumn(name = "aluno_id", referencedColumnName = "id")
    private Aluno afiliado;

    @ManyToOne(targetEntity = Usuario.class, fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "responsavel_id", referencedColumnName = "id")
    private Usuario responsavel;

    @Pattern(regexp = "GENITOR|IRMÃO|AVÔ|TIO|PRIMO", message = ConstMessages.INVALID_KINSHIP)
    private String parentesco;

    public Filiacao () {}

    public Filiacao(Integer id, Aluno afiliado, Usuario responsavel, String parentesco) {
        this.id = id;
        this.afiliado = afiliado;
        this.responsavel = responsavel;
        this.parentesco = parentesco;

        if (afiliado != null) {
            afiliado.addFiliacao(this);
        }
        if (responsavel != null) {
            responsavel.addFiliacao(this);
        }
    }

    public void setResponsavel(Usuario responsavel) {
        if (this.responsavel != null && !this.responsavel.equals(responsavel)) {
            this.responsavel = responsavel;
            responsavel.addFiliacao(this);
        }
    }

    public void setAfiliado(Aluno afiliado) {
        if (this.afiliado != null && !this.afiliado.equals(afiliado)) {
            this.afiliado = afiliado;
        }
    }
}

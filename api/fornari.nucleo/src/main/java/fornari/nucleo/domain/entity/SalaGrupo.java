package fornari.nucleo.domain.entity;

import fornari.nucleo.helper.messages.ConstMessages;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalaGrupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Pattern(regexp = "G1|G2|G3|G4|G5", message = ConstMessages.INVALID_GROUP_NAME)
    private String nome;

    @OneToMany(mappedBy = "grupo", targetEntity = Sala.class)
    private List<Sala> salas = new ArrayList<>();
}

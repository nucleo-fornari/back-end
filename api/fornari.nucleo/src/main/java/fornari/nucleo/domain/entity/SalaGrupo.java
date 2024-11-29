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


    private String nome;

    @OneToMany(mappedBy = "grupo", targetEntity = Sala.class)
    private List<Sala> salas = new ArrayList<>();

    public SalaGrupo(String nome) {
        this.id = null;
        this.nome = nome;
    }
}

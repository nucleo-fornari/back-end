package fornari.nucleo.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(targetEntity = Sala.class, mappedBy = "eventos", cascade = {CascadeType.PERSIST})
    private List<Sala> salas = new ArrayList<>();

    private Boolean encerrado;

    public void addSala(Sala sala) {
        if (!this.salas.contains(sala)) {
            this.salas.add(sala);
            sala.addEvento(this);
        }
    }

    public void removeSala(Sala sala) {
        this.salas.remove(sala);
        sala.removeEvento(this);
    }
}

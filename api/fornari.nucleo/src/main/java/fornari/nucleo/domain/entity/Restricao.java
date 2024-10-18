package fornari.nucleo.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Restricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String tipo;

    private String descricao;

    @ManyToMany
    @JoinTable(
            name = "restricao_aluno",
            joinColumns = @JoinColumn(name = "restricao_id"),
            inverseJoinColumns = @JoinColumn(name = "aluno_id")
    )
    private List<Aluno> alunos;

    public Restricao() {
        this.alunos = new ArrayList<>();
    }

    public void addAluno(Aluno ra) {
        if (!this.alunos.contains(ra)) {
            this.alunos.add(ra);
            ra.addRestricao(this);
        }
    }
}

package fornari.nucleo.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@Getter
@Setter
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ra", nullable = false, unique = true)
    private String ra;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "laudado", nullable = false)
    private boolean laudado;

    @Column(name = "dtNasc", nullable = false)
    private LocalDate dtNasc;

    @Column(name = "observacoes")
    private String observacoes;

    @OneToMany(targetEntity = Filiacao.class, mappedBy = "afiliado", cascade = CascadeType.PERSIST)
    private List<Filiacao> filiacoes;

    @ManyToMany(mappedBy = "alunos")
    private List<Restricao> restricoes;

    public Aluno() {
        this.restricoes = new ArrayList<>();
        this.filiacoes = new ArrayList<>();
    }

    public void addFiliacao(Filiacao filiacao) {
        if (!this.filiacoes.contains(filiacao)) {
            this.filiacoes.add(filiacao);
            filiacao.setAfiliado(this);
        }
    }

    public void addRestricao(Restricao ra) {
        if (!this.restricoes.contains(ra)) {
            this.restricoes.add(ra);
            ra.addAluno(this);
        }
    }
}

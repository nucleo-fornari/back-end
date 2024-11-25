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

    @Column(name = "laudo_url")
    private String laudoNome;

    @OneToMany(targetEntity = Filiacao.class, mappedBy = "afiliado", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Filiacao> filiacoes;

    @ManyToMany(mappedBy = "alunos", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Restricao> restricoes;

    @ManyToOne(targetEntity = Sala.class, cascade = CascadeType.PERSIST)
    private Sala sala;

    @OneToMany(targetEntity = Recado.class, mappedBy = "aluno", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Recado> recados;

    public Aluno() {
        this.restricoes = new ArrayList<>();
        this.filiacoes  = new ArrayList<>();
        this.recados    = new ArrayList<>();
    }

    @PreRemove
    public void removeRelations() {
        for (Restricao restricao : this.restricoes) {
            restricao.removeAluno(this);
        }
        this.restricoes.clear();
    }

    public void addFiliacao(Filiacao filiacao) {
        if (filiacao == null || this.filiacoes.contains(filiacao)) {
            return;
        }
        this.filiacoes.add(filiacao);
        filiacao.setAfiliado(this);
    }

    public void addRestricao(Restricao ra) {
        if (!this.restricoes.contains(ra)) {
            this.restricoes.add(ra);
            ra.addAluno(this);
        }
    }

    public void removeRestricao(Restricao restricao) {
        this.restricoes.remove(restricao);
    }

    public void removeFiliacao(Filiacao filiacao) {
        this.filiacoes.remove(filiacao);
    }
}

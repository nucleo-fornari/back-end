package fornari.nucleo.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "localizacao", nullable = false)
    private String localizacao;

    @Column(name = "nome", nullable = false)
    private String nome;

    @OneToMany(targetEntity = Usuario.class, mappedBy = "sala")
    private List<Usuario> professores = new ArrayList<>();

    @OneToMany(targetEntity = Aluno.class, mappedBy = "sala")
    private List<Aluno> alunos = new ArrayList<>();

    @ManyToOne(targetEntity = SalaGrupo.class, cascade = CascadeType.PERSIST)
    private SalaGrupo grupo;

    @ManyToMany
    @JoinTable(
            name = "evento_sala",
            joinColumns = @JoinColumn(name = "id_sala"),
            inverseJoinColumns = @JoinColumn(name = "id_evento")
    )
    private List<Evento> eventos = new ArrayList<>();

    public void addProfessor(Usuario professor) {
        if (!this.professores.contains(professor)) {
            this.professores.add(professor);
            professor.setSala(this);
        }
    }

    public void addAluno(Aluno aluno) {
        if (!this.alunos.contains(aluno)) {
            this.alunos.add(aluno);
            aluno.setSala(this);
        }
    }

    public void removeAluno(Aluno aluno) {
        this.alunos.remove(aluno);
        aluno.setSala(null);
    }

    public void removeProfessor(Usuario user) {
        this.professores.remove(user);
        user.setSala(null);
    }

    public void addEvento(Evento evento) {
        if (!this.eventos.contains(evento)) {
            this.eventos.add(evento);
            evento.addSala(this);
        }
    }

    public void removeEvento(Evento evento) {
        this.eventos.remove(evento);
    }
}

package fornari.nucleo.domain.entity;

import fornari.nucleo.helper.messages.ConstMessages;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "usuario")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Usuario {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Column(unique = true, nullable = false)
    @Email(message = ConstMessages.INVALID_EMAIL)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column
    private String token;

    @Column(nullable = false)
    private LocalDate dtNasc;

    @Column
    @Pattern(regexp = "RESPONSAVEL|SECRETARIO|PROFESSOR|COORDENADOR", message = ConstMessages.INVALID_USER_ROLE)
    private String funcao;

    @ManyToOne(targetEntity = Endereco.class, cascade = CascadeType.PERSIST)
    @JoinColumn(referencedColumnName = "id", name = "id_endereco")
    private Endereco endereco;

    @OneToMany(targetEntity = Filiacao.class, mappedBy = "responsavel", cascade = CascadeType.PERSIST)
    private List<Filiacao> filiacoes;

    @OneToMany(targetEntity = Evento.class, mappedBy = "usuario", cascade = CascadeType.PERSIST)
    private List<Evento> eventos;

    public Usuario () {
        this.filiacoes = new ArrayList<>();
    }

    public void setEndereco(Endereco endereco) {
        if (this.endereco != endereco) {
            this.endereco = endereco;
            endereco.addUser(this);
        }
    }

    public void addFiliacao(Aluno aluno, String parentesco) {
       if(this.filiacoes.stream().filter(x -> x.getAfiliado().equals(aluno)).toList().isEmpty()) {
           Filiacao filiacao = new Filiacao(aluno, this, parentesco);

           this.filiacoes.add(filiacao);
           filiacao.setResponsavel(this);
       }
    }

    public void addFiliacao(Filiacao filiacao) {
        if (!this.filiacoes.contains(filiacao)) {
            this.filiacoes.add(filiacao);
            filiacao.setResponsavel(this);
            filiacao.getAfiliado().addFiliacao(filiacao);
        }
    }
}

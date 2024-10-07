package fornari.nucleo.domain.entity;

import fornari.nucleo.helper.messages.ConstMessages;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "usuario")
@Getter
@Setter
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
    private Date dtNasc;

    @Column
    @Pattern(regexp = "RESPONSAVEL|SECRETARIO|PROFESSOR|COORDENADOR", message = ConstMessages.INVALID_USER_ROLE)
    private String funcao;

    @ManyToOne(targetEntity = Endereco.class, cascade = CascadeType.PERSIST)
    @JoinColumn(referencedColumnName = "id", name = "id_endereco")
    private Endereco endereco;

//    @ManyToMany(targetEntity = Aluno.class, mappedBy = "responsaveis", cascade = CascadeType.PERSIST)
//    private List<Aluno> afiliados;

    public Usuario () {
//        this.afiliados = new ArrayList<>();
    }
    public void setEndereco(Endereco endereco) {
        if (this.endereco != endereco) {
            this.endereco = endereco;
            endereco.addUser(this);
        }
    }
}

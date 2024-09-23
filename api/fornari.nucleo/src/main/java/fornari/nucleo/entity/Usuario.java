package fornari.nucleo.entity;

import fornari.nucleo.helper.messages.ConstMessages;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

import java.sql.Date;

@Entity(name = "usuario")
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
    @Pattern(regexp = "^[a-zA-Z0-9._]{3,}[@][a-zA-Z]{3,}[.][a-zA-Z.]{3,}$", message = ConstMessages.INVALID_EMAIL)
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

    public Usuario(Integer id, String nome, String cpf, String email, String senha, Date dtNasc, String funcao, Endereco endereco) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.dtNasc = dtNasc;
        this.funcao = funcao;
        this.endereco = endereco;
    }

    public Usuario() {
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDtNasc() {
        return dtNasc;
    }

    public void setDtNasc(Date dtNasc) {
        this.dtNasc = dtNasc;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        if (this.endereco != endereco) {
            this.endereco = endereco;
            endereco.addUser(this);
        }
    }

}

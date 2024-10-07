package fornari.nucleo.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity(name = "endereco")
@Getter
@Setter
public class Endereco {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String cep;

    @Getter
    @Column(nullable = true)
    private String complemento;

    @Column(nullable = false)
    private String bairro;

    @Column(nullable = false, name = "cidade")
    private String localidade;

    @Column(nullable = false)
    private String uf;

    @Column(nullable = false)
    private String logradouro;

    @Column(nullable = false)
    private Integer numero;

    @OneToMany(mappedBy = "endereco", targetEntity = Usuario.class, cascade = CascadeType.PERSIST)
    private List<Usuario> usuarios;

    public Endereco(int id, String cep, String complemento, String bairro, String localidade, String uf, String logradouro, Integer numero) {
        this.id = id;
        this.cep = cep;
        this.complemento = complemento;
        this.bairro = bairro;
        this.localidade = localidade;
        this.uf = uf;
        this.logradouro = logradouro;
        this.numero = numero;
        this.usuarios = new ArrayList<>();
    }

    public Endereco() {
        this.usuarios = new ArrayList<>();
    }

    public void addUser(Usuario usuario) {
        if (!this.usuarios.contains(usuario)) {
            this.usuarios.add(usuario);
            usuario.setEndereco(this);
        }
    }
}

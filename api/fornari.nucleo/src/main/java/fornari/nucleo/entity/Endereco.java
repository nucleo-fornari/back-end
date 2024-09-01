package fornari.nucleo.entity;

import jakarta.persistence.*;
import java.util.*;

@Entity(name = "endereco")
public class Endereco {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String cep;

    @Column(nullable = true)
    private String complemento;

    @Column(nullable = false)
    private String bairro;

    @Column(nullable = false, name = "cidade")
    private String localidade;

    @Column(nullable = false)
    private String uf;

    @OneToMany(mappedBy = "endereco", targetEntity = Usuario.class, cascade = CascadeType.PERSIST)
    private List<Usuario> usuarios;

    public Endereco(Integer id, String cep, String complemento, String bairro, String localidade, String uf) {
        this.id = id;
        this.cep = cep;
        this.complemento = complemento;
        this.bairro = bairro;
        this.localidade = localidade;
        this.uf = uf;
        this.usuarios = new ArrayList<>();
    }

    public Endereco() {
        this.usuarios = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public void addUser(Usuario usuario) {
        if (!this.usuarios.contains(usuario)) {
            this.usuarios.add(usuario);
            usuario.setEndereco(this);
        }
    }
}

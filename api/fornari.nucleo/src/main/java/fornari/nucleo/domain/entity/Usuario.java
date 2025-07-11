package fornari.nucleo.domain.entity;

import fornari.nucleo.helper.messages.ConstMessages;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity(name = "usuario")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Usuario implements UserDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Column(nullable = false)
    private String telefone;

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

    @Column
    private boolean lgpd;

    @ManyToOne(targetEntity = Endereco.class, cascade = CascadeType.PERSIST)
    @JoinColumn(referencedColumnName = "id", name = "id_endereco")
    private Endereco endereco;

    @OneToMany(targetEntity = Filiacao.class, mappedBy = "responsavel", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Filiacao> filiacoes;

    @OneToMany(targetEntity = Evento.class, mappedBy = "usuario", cascade = CascadeType.PERSIST)
    private List<Evento> eventos;

    @OneToMany(targetEntity = Chamado.class, mappedBy = "usuario", cascade = CascadeType.PERSIST)
    private List<Chamado> chamados;

    @OneToMany(targetEntity = Recado.class, mappedBy = "responsavel", cascade = CascadeType.PERSIST)
    private List<Recado> recados;

    @ManyToOne(targetEntity = Sala.class, cascade = CascadeType.PERSIST)
    @JoinColumn(referencedColumnName = "id", name = "id_sala")
    private Sala sala;

    @OneToMany(targetEntity = AvaliacaoDimensoes.class, mappedBy = "criador", cascade = CascadeType.REMOVE)
    private List<AvaliacaoDimensoes> dimensoesCadastradas;

    @OneToMany(targetEntity = TokenRedefinicaoSenha.class, mappedBy = "usuario", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<TokenRedefinicaoSenha> tokens;

    public Usuario () {
        this.chamados = new ArrayList<>();
        this.filiacoes = new ArrayList<>();
        this.recados = new ArrayList<>();
        this.dimensoesCadastradas = new ArrayList<>();
        this.tokens = new ArrayList<>();
    }

    public Usuario(Integer id, String nome, String cpf, String telefone, String email, String funcao) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.funcao = funcao;
    }

    public Usuario(String nome, String email, String senha){
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public void addFiliacao(Filiacao filiacao) {
        if (this.filiacoes == null) {
            this.filiacoes = new ArrayList<>();
        }
        if (filiacao == null || this.filiacoes.contains(filiacao)) {
            return;
        }
        this.filiacoes.add(filiacao);
        filiacao.setResponsavel(this);
    }

    public void addChamado(Chamado chamado) {
        if (!this.chamados.contains(chamado)) {
            this.chamados.add(chamado);
        }
    }

    public void addDimensao(AvaliacaoDimensoes dim) {
        if (!this.dimensoesCadastradas.contains(dim)) {
            this.dimensoesCadastradas.add(dim);
        }
    }

    public void addToken(TokenRedefinicaoSenha tk) {
        if (!this.tokens.contains(tk)) {
            this.tokens.add(tk);
        }
    }

    public void removeEvento(Evento evento) {
        this.eventos.remove(evento);
    }

    public void removeToken(TokenRedefinicaoSenha tk) {
        this.tokens.remove(tk);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Objects.equals(this.funcao, "SECRETARIO") ? List.of(new SimpleGrantedAuthority("SECRETARIO")) :
                Objects.equals(this.funcao, "PROFESSOR") ? List.of(new SimpleGrantedAuthority("PROFESSOR")) :
                                List.of(new SimpleGrantedAuthority("RESPONSAVEL"));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

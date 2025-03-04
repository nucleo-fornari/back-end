package fornari.nucleo.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String mensagem;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    private boolean lida;

    @ManyToOne
    private Usuario usuario;

    public Notificacao(String titulo, String mensagem, Usuario usuario) {
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.usuario = usuario;
        this.dataCriacao = LocalDateTime.now();
        this.lida = false;
    }

    public Notificacao(String titulo, String mensagem) {
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.usuario = null;
        this.dataCriacao = LocalDateTime.now();
        this.lida = false;
    }
}
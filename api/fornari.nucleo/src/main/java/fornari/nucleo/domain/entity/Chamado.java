package fornari.nucleo.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Chamado {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 500)
    @Column(name = "descricao")
    private String descricao;

    @Column(name = "finalizado")
    private boolean finalizado;
    @Column(name = "dtAbertura")
    private LocalDateTime dtAbertura;

    @Column(name = "dtFechamento")
    private LocalDateTime dtFechamento;

    @Column(name = "criancaAtipica")
    private boolean criancaAtipica;

    @JoinColumn(name = "id_chamado_tipo", nullable = false)
    @ManyToOne(targetEntity = ChamadoTipo.class, cascade = CascadeType.PERSIST)
    private ChamadoTipo tipo;

    @JoinColumn(name = "id_usuario", referencedColumnName = "id", nullable = false)
    @ManyToOne(targetEntity = Usuario.class)
    private Usuario usuario;

    public Chamado() {
    }

    public void setTipo(ChamadoTipo tipo) {
        this.tipo = tipo;
        tipo.addChamado(this);
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
        if (finalizado) {
            this.dtFechamento = LocalDateTime.now();
        } else {
            this.dtFechamento = null;
        }
    }

    public void setResponsavel(Usuario responsavel) {
        this.usuario = responsavel;
        responsavel.addChamado(this);
    }
}

package fornari.nucleo.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
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

    public Chamado() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ChamadoTipo getTipo() {
        return tipo;
    }

    public void setTipo(ChamadoTipo tipo) {
        this.tipo = tipo;
        tipo.addChamado(this);
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }

    public LocalDateTime getDtAbertura() {
        return dtAbertura;
    }

    public void setDtAbertura(LocalDateTime dtAbertura) {
        this.dtAbertura = dtAbertura;
    }

    public LocalDateTime getDtFechamento() {
        return dtFechamento;
    }

    public void setDtFechamento(LocalDateTime dtFechamento) {
        this.dtFechamento = dtFechamento;
    }

    public boolean isCriancaAtipica() {
        return criancaAtipica;
    }

    public void setCriancaAtipica(boolean criancaAtipica) {
        this.criancaAtipica = criancaAtipica;
    }
}

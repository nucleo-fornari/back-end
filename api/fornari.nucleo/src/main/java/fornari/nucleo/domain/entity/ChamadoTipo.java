package fornari.nucleo.domain.entity;

import fornari.nucleo.helper.messages.ConstMessages;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ChamadoTipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tipo", nullable = false, unique = true)
    private String tipo;

    @Column(name = "prioridade", nullable = false)
    @Min(value = 1, message = ConstMessages.INVALID_CHAMADO_TIPO_PRIORITY)
    @Max(value = 3, message = ConstMessages.INVALID_CHAMADO_TIPO_PRIORITY)
    private Integer prioridade;

    @OneToMany(mappedBy = "tipo", targetEntity = Chamado.class, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Chamado> chamados;

    public ChamadoTipo() {
        this.chamados = new ArrayList<>();
    }

    public void addChamado(Chamado chamado) {
        if (!this.chamados.contains(chamado)) {
            this.chamados.add(chamado);
            chamado.setTipo(this);
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Integer prioridade) {
        this.prioridade = prioridade;
    }

    public List<Chamado> getChamados() {
        return chamados;
    }

    public void setChamados(List<Chamado> chamados) {
        this.chamados = chamados;
    }
}

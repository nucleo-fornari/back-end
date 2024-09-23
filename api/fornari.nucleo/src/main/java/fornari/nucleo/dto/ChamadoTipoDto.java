package fornari.nucleo.dto;

import fornari.nucleo.entity.Chamado;

import java.util.ArrayList;
import java.util.List;

public class ChamadoTipoDto {
    private Integer id;
    private String tipo;
    private Integer prioridade;
    private List<ChamadoDto> chamados;

    public ChamadoTipoDto() {
        this.chamados = new ArrayList<>();
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

    public List<ChamadoDto> getChamados() {
        return chamados;
    }

    public void setChamados(List<ChamadoDto> chamados) {
        this.chamados = chamados;
    }
}

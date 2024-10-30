package fornari.nucleo.domain.dto;

import fornari.nucleo.domain.dto.usuario.UsuarioDefaultDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChamadoDto {
    private Integer id;
    private String descricao;

    private boolean finalizado;
    private LocalDateTime dtAbertura;
    private LocalDateTime dtFechamento;

    private boolean criancaAtipica;
    private ChamadoTipoDto tipo;
    private UsuarioDefaultDto responsavel;

    public ChamadoDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public ChamadoTipoDto getTipo() {
        this.tipo.getChamados().clear();
        return this.tipo;
    }

    public Integer getPrioridade() {
        Integer prioridade = this.tipo.getPrioridade();

        if (this.criancaAtipica) {
            prioridade += 1;
        }

        return prioridade;
    }

    public void setTipo(ChamadoTipoDto tipo) {
        this.tipo = tipo;
    }
}

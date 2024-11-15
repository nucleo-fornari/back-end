package fornari.nucleo.domain.dto;

import fornari.nucleo.domain.dto.usuario.UsuarioResponseDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChamadoDto {
    private Integer id;
    private String descricao;
    private boolean finalizado;
    private LocalDateTime dtAbertura;
    private ChamadoTipoDto tipo;
    private LocalDateTime dtFechamento;
    private boolean criancaAtipica;
    private UsuarioResponseDto responsavel;

    public ChamadoDto() {
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
}

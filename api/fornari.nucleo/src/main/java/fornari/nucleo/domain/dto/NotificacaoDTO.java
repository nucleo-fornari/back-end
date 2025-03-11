package fornari.nucleo.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class NotificacaoDTO {
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private boolean lida;
    private Integer usuarioId;

    public NotificacaoDTO(String titulo, String mensagem, Integer usuarioId) {
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.dataCriacao = LocalDateTime.now();
        this.lida = false;
        this.usuarioId = usuarioId;
    }
}

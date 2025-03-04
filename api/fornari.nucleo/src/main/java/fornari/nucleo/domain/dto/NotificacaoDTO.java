package fornari.nucleo.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NotificacaoDTO {
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private boolean lida;
}

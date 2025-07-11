package fornari.nucleo.domain.dto.agendamento;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AgendamentoRequestDTO {
    private Integer responsavelId;
    private Integer salaId;
    private String motivo;
    private boolean aceito;
    private String descricao;
    private LocalDateTime data;
}
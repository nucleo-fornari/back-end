package fornari.nucleo.domain.dto.agendamento;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AgendamentoDTO {
    private Integer id;
    private Integer responsavelId;
    private String responsavelNome;
    private Integer salaId;
    private String motivo;
    private boolean aceito;
    private String descricao;
    private LocalDateTime data;
}

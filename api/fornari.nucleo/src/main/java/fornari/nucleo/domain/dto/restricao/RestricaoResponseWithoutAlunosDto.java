package fornari.nucleo.domain.dto.restricao;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestricaoResponseWithoutAlunosDto {
    private Integer id;

    private String tipo;

    private String descricao;
}

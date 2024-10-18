package fornari.nucleo.domain.dto.restricao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestricaoCreationRequestDto {
    private String tipo;

    private String descricao;
}

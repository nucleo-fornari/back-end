package fornari.nucleo.domain.dto.recado;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecadoRequestDto {

    String titulo;
    String conteudo;
    Integer usuarioId;
}

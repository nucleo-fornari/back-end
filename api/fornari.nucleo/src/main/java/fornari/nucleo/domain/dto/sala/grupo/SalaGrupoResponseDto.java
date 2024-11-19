package fornari.nucleo.domain.dto.sala.grupo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SalaGrupoResponseDto {
    private Integer id;
    private String nome;
}

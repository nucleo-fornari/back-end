package fornari.nucleo.domain.dto.AvaliacaoDimensoes;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AvaliacaoDimensoesResponseDto {

    private Integer id;

    private String tituloPreset;

    private String tipoDimensao;

    private String textoDimensao;

}

package fornari.nucleo.domain.dto.AvaliacaoDimensoes;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AvaliacaoDimensoesRequestDto {

    private String tituloPreset;

    private String tipoDimensao;

    private String textoDimensao;

    private Integer id;

    private Integer userId;
}

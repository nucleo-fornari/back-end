package fornari.nucleo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChamadoTipoDto {
    private Integer id;
    private String tipo;
    private Integer prioridade;
    private List<ChamadoDto> chamados;
}

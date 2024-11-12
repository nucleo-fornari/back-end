package fornari.nucleo.domain.dto.sala;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Data
@Builder
@Getter
@Setter
public class SalaRequestDto {
    private String localizacao;
    private String nome;
}

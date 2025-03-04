package fornari.nucleo.domain.dto.aluno;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlunoAndSalaIdDto {
    private String nome;
    private Integer idSala;
}
package fornari.nucleo.domain.dto.aluno;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AlunoPutDto {
    private Integer id;
    private String nome;
    private String ra;
    private LocalDate dtNasc;
}

package fornari.nucleo.domain.dto.aluno;

import fornari.nucleo.domain.dto.FiliacaoAlunoDto;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class AlunoCreationRequestDto {

    private Integer id;

    private String ra;

    private String nome;

    private boolean laudado;

    private LocalDate dtNasc;

    private String observacoes;

    private FiliacaoAlunoDto filiacao;
}

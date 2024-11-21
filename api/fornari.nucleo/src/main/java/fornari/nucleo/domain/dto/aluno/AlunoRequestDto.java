package fornari.nucleo.domain.dto.aluno;

import fornari.nucleo.domain.dto.FiliacaoAlunoDto;
import fornari.nucleo.domain.dto.sala.SalaRequestDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class AlunoRequestDto {

    private Integer id;

    private String ra;

    private String nome;

    private boolean laudado;

    private LocalDate dtNasc;

    private String observacoes;

    private FiliacaoAlunoDto filiacao;

    private List<Integer> restricoes;

    private SalaRequestDto sala;
}

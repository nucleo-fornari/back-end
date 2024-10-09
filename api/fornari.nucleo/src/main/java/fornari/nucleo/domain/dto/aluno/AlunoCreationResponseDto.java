package fornari.nucleo.domain.dto.aluno;

import fornari.nucleo.domain.dto.EnderecoDto;
import fornari.nucleo.domain.dto.FiliacaoAlunoDto;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class AlunoCreationResponseDto {

    private Integer id;

    private String ra;

    private String nome;

    private boolean laudado;

    private LocalDate dtNasc;

    private List<FiliacaoAlunoDto> filiacoes;

    private String observacoes;
}
